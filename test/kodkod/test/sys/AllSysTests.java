package kodkod.test.sys;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ExamplesTestWithRegularSolver.class,
	ExamplesTestWithIncrementalSolver.class})

public class AllSysTests {}
