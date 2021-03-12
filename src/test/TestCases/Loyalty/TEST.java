package TestCases.Loyalty;

import Helpers.Test.BaseTest;
import Step.CustomerStep;
import Step.Value.BaseStepValue;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TEST extends BaseTest {
    SoftAssert soft = new SoftAssert();

    @Test(description = "Main test")
    public void mainTest() throws Exception {
        testOk();
        testOk();
        testFailed();
        testOk();
        //softAssert.assertEquals("1","3");
        softAssert.assertAll();
    }

    private void testOk() {
        BaseStepValue base = getNewBaseStepValue(false);
        base.expectedValue = 1;
        step("Vérifie que la valeur est égal à 1 ", () -> {
            CustomerStep.testMe(base);
        });
    }

    private void testFailed() {
        BaseStepValue base = getNewBaseStepValue(false);
        base.expectedValue = 66;
        step("Vérifie que la valeur est égal à 66 ", () -> {
            CustomerStep.testMe(base);
        });
    }
}
