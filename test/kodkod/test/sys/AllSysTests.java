package kodkod.test.sys;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ExamplesTestWithIncrementalSolver.class, 
	ExamplesTestWithRegularSolver.class })

public class AllSysTests {}
