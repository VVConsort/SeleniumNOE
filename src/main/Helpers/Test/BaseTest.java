package Helpers.Test;

import Helpers.Test.TestSuiteProperties.PropertiesLoader;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseTest {

    // Soft assert
    protected  SoftAssert softAssert = new SoftAssert();
    // Liste contenant les erreurs d'assertion
    private  List<String> assertionError = new ArrayList<String>();
    // Driver Chrome
    protected ChromeDriver driver;


    /*public void run(IHookCallBack callBack, ITestResult testResult) {

        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            try {
                ReportHelper.attachScreenshot(driver, testResult.getMethod().getMethodName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    /**
     * Ajoute une nouvelle Step au test
     * @param name     Description de la step
     * @param runnable
     */
    public void step(String name, Runnable runnable) {
        String uuid = UUID.randomUUID().toString();
        StepResult result = new StepResult().setName(name);
        Allure.getLifecycle().startStep(uuid, result);
        StatusDetails statutDetails = new StatusDetails();
        // Prend un screenshot
        ReportHelper.attachScreenshot(driver);
        try {
            runnable.run();
            Allure.getLifecycle().updateStep(uuid, s -> s.setStatus(Status.PASSED));
        } catch (AssertionError e) {
            statutDetails.setMessage(e.getMessage());
            Allure.getLifecycle().updateStep(uuid, s -> s.setStatus(ResultsUtils.getStatus(e).orElse(Status.FAILED))
                    .setStatusDetails(statutDetails));
            assertionError.add("L'étape '" + name + "' a échoué : " + e.getMessage());
        } catch (Exception e) {
            statutDetails.setMessage(e.getMessage());
            Allure.getLifecycle().updateStep(uuid, s -> s.setStatus(ResultsUtils.getStatus(e).orElse(Status.BROKEN))
                    .setStatusDetails(statutDetails));
            assertionError.add("L'étape '" + name + "' a rencontré une exception : " + e.getMessage());
        } finally {
            Allure.getLifecycle().stopStep(uuid);
        }
    }

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
    protected void assertAll() {
        // On visualise les comparaisons fausses
        try {
            softAssert.assertAll();
        } catch (AssertionError err) {
            // Prend un screenshot
            ReportHelper.attachScreenshot(driver);
            // Fermeture du navigateur
            closeBrowser();
            throw err;
        }
    }

    @AfterTest
    protected void gatherAssertionErrors() {
        if (assertionError != null && !assertionError.isEmpty()) {
            throw new AssertionError(String.join("\n", assertionError));
        }
        // Fermeture du navigateur
        closeBrowser();
    }

    protected void onAfterTest() {
        // Si il y'a des assertions fausse, alors cela mettre le test en FAILED
        softAssert.assertAll();


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
