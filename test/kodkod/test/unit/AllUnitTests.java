package kodkod.test.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	SparseSequenceTest.class,
	BooleanCircuitTest.class,
	BooleanMatrixTest.class,
	TranslatorTest.class,
	EvaluatorTest.class,
	SymmetryBreakingTest.class,
	SkolemizationTest.class,
	EnumerationTest.class,
	IntTest.class,
	NativeSolverTest.class,
	UCoreTest.class,
	ReductionAndProofTest.class,
	IncrementalSolverTest.class,
	RegressionTests.class,
})

public class AllUnitTests {}
