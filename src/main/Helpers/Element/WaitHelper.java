package Helpers.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        waitUntilComponentIsVisble(driver, timeOutInSeconds, LOADING_XPATH, false);
        waitUntilComponentIsNotVisible(driver, timeOutInSeconds, LOADING_XPATH, false);
        //waitForVisibleToInvisible(driver, timeOutInSeconds, LOADING_XPATH);
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
    public static void waitUntilComponentIsVisble(WebDriver driver, int timeOutInSeconds, String xPath, boolean throwException) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        } catch (TimeoutException e) {
            if (throwException) {
                throw e;
            }
        }
    }

    /**
     * @param driver
     * @param timeOutInSeconds
     * @param xPath
     * @param throwException
     */
    public static void waitUntilComponentIsNotVisible(WebDriver driver, int timeOutInSeconds, String xPath, boolean throwException) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
        } catch (TimeoutException e) {
            if (throwException) {
                throw e;
            }
        }
    }
    public static void waitUntilComponentIsNotVisible(WebDriver driver, int timeOutInSeconds, WebElement webElement, boolean throwException) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.invisibilityOf(webElement));
        } catch (TimeoutException e) {
            if (throwException) {
                throw e;
            }
        }
    }
}
