package Pages;

import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class OpenBravoLauncher {

    public static final String USERNAME = "valentinvanhaute1";
    public static final String AUTOMATE_KEY = "UfWYC3LfWLWjmy1qmn6n";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    /**
     * Lance Chrome à partir des paramètres spécifiés
     * @param url
     * @param terminalKey
     * @param chromeProfilePath
     * @param chromeProfileName
     * @return
     * @throws MalformedURLException
     */
    public static WebDriver launchOpenBravoWithCache(String url, String terminalKey, String chromeProfilePath, String chromeProfileName) throws MalformedURLException {
        /** Test
         *
         */
        // Chemin vers le driver
        String pathToChromeDriver = "src/main/resources/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        // Chemin vers le profil contenant le cache OB
        ChromeOptions chromeProfile = new ChromeOptions();

        chromeProfile.addArguments("user-data-dir=" + chromeProfilePath);
        // Le nom de dossier du profil
        chromeProfile.addArguments("profile-directory=" + chromeProfileName);
        //RemoteWebDriver driver = new ChromeDriver(chromeProfile);
        WebDriver driver = new ChromeDriver(chromeProfile);
        // Ajout des paramètres à l'URL
        driver.get(url + "/?terminal=" + terminalKey);
        // Retourne le driver
        return driver;
    }

    /**
     * On lance OB en utilisant URL, caisse et profile Chrome définies dans les Properties de la TestSuite
     * @return
     */
    public static WebDriver launchOpenBravoWithCache() throws MalformedURLException {
        return launchOpenBravoWithCache(TestSuiteProperties.OB_POS_URL, TestSuiteProperties.TERMINAL_KEY, TestSuiteProperties.CHROME_PROFILE_PATH, TestSuiteProperties.CHROME_PROFILE);
    }
}

