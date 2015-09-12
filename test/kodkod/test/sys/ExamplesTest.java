package kodkod.test.sys;

import static kodkod.engine.Solution.Outcome.SATISFIABLE;
import static kodkod.engine.Solution.Outcome.TRIVIALLY_UNSATISFIABLE;
import static kodkod.engine.Solution.Outcome.UNSATISFIABLE;
import static org.junit.Assert.assertEquals;
import kodkod.ast.Formula;
import kodkod.engine.Solution;
import kodkod.instance.Bounds;

import org.junit.Test;

import kodkod.examples.alloy.*;
import kodkod.examples.tptp.*;


public abstract class ExamplesTest  {

	protected abstract Solution solve(Formula formula, Bounds bounds);
	
	protected void check(String name, Solution sol, Solution.Outcome outcome) { 
		assertEquals(outcome, sol.outcome());
	}
	
	/**
	 * Runs the Bigconfig example for 1 hq, 9 subs, 4 unwindings.
	 */
	@Test
	public final void testBigconfig() {
		final Bigconfig prob = new Bigconfig(4);
		final Solution sol = solve(prob.show(), prob.bounds(1, 9, 10));
		check(prob.getClass().getSimpleName(), sol, SATISFIABLE);
	}
	
	/**
	 * Runs the CeilingsAndFloors.checkBelowTooDoublePrime example for 6 Man, 6 Platform.
	 */
	@Test
	public final void testCeilingsAndFloors_BelowTooDoublePrime() {
		final CeilingsAndFloors prob = new CeilingsAndFloors();
		final Solution sol = solve(prob.checkBelowTooDoublePrime(), prob.bounds(6, 6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs the CeilingsAndFloors.checkBelowTooAssertion example for 6 Man, 6 Platform.
	 */
	@Test
	public final void testCeilingsAndFloors_BelowTooAssertion() {
		final CeilingsAndFloors prob = new CeilingsAndFloors();
		final Solution sol = solve(prob.checkBelowTooAssertion(), prob.bounds(6, 6));
		check(prob.getClass().getSimpleName(), sol, SATISFIABLE);
	}
	
	/**
	 * Runs the Dijkstra example for 6 States, 6 Processes, and 6 Mutexes.
	 */
	@Test
	public final void testDijkstra() {
		final Dijkstra prob = new Dijkstra();
		final Solution sol = solve(prob.checkDijkstraPreventsDeadlocks(), prob.bounds(6,6,6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs FileSystem.checkNoDirAliases for 5.
	 */
	@Test
	public final void testFileSystem() {
		final FileSystem prob = new FileSystem();
		final Solution sol = solve(prob.checkNoDirAliases(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, SATISFIABLE);
	}
	
	/**
	 * Runs Handshake.runPuzzle for 6.
	 */
	@Test
	public final void testHandshake() {
		final Handshake prob = new Handshake();
		final Solution sol = solve(prob.runPuzzle(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, SATISFIABLE);
	}
	
	/**
	 * Runs Hotel.checkNoBadEntry for 4.
	 */
	@Test
	public final void testHotel4() {
		final Hotel prob = new Hotel();
		check(prob.getClass().getSimpleName(), solve(prob.checkNoBadEntry(), prob.bounds(4)), UNSATISFIABLE);
	}
	
	/**
	 * Runs Hotel.checkNoBadEntry for 6.
	 */
	@Test
	public final void testHotel6() {
		final Hotel prob = new Hotel();
		check(prob.getClass().getSimpleName(), solve(prob.checkNoBadEntry(), prob.bounds(6)), SATISFIABLE);
	}
	
	/**
	 * Runs Lists.runShow for 5.
	 */
	@Test
	public final void testLists_runShow() {
		final Lists prob = new Lists();
		final Solution sol = solve(prob.runShow(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, SATISFIABLE);
	}
	
	/**
	 * Runs Lists.checkEmpties for 5.
	 */
	@Test
	public final void testLists_checkEmpties() {
		final Lists prob = new Lists();
		final Solution sol = solve(prob.checkEmpties(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs Lists.checkReflexive for 5.
	 */
	@Test
	public final void testLists_checkReflexive() {
		final Lists prob = new Lists();
		final Solution sol = solve(prob.checkReflexive(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs Lists.checkSymmetric for 5.
	 */
	@Test
	public final void testLists_checkSymmetric() {
		final Lists prob = new Lists();
		final Solution sol = solve(prob.checkSymmetric(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs the Pigeonhole example for 10 Pigeons, 9 Holes.
	 */
	@Test
	public final void testPigeonhole() {
		final Pigeonhole prob = new Pigeonhole();
		final Formula show = prob.declarations().and(prob.pigeonPerHole());
		final Solution sol = solve(show, prob.bounds(10,9));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs RingElection.checkAtMostOneElected for 10 Times, 5 Processes.
	 */
	@Test
	public final void testRingElection() {
		final RingElection prob = new RingElection();
		final Solution sol = solve(prob.checkAtMostOneElected(), prob.bounds(5,10));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs Trees.checkEquivOfTreeDefns example for 4.
	 */
	@Test
	public final void testTrees() {
		final Trees prob = new Trees();
		final Solution sol = solve(prob.checkEquivOfTreeDefns(), prob.bounds(4));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}


	/**
	 * Runs the Toughnut example for an 8x8 board.
	 */
	@Test
	public final void testToughnut() {
		final Toughnut prob = new Toughnut();
		final Solution sol = solve(prob.checkBelowTooDoublePrime(), prob.bounds(8));
		check(prob.getClass().getSimpleName(), sol, TRIVIALLY_UNSATISFIABLE);
	}
	
	/**
	 * Runs AbstractWorldDefinitions.checkA241 for 5.
	 */
	@Test
	public final void testAWD_A241() {
		final AbstractWorldDefinitions prob = new AbstractWorldDefinitions();
		final Solution sol = solve(prob.checkA241(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
		
	}
	
	/**
	 * Runs AbstractWorldDefinitions.checkAbOp_total for 5.
	 */
	@Test
	public final void testAWD_AbOp_total() {
		final AbstractWorldDefinitions prob = new AbstractWorldDefinitions();
		final Solution sol = solve(prob.checkAbOp_total(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, TRIVIALLY_UNSATISFIABLE);
		
	}
	
	/**
	 * Runs AbstractWorldDefinitions.checkAbIgnore_inv for 5.
	 */
	@Test
	public final void testAWD_AbIgnore_inv() {
		final AbstractWorldDefinitions prob = new AbstractWorldDefinitions();
		final Solution sol = solve(prob.checkAbIgnore_inv(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs AbstractWorldDefinitions.checkAbTransfer_inv for 5.
	 */
	@Test
	public final void testAWD_AbTransfer_inv() {
		final AbstractWorldDefinitions prob = new AbstractWorldDefinitions();
		final Solution sol = solve(prob.checkAbTransfer_inv(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs ALG195.checkCO1.
	 */
	@Test
	public final void testALG195() {
		final ALG195 prob = new ALG195();
		final Solution sol = solve(prob.checkCO1(), prob.bounds());
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs ALG197.checkCO1.
	 */
	@Test
	public final void testALG197() {
		final ALG197 prob = new ALG197();
		final Solution sol = solve(prob.checkCO1(), prob.bounds());
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs ALG212.checkDistLong for 4.
	 */
	@Test
	public final void testALG212() {
		final ALG212 prob = new ALG212();
		final Solution sol = solve(prob.checkDistLong(), prob.bounds(4));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs COM008.checkGoalToBeProved for 5.
	 */
	@Test
	public final void testCOM008() {
		final COM008 prob = new COM008();
		final Solution sol = solve(prob.checkGoalToBeProved(), prob.bounds(5));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs GEO091.checkTheorem_2_13 for 6.
	 */
	@Test
	public final void testGEO091() {
		final GEO091 prob = new GEO091();
		final Solution sol = solve(prob.checkTheorem_2_13(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs GEO092.checkProposition2141 for 6.
	 */
	@Test
	public final void testGEO092() {
		final GEO092 prob = new GEO092();
		final Solution sol = solve(prob.checkProposition2141(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs GEO115.checkTheorem385 for 6.
	 */
	@Test
	public final void testGEO115() {
		final GEO115 prob = new GEO115();
		final Solution sol = solve(prob.checkTheorem385(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs GEO158.checkConsistent for 6.
	 */
	@Test
	public final void testGEO158() {
		final GEO158 prob = new GEO158();
		final Solution sol = solve(prob.checkConsistent(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs GEO159.checkDefs for 6.
	 */
	@Test
	public final void testGEO159() {
		final GEO159 prob = new GEO159();
		final Solution sol = solve(prob.checkDefs(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs GRA019.checkGoalToBeProved for 6.
	 */
//	@Test
//	public final void testGRA019() {
//		final GRA013_026 prob = new GRA013_026();
//		final Solution sol = solve(prob.checkGoalToBeProved(), prob.bounds(6));
//		check(prob.getClass().getSimpleName(), sol, SATISFIABLE, 407, 6968, 15413);
//	}
	
	/**
	 * Runs LAT258.checkGoalToBeProved for 6.
	 */
	@Test
	public void testLAT258() {
		final LAT258 prob = new LAT258();
		final Solution sol = solve(prob.checkGoalToBeProved(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs MED007.checkTranssls2_qilt27 for 6.
	 */
	@Test
	public final void testMED007() {
		final MED007 prob = new MED007();
		final Solution sol = solve(prob.checkTranssls2_qilt27(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs MED009.checkTranssls2_qige27 for 6.
	 */
	@Test
	public final void testMED009() {
		final MED009 prob = new MED009();
		final Solution sol = solve(prob.checkTranssls2_qige27(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs NUM374.checkWilkie for 3.
	 */
	@Test
	public final void testNUM374() {
		final NUM374 prob = new NUM374();
		final Solution sol = solve(prob.checkWilkie(), prob.bounds(3));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs NUM378.checkInequalities.
	 */
	@Test
	public final void testNUM378() {
		final NUM378 prob = new NUM378();
		final Solution sol = solve(prob.checkInequalities(), prob.bounds());
		check(prob.getClass().getSimpleName(), sol, TRIVIALLY_UNSATISFIABLE);
	}
	
	/**
	 * Runs SET943.checkT96_zfmisc_1 for 3.
	 */
	@Test
	public final void testSET943() {
		final SET943 prob = new SET943();
		final Solution sol = solve(prob.checkT96_zfmisc_1(), prob.bounds(3));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs SET948.checkT101_zfmisc_1 for 3.
	 */
	@Test
	public final void testSET948() {
		final SET948 prob = new SET948();
		final Solution sol = solve(prob.checkT101_zfmisc_1(), prob.bounds(3));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs SET967.checkT120_zfmisc_1 for 3.
	 */
	@Test
	public final void testSET967() {
		final SET967 prob = new SET967();
		final Solution sol = solve(prob.checkT120_zfmisc_1(), prob.bounds(3));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
	
	/**
	 * Runs TOP020.checkChallenge_AMR_1_4_4 for 6.
	 */
	@Test
	public final void testTOP020() {
		final TOP020 prob = new TOP020();
		final Solution sol = solve(prob.checkChallenge_AMR_1_4_4(), prob.bounds(6));
		check(prob.getClass().getSimpleName(), sol, UNSATISFIABLE);
	}
}
