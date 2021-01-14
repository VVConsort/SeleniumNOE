package Step;

import Helpers.Element.WaitHelper;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import View.OpenBravoLauncher;
import View.Log.LogScreenView;
import Step.Value.LoggingStepValue;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class LoggingStep {

    // Timeout d'ouverture du cache
    private static final int CACHE_LOADING_TIMEOUT_IN_SECONDS = 120;

    /**
     * Lancement d'OB avec le cache et l'utilisateur par défaut (définie dans les propriètes de la testsuite)
     * @return le webdriver crée
     */
    @Step("Log sur OpenBravo")
    public static WebDriver launchAndLogOB() throws MalformedURLException, InterruptedException {

        // Récupération du driver et lancement de la caisse
        WebDriver driver = OpenBravoLauncher.launchOpenBravoWithCache();
        // Ecran de loggin
        LogScreenView logScreenView = new LogScreenView(driver);
        // Set l'utilisateur
        logScreenView.setUserName(TestSuiteProperties.USERNAME);
        // Set le mdp
        logScreenView.setPassword(TestSuiteProperties.PASSWORD);
        // Connection
        logScreenView.clickConnexion();
        // On attend que le cache soit chargé
        WaitHelper.waitUntilLoadIsFinished(driver, CACHE_LOADING_TIMEOUT_IN_SECONDS);
        // Retourne le driver
        return driver;
    }

    @Step("Log sur OpenBravo avec la caisse {step.terminalKey}")
    public static WebDriver launchAndLogOB(LoggingStepValue step) throws MalformedURLException, InterruptedException {

        // Récupération du driver et lancement de la caisse
        WebDriver driver = OpenBravoLauncher.launchOpenBravoWithCache(step.url, step.terminalKey, step.chromeProfilePath, step.chromeProfile);
        // On attend le chargement de la page
        // Ecran de loggin
        LogScreenView logScreenView = new LogScreenView(driver);
        // Set l'utilisateur
        logScreenView.setUserName(step.username);
        // Set le mdp
        logScreenView.setPassword(step.password);
        // Connection
        logScreenView.clickConnexion();
        // On attend que le cache soit chargé
        WaitHelper.waitUntilLoadIsFinished(driver, CACHE_LOADING_TIMEOUT_IN_SECONDS);
        return driver;
    }

}
