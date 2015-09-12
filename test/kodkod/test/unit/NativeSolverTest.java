/**
 * 
 */
package kodkod.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kodkod.ast.Formula;
import kodkod.engine.Solution.Outcome;
import kodkod.engine.Solver;
import kodkod.engine.satlab.ResolutionTrace;
import kodkod.engine.satlab.SATFactory;
import kodkod.engine.satlab.SATProver;
import kodkod.engine.satlab.SATSolver;
import kodkod.instance.Bounds;
import kodkod.util.ints.Ints;

import org.junit.Test;

import kodkod.test.util.Solvers;
import kodkod.examples.alloy.CeilingsAndFloors;


/**
 * A test that loads multiple native solvers into memory.
 * 
 * @author Emian Torlak
 */
public class NativeSolverTest {

	private static final List<SATFactory> solvers = Solvers.allAvailableSolvers();
	
	private final Formula formula;
	private final Bounds bounds; 
	
	public NativeSolverTest() {
		final CeilingsAndFloors prob = new CeilingsAndFloors();
		this.formula = prob.checkBelowTooDoublePrime();
		this.bounds = prob.bounds(6, 6);
	}
	
	@Test
	public void testEmptyCNF() {
		for(SATFactory factory : solvers) {
			assertTrue(factory.instance().solve());
		}
	}
	
	@Test
	public void testEmptyClauseCNF() {
		for(SATFactory factory : solvers) {
			final SATSolver solver = factory.instance();
			solver.addClause(new int[0]);
			assertFalse(solver.solve());
		}
	}

	@Test
	public void testProofOfEmptyClauseCNF() {
		for(SATFactory factory : solvers) {
			if (!factory.prover()) continue;
			final SATProver solver = (SATProver) factory.instance();
			solver.addClause(new int[0]);
			assertFalse(solver.solve());
			final ResolutionTrace proof = solver.proof();
			assertEquals(1, proof.size());
			assertEquals(Ints.singleton(0), proof.core());
			assertEquals(Ints.EMPTY_SET, proof.resolvents());
			assertEquals(0, proof.get(0).size());
		}
	}
	
	@Test
	public void testProofOfNthEmptyClauseCNF() {
		for(SATFactory factory : solvers) {
			if (!factory.prover()) continue;
			final SATProver solver = (SATProver) factory.instance();
			solver.addVariables(1);
			solver.addClause(new int[]{1});
			solver.addVariables(1);
			solver.addClause(new int[]{-2});
			solver.addVariables(2);
			solver.addClause(new int[]{2, 3, 4});
			solver.addClause(new int[0]);
			solver.addClause(new int[]{4});
			solver.addClause(new int[]{3});
			assertFalse(solver.solve());
			final ResolutionTrace proof = solver.proof();
			assertEquals(4, proof.size());
			assertEquals(Ints.singleton(3), proof.core());
			assertEquals(Ints.EMPTY_SET, proof.resolvents());
			assertEquals(0, proof.get(3).size());
		}
	}

	@Test
	public void testProofOfLastEmptyClauseCNF() {
		for(SATFactory factory : solvers) {
			if (!factory.prover()) continue;
			final SATProver solver = (SATProver) factory.instance();
			solver.addVariables(1);
			solver.addClause(new int[]{1});
			solver.addVariables(1);
			solver.addClause(new int[]{-2});
			solver.addVariables(2);
			solver.addClause(new int[]{2, 3, 4});
			solver.addClause(new int[0]);
			assertFalse(solver.solve());
			final ResolutionTrace proof = solver.proof();
			assertEquals(4, proof.size());
			assertEquals(Ints.singleton(3), proof.core());
			assertEquals(Ints.EMPTY_SET, proof.resolvents());
			assertEquals(0, proof.get(3).size());
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPlingelingBadThreadInput() {
		final SATFactory pl = SATFactory.plingeling(0, true);
		solveWith(pl); 
	}
	
	@Test
	public void testPlingelingOneThread() {
		final SATFactory pl = SATFactory.plingeling(1,null);
		assertEquals(Outcome.UNSATISFIABLE, solveWith(pl));
	}
	
	@Test
	public void testPlingelingPortfolio() {
		final SATFactory pl = SATFactory.plingeling(null,true);
		assertEquals(Outcome.UNSATISFIABLE, solveWith(pl));
	}
	
	@Test
	public void testPlingelingThreeThreadsPortfolio() {
		final SATFactory pl = SATFactory.plingeling(3,true);
		assertEquals(Outcome.UNSATISFIABLE, solveWith(pl));
	}
	
//	@Test
//	public void testLingelingIncremental() {
//		final SATSolver solver = SATFactory.Lingeling.instance();
//		solver.addVariables(1);
//		solver.addClause(new int[]{1});
//		assertTrue(solver.solve());
//		solver.addVariables(1);
//		solver.addClause(new int[]{-2});
//		assertTrue(solver.solve());
//		solver.addVariables(8);
//		solver.addClause(new int[]{2, 9, 10});
//		assertTrue(solver.solve());
//		solver.addClause(new int[0]);
//		solver.addClause(new int[]{7, 8, 10});
//		assertFalse(solver.solve());
//		solver.addClause(new int[]{-3, 5});
//		assertFalse(solver.solve());
//	}
//	
//	@Test
//	public void testLingelingRetrieveModel() {
//		final SATSolver solver = SATFactory.Lingeling.instance();
//		solver.addVariables(1);
//		solver.addClause(new int[]{1});
//		assertTrue(solver.solve());
//		solver.addVariables(1);
//		solver.addClause(new int[]{-2});
//		assertTrue(solver.solve());
//		solver.addVariables(18);
//		solver.addClause(new int[]{2, 9, 10});
//		assertTrue(solver.solve());
//		try {
//			for(int i = 1; i <= 20; i++)
//				solver.valueOf(i);
//		} catch (IllegalArgumentException ia) {
//			fail(ia.getMessage());
//		}
//		try {
//			solver.valueOf(21);
//			fail("Expected an IllegalArgumentsException for non-existant variable 21.");
//		} catch (IllegalArgumentException ia) {
//			// do nothing
//		}
//	}
	
	@Test
	public void testMultipleSolvers() {
		
		final List<Callable<Outcome>> calls = new ArrayList<Callable<Outcome>>();
		for(SATFactory factory : solvers) {
			calls.add(callSolver(factory));
		}

		final ExecutorService exec = Executors.newFixedThreadPool(calls.size());
		try {
			final List<Future<Outcome>> out = exec.invokeAll(calls);
			assertEquals(calls.size(), out.size());
			for(Future<Outcome> result : out) {
				assertEquals(Outcome.UNSATISFIABLE, result.get());
			}
		} catch (InterruptedException e) {
			fail("Unexpected interruption");
		} catch (ExecutionException e) {
			fail("Unexpected execution exception");
		}
	}
	
	private Callable<Outcome> callSolver(final SATFactory factory) { 
		return new Callable<Outcome>() {
			public Outcome call() throws Exception {
				return solveWith(factory);
			}
		};
	}
	
	private Outcome solveWith(SATFactory factory) {
		final Solver solver = new Solver();
		solver.options().setSolver(factory);
		return solver.solve(formula, bounds).outcome();
	}
	
}
