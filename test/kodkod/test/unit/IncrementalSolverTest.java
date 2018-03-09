/* 
 * Kodkod -- Copyright (c) 2005-2012, Emina Torlak
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package kodkod.test.unit;

import static kodkod.engine.Solution.Outcome.SATISFIABLE;
import static kodkod.engine.Solution.Outcome.TRIVIALLY_SATISFIABLE;
import static kodkod.engine.Solution.Outcome.TRIVIALLY_UNSATISFIABLE;
import static kodkod.engine.Solution.Outcome.UNSATISFIABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import kodkod.ast.Formula;
import kodkod.ast.Relation;
import kodkod.engine.Evaluator;
import kodkod.engine.IncrementalSolver;
import kodkod.engine.Solution;
import kodkod.engine.Solution.Outcome;
import kodkod.engine.config.Options;
import kodkod.engine.satlab.SATFactory;
import kodkod.instance.Bounds;
import kodkod.instance.Instance;
import kodkod.instance.TupleFactory;
import kodkod.instance.Universe;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import kodkod.test.util.Solvers;

/**
 * Tests for {@link IncrementalSolver incremental solving}.
 * 
 * @author Emina Torlak
 */
@RunWith(Parameterized.class)
public final class IncrementalSolverTest {

	private final IncrementalSolver solver;

	/**
	 * Constructs an incremental solver test.
	 */
	public IncrementalSolverTest(SATFactory solverOpt) {
		final Options opt = new Options();
		opt.setSolver(solverOpt);
		this.solver = IncrementalSolver.solver(opt);
	}


	@Parameters
	public static Collection<Object[]> solversToTestWith() {	
		final Collection<Object[]> ret = new ArrayList<Object[]>();
		for(SATFactory factory : Solvers.allAvailableSolvers()) {
			if (factory.incremental())
				ret.add(new Object[]{factory});
		}	
		return ret;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		solver.free();
	}

	@Test
	public void testBadOptions() {
		final Options opt = new Options();
		opt.setLogTranslation(1);
		try {
			IncrementalSolver.solver(opt);
			fail("Expected an IllegalArgumentException when logging is enabled.");
		} catch (IllegalArgumentException iae) {
			// fine
		}
		opt.setLogTranslation(0);
		opt.setSolver(SATFactory.plingeling());
		try {
			IncrementalSolver.solver(opt);
			fail("Expected an IllegalArgumentException when using a non-incremental solver.");
		} catch (IllegalArgumentException iae) {
			// fine
		}
	}

	@Test
	public void testOneTriviallySatisfiableStep() {
		assertTrue(solver.usable());
		final Solution sol = solver.solve(Formula.TRUE, new Bounds(new Universe("A0")));
		assertTrue(solver.usable());
		assertEquals(TRIVIALLY_SATISFIABLE, sol.outcome());
	}

	@Test
	public void testOneTriviallyUnsatisfiableStep() {
		assertTrue(solver.usable());
		final Bounds b = new Bounds(new Universe("A0"));
		final Solution sol = solver.solve(Formula.FALSE, b);
		assertFalse(solver.usable());
		assertEquals(TRIVIALLY_UNSATISFIABLE, sol.outcome());
		try {
			solver.solve(Formula.TRUE, b);
			fail("Expected an IllegalStateException when trying to call a solver that has returned UNSAT.");
		} catch (IllegalStateException iae) {
			// finle
		}
	}

	@Test
	public void testBadBounds() {
		final Bounds b = new Bounds(new Universe("A0", "A1", "A2"));
		final TupleFactory t = b.universe().factory();
		final Relation r0 = Relation.unary("r0");
		final Relation r1 = Relation.unary("r1");
		b.bound(r0, t.setOf("A0"));
		final Solution sol = solver.solve(r0.some(), b);
		assertEquals(SATISFIABLE, sol.outcome());
		b.bound(r1, t.setOf("A1"));
		try {
			solver.solve(r1.some(), b);
			fail("Expected an IllegalArgumentException when solving with bounds that do not induce a coarser set of symmetries than the initial bounds.");
		} catch (IllegalArgumentException iae) {
			// fine
		}
		assertFalse(solver.usable());
	}

	private Solution checkModel(Solution s, Formula...formulas) {
		final Instance i = s.instance();
		assertNotNull(i);
		final Evaluator eval = new Evaluator(i, solver.options());
		assertTrue(eval.evaluate(Formula.and(formulas)));
		return s;
	}

	private Solution checkOutcomeAndStats(Solution s, Outcome expectedOutcome, int expectedPrimaryVars) {
		assertEquals(expectedOutcome, s.outcome());
		assertEquals(expectedPrimaryVars, s.stats().primaryVariables());
		return s;
	}

