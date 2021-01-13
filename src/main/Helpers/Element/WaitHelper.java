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
    public static void waitUntilLoadIsFinished(WebDriver driver, int timeOutInSeconds) throws InterruptedException {
        waitUntilElementIsVisible(driver, timeOutInSeconds, LOADING_XPATH, false);
        waitUntilElementIsNotVisible(driver, timeOutInSeconds, LOADING_XPATH, false);
    }

    /**
     * Attend l'affichage du composant
     * @param driver
     * @param timeOutInSeconds
     * @param xPath
     */
    public static void waitUntilElementIsVisible(WebDriver driver, int timeOutInSeconds, String xPath, boolean throwException) {
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
    public static void waitUntilElementIsNotVisible(WebDriver driver, int timeOutInSeconds, String xPath, boolean throwException) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
        } catch (TimeoutException e) {
            if (throwException) {
                throw e;
            }
        }
    }

    /**
     *
     * @param driver
     * @param timeOutInSeconds
     * @param webElement
     * @param throwException
     */
    public static void waitUntilElementIsNotVisible(WebDriver driver, int timeOutInSeconds, WebElement webElement, boolean throwException) {
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
