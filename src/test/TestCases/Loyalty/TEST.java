package TestCases.Loyalty;

import Helpers.Test.BaseTest;
import Step.CustomerStep;
import Step.Value.BaseStepValue;
import org.testng.annotations.Test;

public class TEST extends BaseTest {

    @Test(description = "Main test")
    public void mainTest() {
        testOk();
        testOk();
        testFailed();
        testOk();
    }

    private void testOk() {
        BaseStepValue step = getNewBaseStepValue(false);
        step.expectedValue = 1;
        CustomerStep.testMe(step);

    }

    private void testFailed() {
        BaseStepValue step = getNewBaseStepValue(false);
        step.expectedValue = 2;
        CustomerStep.testMe(step);
    }
}