	@Test
	public void testSimpleIncrementalSequence() {
		final Bounds b = new Bounds(new Universe("A0", "A1", "A2"));
		final TupleFactory t = b.universe().factory();
		final Relation r0 = Relation.unary("r0");
		final Relation r1 = Relation.unary("r1");
		final Relation r2 = Relation.unary("r2");
		b.bound(r0, t.setOf("A0","A1"));
		b.bound(r1, t.setOf("A1","A2"));
		final Formula[] f = { r0.some(), 
				r1.some(), 
				r0.intersection(r1).no(), 
				r2.in(r0.union(r1)) };

		checkModel(solver.solve(f[0], b), f[0]);

		b.relations().clear();
		checkModel(solver.solve(f[1], b), f[0], f[1]);
		checkModel(solver.solve(f[2], b), f[0], f[1], f[2]);

		b.bound(r2, t.allOf(1));
		checkModel(solver.solve(f[3], b), f[0], f[1], f[2], f[3]);
	}

	@Test
	public void testIncrementalFullSymmetryBreaking() {
		final Bounds b = new Bounds(new Universe("A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9"));
		final TupleFactory t = b.universe().factory();
		final Relation ord = Relation.binary("ord"), univ03 = Relation.unary("univ[0..3]"), first = Relation.unary("first"), last = Relation.unary("last");
		final Relation next47 = Relation.binary("next43"), univ47 = Relation.unary("univ[4..7]");
		final Relation next09 = Relation.binary("next09");
		b.bound(univ03, t.setOf("A0", "A1", "A2", "A3"));
		b.bound(first, b.upperBound(univ03));
		b.bound(last, b.upperBound(univ03));
		b.bound(ord, b.upperBound(univ03).product(b.upperBound(univ03)));
		b.boundExactly(univ47, t.setOf("A4", "A5", "A6", "A7"));

		final Formula[] f = { ord.totalOrder(univ03, first, last),
				ord.acyclic(), 
				next47.acyclic(), 
				next09.acyclic() };

		checkOutcomeAndStats(checkModel(solver.solve(f[0], b), f[0]), TRIVIALLY_SATISFIABLE, 0);

		b.relations().clear();
		checkOutcomeAndStats(checkModel(solver.solve(f[1], b), f[0], f[1]), TRIVIALLY_SATISFIABLE, 0);

		b.bound(next47, t.setOf("A4", "A5", "A6", "A7").product(t.setOf("A4", "A5", "A6", "A7")));
		checkOutcomeAndStats(checkModel(solver.solve(f[2], b), f[0], f[1], f[2]), TRIVIALLY_SATISFIABLE, 0);

		b.relations().clear();
		b.bound(next09, t.allOf(2));
		// expecting 6 variables for the symmetry-reduced upper bound of next47 and 100 for the full upper bound of next09
		checkOutcomeAndStats(checkModel(solver.solve(f[3], b), f[0], f[1], f[2], f[3]), SATISFIABLE, 100 + 6);
	}

	@Test
	public void testIncrementalPartialSymmetryBreaking() {
		final Bounds b = new Bounds(new Universe("A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9"));
		final TupleFactory t = b.universe().factory();
		final Relation ord = Relation.binary("ord"), univ03 = Relation.unary("univ[0..3]"), first = Relation.unary("first"), last = Relation.unary("last");
		final Relation fun = Relation.binary("fun"), univ47 = Relation.unary("univ[4..7]"), univ89 = Relation.unary("univ[8..9]");

		b.bound(univ47, t.setOf("A4", "A5", "A6", "A7"));
		b.bound(univ89, t.setOf("A8", "A9"));
		b.bound(fun, b.upperBound(univ47).product(b.upperBound(univ89)));

		final Formula[] f = { fun.function(univ47, univ89), 
				ord.totalOrder(univ03, first, last), 
				ord.acyclic(), 
				last.product(first).in(ord) };

		final int vars0 =  4 + 2 + 4*2; // univ47, univ89, fun
		checkOutcomeAndStats(checkModel(solver.solve(f[0], b), f[0]), SATISFIABLE, vars0);

		b.relations().clear();
		b.bound(univ03, t.setOf("A0", "A1", "A2", "A3"));
		b.bound(first, b.upperBound(univ03));
		b.bound(last, b.upperBound(univ03));
		b.bound(ord, b.upperBound(univ03).product(b.upperBound(univ03)));
		final int vars1 = vars0 + 4*3 + 4*4; // univ03, first, last, ord
		checkOutcomeAndStats(checkModel(solver.solve(f[1], b), f[0], f[1]), SATISFIABLE, vars1);

		b.relations().clear();

		checkOutcomeAndStats(checkModel(solver.solve(f[2], b), f[0], f[1], f[2]), SATISFIABLE, vars1);

		checkOutcomeAndStats(solver.solve(f[3], b), UNSATISFIABLE, vars1);
		
		//final Solver whole = new Solver(solver.options());
		//System.out.println(whole.solve(Formula.and(f[0], f[1], f[2]), b));
	}


}
