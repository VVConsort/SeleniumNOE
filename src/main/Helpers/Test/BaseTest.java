package Helpers.Test;

import Enums.PaymentMean;
import Helpers.Test.TestSuiteProperties.PropertiesLoader;
import Step.TicketStep;
import Step.Value.*;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    // Driver Chrome
    public ChromeDriver currentDriver;
    // Soft assert
    public SoftAssert softAssert = new SoftAssert();

    /**
     * Charge les properties de la TestSuite
     * @param propertiesFilePath Chemin vers le fichier Properties à charger
     */
    @Parameters({"propertiesFilePath"})
    @BeforeSuite
    protected void setTestSuiteProperties(String propertiesFilePath) {
        loadProperties(propertiesFilePath);
    }

    /**
     * Charge les properties du Test
     * @param propertiesFilePath Chemin vers le fichier Properties à charger
     */
    @Parameters({"propertiesFilePath"})
    @BeforeTest
    protected void setTestProperties(String propertiesFilePath) {
        loadProperties(propertiesFilePath);
    }

    private void loadProperties(String propertiesFilePath) {
        PropertiesLoader pro = new PropertiesLoader();
        pro.setTestSuiteProperties(propertiesFilePath);
    }

    /**
     * Vide le ticket
     */
    protected void deleteTicket() {
        TicketStep.deleteTicket(currentDriver);
    }

    /**
     * Ferme le navigateur
     */
    protected void closeBrowser() {
        if (!isDriverClosed()) {
            // Screenshot
            //attachScreenshot();
            // Ferme le navigateur
            currentDriver.quit();
        }
    }

    /**
     * Fait apparaitre les comparaisons fausses et met éventuellement le test en FAILED
     */
    protected void assertAll() {
        // On visualise les comparaisons fausses
        try {
            softAssert.assertAll();
        } catch (AssertionError err) {
            attachScreenshot();
            throw err;
        }

    }

    @AfterTest
    protected void onAfterTest() {
        // On test les comparaisons
        try {
            softAssert.assertAll();
        } catch (AssertionError err) {
            // Prend un screenshot
            attachScreenshot();
            // Fermeture du navigateur
            closeBrowser();
            throw err;
        }
        // Fermeture du navigateur
        closeBrowser();
    }

    // FIXME
    @AfterMethod
    protected void onMethodEnd(ITestResult result) {
        // On prend un screenshot pour l'attacher au rapport
        if (!isDriverClosed()) {
            attachScreenshot();
        }
    }

    /**
     * Renvoie vrai si le driver/navigateur est fermé
     * @return
     */
    private boolean isDriverClosed() {
        return currentDriver == null || currentDriver.getSessionId() == null;
    }

    /**
     * Prend un screenshot et l'attache au rapport
     * @return
     */
    @Attachment(value = "Page screenshot", type = "image/png")
    protected byte[] attachScreenshot() {
        return ((TakesScreenshot) currentDriver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * @param expectedValue
     * @param stopTestOnFail
     * @return
     */
    protected BaseStepValue newStepValue(Object expectedValue, boolean stopTestOnFail) {
        return new BaseStepValue(expectedValue, currentDriver, softAssert, stopTestOnFail);
    }

    /**
     * @param expectedValue
     * @param stopTestOnFail
     * @return
     */
    protected DiscountStepValue newDiscountStepValue(Object expectedValue, String discountLabel, String associatedProduct, boolean stopTestOnFail) {
        return new DiscountStepValue(expectedValue, discountLabel, associatedProduct, currentDriver, softAssert, stopTestOnFail);
    }

    protected LoggingStepValue newLoggingStepValue(String url, String terminalKey, String chromeProfilesPath, String chromeProfile, String userName, String password) {
        return new LoggingStepValue(url, terminalKey, chromeProfilesPath, chromeProfile, userName, password);
    }

    protected TicketStepValue newTicketStepValue(Object expectedValue, boolean stopTestOnFail) {
        return new TicketStepValue(expectedValue, currentDriver, softAssert, stopTestOnFail);
    }

    protected PaymentStepValue newPaymentStepValue(Object expectedValue, String paymentAmount, PaymentMean paymentMean, String paymentId, boolean stopTestOnFail) {
        return new PaymentStepValue(expectedValue, paymentAmount, paymentMean, paymentId, currentDriver, softAssert, stopTestOnFail);
    }
}
