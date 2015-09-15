package kodkod.test.sys;

import java.util.ArrayList;
import java.util.Collection;

import kodkod.ast.Formula;
import kodkod.engine.Solution;
import kodkod.engine.Solver;
import kodkod.engine.satlab.SATFactory;
import kodkod.instance.Bounds;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import kodkod.test.util.Solvers;

@RunWith(Parameterized.class)
public class ExamplesTestWithRegularSolver extends ExamplesTest  {


	private final Solver solver;
	
	public ExamplesTestWithRegularSolver(SATFactory solverOpt) {
		this.solver = new Solver();
		this.solver.options().setSolver(solverOpt);
	}
	
	@Parameters
	public static Collection<Object[]> solversToTestWith() {
		final Collection<Object[]> ret = new ArrayList<Object[]>();
		for(SATFactory factory : Solvers.allAvailableSolvers()) {
			ret.add(new Object[]{factory});
			//System.out.println(factory);
		}
		return ret;
	}

	protected Solution solve(Formula formula, Bounds bounds) {
		return solver.solve(formula, bounds);
	}

}
