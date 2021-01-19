package Helpers.Test;

import Helpers.Test.TestSuiteProperties.PropertiesLoader;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import Step.Value.Payment.PaymentStepValue;
import org.openqa.selenium.chrome.ChromeDriver;
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
            ReportHelper.attachScreenshot(currentDriver);
            // Fermeture du navigateur
            closeBrowser();
            throw err;
        }
        // Fermeture du navigateur
        closeBrowser();
    }

    /**
     * Instancie et retourne une step
     * @param isHardAssert
     * @return
     */
    protected BaseStepValue getNewBaseStepValue(boolean isHardAssert) {
        return new BaseStepValue(currentDriver, softAssert, isHardAssert);
    }

    /**
     * Instancie et retourne une step promo
     * @param isHardAssert
     * @return
     */
    protected DiscountStepValue getNewDiscountStepValue(boolean isHardAssert) {
        return new DiscountStepValue(currentDriver, softAssert, isHardAssert);
    }

    /**
     * Instancie et retourne une step paiement
     * @param isHardAssert
     * @return
     */
    protected PaymentStepValue getNewPaymentStepValue(boolean isHardAssert) {
        return new PaymentStepValue(currentDriver, softAssert, isHardAssert);
    }

    // FIXME
    @AfterMethod
    /*protected void onMethodEnd(ITestResult result) {
        // On prend un screenshot pour l'attacher au rapport
        if (!isDriverClosed()) {
            attachScreenshot();
        }
    }*/

    /**
     * Renvoie vrai si le driver/navigateur est fermé
     * @return
     */
    private boolean isDriverClosed() {
        return currentDriver == null || currentDriver.getSessionId() == null;
    }


}
