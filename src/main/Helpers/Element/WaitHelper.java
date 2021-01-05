package Helpers.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Classe utilitaire pour l'attente
 */
public class WaitHelper {

    // XPath chargement
    private static final String LOADING_XPATH = "//*[@id=\"terminal_containerLoading_control\"]";


    /**
     * Attend que le chargement soit fini
     * @param timeOutInSeconds
     * @param driver
     */
    public static void waitUntilLoadIsFinished(WebDriver driver, int timeOutInSeconds) {
        waitForVisibleToInvisible(driver, timeOutInSeconds, LOADING_XPATH);
    }

    /**
     * Attend l'apparition d'un élément et sa disparition
     * @param driver
     * @param timeOutInSeconds
     * @param xPath
     */
    private static void waitForVisibleToInvisible(WebDriver driver, int timeOutInSeconds, String xPath) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        // On attend une premiere fois que la fenêtre de chargement apparaisse
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        // Et qu'elle disparaisse
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
    }

    /**
     * Attend l'affichage du composant
     * @param driver
     * @param timeOutInSeconds
     * @param xPath
     */
    public static void waitUntilComponentIsVisble(WebDriver driver , int timeOutInSeconds,String xPath)
    {
        WebDriverWait wait = new WebDriverWait(driver,timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
    }
}
