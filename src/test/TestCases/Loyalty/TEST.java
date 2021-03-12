package TestCases.Loyalty;

import Helpers.Test.BaseTest;
import Step.CustomerStep;
import Step.Value.BaseStepValue;
import org.testng.annotations.Test;

public class TEST extends BaseTest {

    @Test(description = "Main test")
    public void mainTest() throws Exception {
        testOk();
        testOk();
        testFailed();
        testOk();
        softAssert.assertAll();
    }

    private void testOk() {
        BaseStepValue base = getNewBaseStepValue(false);
        base.expectedValue = 1;
        step("test Ok step ", () -> {
            CustomerStep.testMe(base);
        });
    }

    private void testFailed() {
        BaseStepValue base = getNewBaseStepValue(false);
        base.expectedValue = 3;
        step("test KO step ", () -> {
            CustomerStep.testMe(base);
        });
    }
}
