package Step.Value;

import Helpers.Test.ReportHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BaseStepValue {

    private static StepResult result_fail;
    private static StepResult result_pass;
    public Object expectedValue;
    public ChromeDriver driver;
    boolean isHardAssert;
    private SoftAssert soft;

    public BaseStepValue(ChromeDriver driver, SoftAssert soft, boolean isHardAssert) {
        this.driver = driver;
        this.soft = soft;
        this.isHardAssert = isHardAssert;
    }

    /**
     * Comparaison des deux valeures
     */
    public void isEquals(Object valueToTest) {
        // Prend un screenshot
        // ReportHelper.attachScreenshot(driver);
        //String uuid = UUID.randomUUID().toString();
        Allure.getLifecycle().updateStep(s -> s.setStatus(Status.PASSED));
        try {
            Assert.assertEquals(valueToTest, expectedValue);
        } catch (AssertionError e) {
            Allure.getLifecycle().updateStep(s -> s.setStatus(Status.FAILED));
            Allure.getLifecycle().updateTestCase(s -> s.setStatus(Status.FAILED));
        }

    }

    /**
     * Vérifie que la valeur n'est pas null
     * @param valueToTest
     */
    public void isNotNull(Object valueToTest) {
        // Prend un screenshot
        ReportHelper.attachScreenshot(driver);
        if (isHardAssert) {
            Assert.assertNotNull(valueToTest);
        } else {
            soft.assertNotNull(valueToTest);
        }
    }

    /**
     * Vérifie que la valeur est null
     * @param valueToTest
     */
    public void isNull(Object valueToTest) {
        // Prend un screenshot
        ReportHelper.attachScreenshot(driver);
        if (isHardAssert) {
            Assert.assertNull(valueToTest);
        } else {
            soft.assertNull(valueToTest);
        }
    }

    public void isTrue(boolean valueToTest) {
        // Prend un screenshot
        ReportHelper.attachScreenshot(driver);
        if (isHardAssert) {
            Assert.assertTrue(valueToTest);
        } else {
            soft.assertTrue(valueToTest);
        }
    }

    public void isFalse(boolean valueToTest) {
        // Prend un screenshot
        ReportHelper.attachScreenshot(driver);
        if (isHardAssert) {
            Assert.assertFalse(valueToTest);
        } else {
            soft.assertFalse(valueToTest);
        }
    }

    public String getExpectedValue() {
        return String.valueOf(expectedValue);
    }


}
