/**
 * 
 */
package kodkod.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import kodkod.ast.Formula;
import kodkod.ast.Relation;
import kodkod.engine.Solution;
import kodkod.engine.Solver;
import kodkod.engine.satlab.SATFactory;
import kodkod.instance.Bounds;
import kodkod.instance.TupleFactory;
import kodkod.instance.Universe;

import org.junit.Test;

import kodkod.examples.alloy.CeilingsAndFloors;
import kodkod.examples.alloy.Dijkstra;

/**
 * Tests the solution enumeration functionality of the Solver class.
 * @author Emina Torlak
 */
public class EnumerationTest {
	private final Solver solver;
	/**
	 * Constructs a new EnumerationTest.
	 */
	public EnumerationTest( ) {
		solver = new Solver();
		solver.options().setSolver(SATFactory.MiniSat);
	}

	@Test
	public final void testCeilingsAndFloors() {
		final CeilingsAndFloors model = new CeilingsAndFloors();
		final Formula f = model.checkBelowTooAssertion();
		
		// has exactly one instance
		Iterator<Solution> sol = solver.solveAll(f, model.bounds(2,2));
		assertNotNull(sol.next().instance());
		assertNull(sol.next().instance());
		assertFalse(sol.hasNext());

		// has more than one instance
		sol = solver.solveAll(f, model.bounds(3,3));
		assertNotNull(sol.next().instance());
		assertNotNull(sol.next().instance());
		assertTrue(sol.hasNext());
		
		// has no instances
		sol = solver.solveAll(model.checkBelowTooDoublePrime(), model.bounds(3,3));
		assertNull(sol.next().instance());
	}
	
	@Test
	public final void testDijkstra() {
		final Dijkstra model = new Dijkstra();
		final Formula f = model.showDijkstra();

		Iterator<Solution> sol = solver.solveAll(f, model.bounds(5,2,2));
		// has more than one instance
		assertNotNull(sol.next().instance());
		assertNotNull(sol.next().instance());
		assertTrue(sol.hasNext());
		
	}
	
	@Test
	public final void testTrivial() {
		final Relation r = Relation.unary("r");
		final Universe u  = new Universe(Arrays.asList("a","b","c"));
		final TupleFactory f = u.factory();
		final Bounds b = new Bounds(u);
		b.bound(r, f.setOf("a"), f.allOf(1));
		final Formula someR = r.some();
		
		Iterator<Solution> sol = solver.solveAll(someR, b);
		// has a trivial instance, followed by 2 non-trivial instances
		assertEquals(Solution.Outcome.TRIVIALLY_SATISFIABLE, sol.next().outcome());
		assertEquals(Solution.Outcome.SATISFIABLE, sol.next().outcome());
		assertEquals(Solution.Outcome.SATISFIABLE, sol.next().outcome());
		assertEquals(Solution.Outcome.UNSATISFIABLE, sol.next().outcome());
		assertFalse(sol.hasNext());

	}
	
	
}
