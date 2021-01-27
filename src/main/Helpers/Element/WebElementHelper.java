package Helpers.Element;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Classe utilitaire pour la recherche de WebElement
 */
public class WebElementHelper {

    // visible :
    // //*[not(contains(@style,'display: none;'))]

    //  invisible : //*[@id='terminal_containerLoading'][contains(@style,'display: none')]
////*[@id='terminal_containerLoading'][contains(@style,'display: none')]")

    // visible //*[@id='terminal_containerLoading'][not(contains(@style,'display: none'))]

    /**
     * Retourne un element
     * @param driver
     * @param by
     * @return
     */
    public static WebElement getElement(WebDriver driver, By by) {
        WebElement webElem = null;
        try {
            webElem = driver.findElement(by);
        } catch (NoSuchElementException e) {
            return webElem;
        }
        return webElem;
    }

    /**
     * Retourne l'élement à partir de son text
     * @param label
     * @return
     */
    public static WebElement getElementFromText(String label, WebDriver driver) {
        // Recherche l'élement à partir du texte
        return getElement(driver, By.xpath("*//div[contains(text(),'" + label + "')]"));
    }

    /**
     * Retourne le texte de l'élement à partir de son XPath
     * @param driver
     * @param by
     * @return
     */
    public static String getTextFromElement(WebDriver driver, By by) {
        WebElement webElem;
        try {
            webElem = driver.findElement(by);
        } catch (NoSuchElementException e) {
            return "";
        }
        return webElem.getText();
    }

    /**
     * Retourne l'élément à partir du texte et de l'id
     * @param text
     * @param driver
     * @return
     */
    public static WebElement getElementFromIdAndText(String id, String text, WebDriver driver) {
        String Xpath = "*//div[starts-with(@id,'" + id + "') and contains (text(),'" + text + "')]";
        return getElement(driver, By.xpath(Xpath));
    }

    /**
     * @param driver
     * @param timeOutInSeconds
     * @param throwException
     */
    public static void waitUntilElementIsNotPresent(WebDriver driver, int timeOutInSeconds, By by, boolean throwException) {
        try {
            System.out.println("wait for invisibility");
            doWaitForInvisibility(driver, by, timeOutInSeconds);
        } catch (Throwable e) {
            if (throwException) {
                throw e;
            }
        }
        System.out.println("is invisible");
    }

    /**
     * Attend l'affichage du composant
     * @param driver
     * @param timeOutInSeconds
     */
    public static WebElement waitUntilElementIsVisible(WebDriver driver, int timeOutInSeconds, By by, boolean throwException) {
        WebElement elem = null;
        try {
            elem = doWaitForVisibility(driver, by, timeOutInSeconds);
        } catch (Throwable e) {
            if (throwException)
                throw e;
        }
        return elem;
    }

    /**
     * @param driver
     * @param by
     * @param timeOutInSec
     * @return
     */
    private static WebElement doWaitForVisibility(WebDriver driver, By by, int timeOutInSec) {
        WebElement elem = null;
        Wait wait = new FluentWait<>(driver)
                .withTimeout(timeOutInSec, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class);

        elem = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(by);
            }
        });
        return elem;
    }

    /**
     * @param driver
     * @param by
     * @param timeOutInSec
     * @return
     */
    private static void doWaitForInvisibility(WebDriver driver, By by, int timeOutInSec) {

        Wait wait = new FluentWait<>(driver)
                .withTimeout(timeOutInSec, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class);
        // Attend que l'élément ne soit plus affiché
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return !driver.findElement(by).isDisplayed();
            }
        });
    }

}
