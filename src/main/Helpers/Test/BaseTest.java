package Helpers.Test;

import Helpers.Rest.XRay.XRayRestHelper;
import Helpers.Test.Properties.Loader.TestPropertiesLoader;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class BaseTest {

    // Driver Chrome
    protected static ChromeDriver driver;
    // Soft assert
    protected SoftAssert softAssert = new SoftAssert();

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
        TestPropertiesLoader pro = new TestPropertiesLoader(propertiesFilePath);
        pro.setProperties();
    }

    /**
     * Vide le ticket
     */
    protected void deleteTicket() throws InterruptedException {
        TicketStep.deleteTicket(driver);
    }

    /**
     * Ferme le navigateur
     */
    @Step("Fermeture du navigateur")
    protected void closeBrowser() {
        if (!isDriverClosed()) {
            // Ferme le navigateur
            driver.quit();
        }
    }

    /**
     * Fait apparaitre les comparaisons fausses et met éventuellement le test en FAILED
     */
    @Step("Vérification des valeurs attendues")
    protected void assertAll() throws AssertionError, IOException {
        // On visualise les comparaisons fausses
        try {
            softAssert.assertAll();
        } catch (AssertionError err) {
            closeBrowser();
            throw err;
        }
    }

    @AfterTest
    protected void onAfterTest(ITestContext result) throws IOException {
        // On test les comparaisons
        assertAll();
        // Fermeture du navigateur
        closeBrowser();
        // Envoie REST à jira
        // postCucumberTestExecutionToXRay(result.getName());
    }

    /**
     * Envoie le report d'éxécution Cucumber vers XRay
     * @throws IOException
     */
    @Step("Mise à jours du statut d'éxécution du test sur Jira/XRay")
    private void postCucumberTestExecutionToXRay(String testName) throws IOException {
        XRayRestHelper.postReportToXRay(testName);
    }

    /**
     * Instancie et retourne une step
     * @param isHardAssert
     * @return
     */
    protected BaseStepValue getNewBaseStepValue(boolean isHardAssert) {
        return new BaseStepValue(driver, softAssert, isHardAssert);
    }

    /**
     * Instancie et retourne une step promo
     * @param isHardAssert
     * @return
     */
    protected DiscountStepValue getNewDiscountStepValue(boolean isHardAssert) {
        return new DiscountStepValue(driver, softAssert, isHardAssert);
    }

    /**
     * Instancie et retourne une step paiement
     * @param isHardAssert
     * @return
     */
    protected PaymentStepValue getNewPaymentStepValue(boolean isHardAssert) {
        return new PaymentStepValue(driver, softAssert, isHardAssert);
    }

    /**
     * Renvoie vrai si le driver/navigateur est fermé
     * @return
     */
    private boolean isDriverClosed() {
        return driver == null || driver.getSessionId() == null;
    }
}
