package Helpers.Element;

import org.openqa.selenium.*;
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
        //FIXME pabo
        Thread.sleep(1000);
        waitUntilElementIsVisible(driver, timeOutInSeconds, LOADING_XPATH, true);
        System.out.println("cache visible");
        waitUntilElementIsNotVisible(driver, timeOutInSeconds, LOADING_XPATH, true);
        System.out.println("cache chargé");
    }

    /**
     * Attend l'affichage du composant
     * @param driver
     * @param timeOutInSeconds
     * @param xPath
     */
    public static WebElement waitUntilElementIsVisible(WebDriver driver, int timeOutInSeconds, String xPath, boolean throwException) {
        WebElement elem = null;
        try {
            elem = new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        } catch (TimeoutException e) {
            if (throwException) {
                throw e;
            }
        }
        return elem;
    }

    /**
     * @param driver
     * @param timeOutInSeconds
     * @param xPath
     * @param throwException
     */
    public static void waitUntilElementIsNotVisible(WebDriver driver, int timeOutInSeconds, String xPath, boolean throwException) {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, timeOutInSeconds).ignoring(StaleElementReferenceException.class, ElementNotVisibleException.class);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
        } catch (TimeoutException e) {
            if (throwException) {
                throw e;
            }
        }
    }

    /**
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
