package kodkod.test.sys;

import static kodkod.engine.Solution.Outcome.UNSATISFIABLE;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import kodkod.ast.Formula;
import kodkod.ast.Relation;
import kodkod.engine.IncrementalSolver;
import kodkod.engine.Solution;
import kodkod.engine.config.Options;
import kodkod.engine.fol2sat.SymmetryDetector;
import kodkod.engine.satlab.SATFactory;
import kodkod.instance.Bounds;
import kodkod.instance.TupleFactory;
import kodkod.instance.TupleSet;
import kodkod.util.ints.IndexedEntry;
import kodkod.util.ints.IntSet;
import kodkod.util.nodes.AnnotatedNode;
import kodkod.util.nodes.Nodes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import kodkod.test.util.Solvers;

import kodkod.examples.tptp.LAT258;

@RunWith(Parameterized.class)
public class ExamplesTestWithIncrementalSolver extends ExamplesTest  {


	private final IncrementalSolver solver;
	
	public ExamplesTestWithIncrementalSolver(SATFactory solverOpt) {
		final Options opt = new Options();
		opt.setSolver(solverOpt);
		this.solver = IncrementalSolver.solver(opt);
	}
	
	@Parameters
	public static Collection<Object[]> solversToTestWith() {
		final Collection<Object[]> ret = new ArrayList<Object[]>();
		for(SATFactory factory : Solvers.allAvailableSolvers()) {
			if (factory.incremental()) { 
				ret.add(new Object[]{factory});
				//System.out.println(factory);
			}
		}
		
		return ret;
	}

	protected Solution solve(Formula formula, Bounds bounds) {
		final Set<IntSet> parts = SymmetryDetector.partition(bounds);
		final Bounds inc = new Bounds(bounds.universe());
		final TupleFactory t = inc.universe().factory();
		for(IndexedEntry<TupleSet> e : inc.intBounds()) {
			inc.boundExactly(e.index(), e.value());
		}
		for(IntSet part : parts) {
			// dummy relations to set up initial symmetry classes
			inc.boundExactly(Relation.unary("r" + part.min()), t.setOf(1, part)); 
		}
		
		
		Solution sol = solver.solve(Formula.TRUE, inc);
		assertEquals(Solution.Outcome.TRIVIALLY_SATISFIABLE, sol.outcome());
		//System.out.println("FORMULAS: " + Nodes.roots(formula).size());
		for(Formula f : Nodes.roots(formula)) {
			inc.relations().clear();
			if (!bounds.relations().isEmpty()) {
				final Set<Relation> rels = AnnotatedNode.annotate(f).relations();
				rels.retainAll(bounds.relations());
				for(Relation r : rels) { 
					inc.bound(r, bounds.lowerBound(r), bounds.upperBound(r));
				}
				bounds.relations().removeAll(rels);
			}
			//System.out.println(f + ", " + inc.relations() + "\n");
			sol = solver.solve(f, inc);
			if (sol.unsat()) {
				break;
			}
		}
		return sol;
	}

	/**
	 * Runs LAT258.checkGoalToBeProved for 5.  Running it for 6 takes about a minute.
	 */
	@Test
	public void testLAT258() {
		final LAT258 prob = new LAT258();
		final Solution sol = solve(prob.checkGoalToBeProved(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
}
