package Step.Value;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BaseStepValue {

    public Object expectedValue;

    boolean isHardAssert;

    public WebDriver driver;

    private SoftAssert soft;

    public BaseStepValue(Object expectedValue, WebDriver driver, SoftAssert soft, boolean isHardAssert) {
        this.expectedValue = expectedValue;
        this.driver = driver;
        this.soft = soft;
        this.isHardAssert = isHardAssert;
    }

    /**
     * Comparaison des deux valeures
     */
    public void isEquals(Object valueToTest) {
        if (isHardAssert) {
            Assert.assertEquals(valueToTest,expectedValue);
        } else {
            soft.assertEquals(valueToTest,expectedValue);
        }
    }

    /**
     * Vérifie que la valeur n'est pas null
     * @param valueToTest
     */
    public void isNotNull(Object valueToTest)
    {
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
    public void isNull(Object valueToTest)
    {
        if (isHardAssert) {
            Assert.assertNull(valueToTest);
        } else {
            soft.assertNull(valueToTest);
        }
    }

    public String getExpectedValue()
    {
        return String.valueOf(expectedValue);
    }
}
