package Step;

import Helpers.Loading.LoadingHelper;
import Helpers.Test.ReportHelper;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import Step.Value.LoggingStepValue;
import View.BackOffice.LoggingPage;
import View.BrowserLauncher;
import View.Log.LogScreenView;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;

public class LoggingStep {

    // Timeout d'ouverture du cache
    private static final int CACHE_LOADING_TIMEOUT_IN_SECONDS = 600;

    /**
     * Lancement d'OB avec le cache et l'utilisateur par défaut (définie dans les propriètes de la testsuite)
     * @return le webdriver crée
     */
    @Step("Lancement et log sur OpenBravo")
    public static ChromeDriver launchAndLogOpenBravo() throws MalformedURLException, InterruptedException {

        // Récupération du driver et lancement de la caisse
        ChromeDriver driver = BrowserLauncher.launchOpenBravoWithCache();
        // Ecran de loggin
        LogScreenView logScreenView = new LogScreenView(driver);
        // Set l'utilisateur
        logScreenView.setUserName(TestSuiteProperties.USERNAME);
        // Set le mdp
        logScreenView.setPassword(TestSuiteProperties.PASSWORD);
        // Connection
        logScreenView.clickConnexion();
        // On attend que le cache soit chargé
        LoadingHelper.waitUntilLoadIsFinished(driver, CACHE_LOADING_TIMEOUT_IN_SECONDS);
        ReportHelper.attachScreenshot(driver);
        // Retourne le driver
        return driver;
    }

    @Step("Log sur OpenBravo avec la caisse {step.terminalKey}")
    public static ChromeDriver launchAndLogOpenBravo(LoggingStepValue step) throws MalformedURLException, InterruptedException {

        // Récupération du driver et lancement de la caisse
        ChromeDriver driver = BrowserLauncher.launchOpenBravoWithCache(step.url, step.terminalKey, step.chromeProfilePath, step.chromeProfile);
        // Ecran de loggin
        LogScreenView logScreenView = new LogScreenView(driver);
        // Set l'utilisateur
        logScreenView.setUserName(step.username);
        // Set le mdp
        logScreenView.setPassword(step.password);
        // Connection
        logScreenView.clickConnexion();
        // On attend que le cache soit chargé
        LoadingHelper.waitUntilLoadIsFinished(driver, CACHE_LOADING_TIMEOUT_IN_SECONDS);
        ReportHelper.attachScreenshot(driver);
        return driver;
    }

    @Step("Lancement et log sur le BackOffice")
    public static ChromeDriver launchAndLogBackOffice() {
        // Récupération du driver et lancement de la caisse
        ChromeDriver driver = BrowserLauncher.launchBackOffice();
        // Ecran de loggin
        LoggingPage logPage = new LoggingPage(driver);
        // Set l'utilisateur
        logPage.setUserName(TestSuiteProperties.USERNAME);
        // Set le mdp
        logPage.setPassword(TestSuiteProperties.PASSWORD);
        // Connection
        logPage.clickLogIn();
        return driver;
    }

}
