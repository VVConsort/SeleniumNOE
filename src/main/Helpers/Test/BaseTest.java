package Helpers.Test;

import Enums.PaymentMean;
import Helpers.Test.TestSuiteProperties.PropertiesLoader;
import View.Ticket.ReceiptView;
import Step.TicketStep;
import Step.Value.*;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    // Driver Chrome
    public WebDriver currentDriver;
    // Soft assert
    public SoftAssert softAssert = new SoftAssert();
    // Page ticket
    private ReceiptView _receiptViewPage;

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
     * Vide le ticket et ferme chrome
     */
    protected void deleteTicket() {
        TicketStep.deleteTicket(currentDriver);
    }

    /**
     * Ferme le navigateur
     */
    protected void closeBrowser() {
        // Ferme le navigateur
        currentDriver.quit();
    }

    /**
     * Fait apparaitre les comparaisons fausses et met éventuellement le test en FAILED
     */
    protected void assertAll() {
        // On visualise les comparaisons fausses
        softAssert.assertAll();
    }

    @AfterTest
    protected void onAfterTest() {
        // Vérification des valeures comparées
        assertAll();
        // Fermeture du navigateur
        closeBrowser();
    }

    @AfterTest
    protected void onMethodEnd(ITestResult result) {
        // Si la méthode est en échec, on prend un screenshot pour l'attacher au rapport
        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot();
        }
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
     * Retourne la page ticket en l'instanciant si nécessaire
     * @return
     */
    protected ReceiptView getTicketPage() {
        if (_receiptViewPage == null) {
            _receiptViewPage = new ReceiptView(currentDriver);
        }
        return _receiptViewPage;
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

    protected PaymentStepValue newPaymentStepValue(Object expectedValue, String paymentAmount, PaymentMean paymentMean , String paymentId, boolean stopTestOnFail) {
        return new PaymentStepValue(expectedValue, paymentAmount,paymentMean,paymentId,currentDriver, softAssert, stopTestOnFail);
    }
}
