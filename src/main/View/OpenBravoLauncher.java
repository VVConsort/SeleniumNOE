package View;

import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;

public class OpenBravoLauncher {

    /**
     * Lance Chrome à partir des paramètres spécifiés
     * @param url
     * @param terminalKey
     * @param chromeProfilePath
     * @param chromeProfileName
     * @return
     * @throws MalformedURLException
     */
    public static ChromeDriver launchOpenBravoWithCache(String url, String terminalKey, String chromeProfilePath, String chromeProfileName) throws MalformedURLException {
        // Chemin vers le driver
        String pathToChromeDriver = "src/main/resources/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        // Chemin vers le profil contenant le cache OB
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-data-dir=" + chromeProfilePath);
        // Le nom de dossier du profil
        chromeOptions.addArguments("profile-directory=" + chromeProfileName);
        // Lance Chrome en plein écran, sinon certains boutons ne seront pas clickable
        chromeOptions.addArguments("start-maximized"); // open Browser in maximized mode
        chromeOptions.addArguments("disable-infobars"); // disabling infobars
        chromeOptions.addArguments("--disable-extensions"); // disabling extensions
        chromeOptions.addArguments("--disable-gpu"); // applicable to windows os only
        chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model
        //chromeOptions.addArguments("--start-maximized");
        //RemoteWebDriver driver = new ChromeDriver(chromeProfile);
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        // Ajout des paramètres à l'URL
        driver.get(url + "/?terminal=" + terminalKey);
        // Retourne le driver
        return driver;
    }

    /**
     * On lance OB en utilisant URL, caisse et profile Chrome définies dans les Properties de la TestSuite
     * @return
     */
    public static ChromeDriver launchOpenBravoWithCache() throws MalformedURLException {
        return launchOpenBravoWithCache(TestSuiteProperties.OB_POS_URL, TestSuiteProperties.TERMINAL_KEY, TestSuiteProperties.CHROME_PROFILE_PATH, TestSuiteProperties.CHROME_PROFILE);
    }
}

