package Helpers.Element;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
    public static WebElement getElement(ChromeDriver driver, By by) {
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
     * @param text
     * @return
     */
    public static WebElement getElementFromText(ChromeDriver driver, String text, int timeOutInSec, boolean throwException) {
        // Recherche l'élement à partir du texte
        return waitUntilElementIsVisible(driver, timeOutInSec, By.xpath("//*[contains(text(),'" + text + "')]"), throwException);
    }

    /**
     * Retourne le texte de l'élement à partir de son XPath
     * @param driver
     * @param by
     * @return
     */
    /*public static String getTextFromElement(ChromeDriver driver, By by) {
        WebElement webElem;
        try {
            webElem = driver.findElement(by);
        } catch (NoSuchElementException e) {
            return "";
        }
        return webElem.getText();
    }*/

    /**
     * Retourne l'élément à partir du texte et de l'id
     * @param text
     * @param driver
     * @return
     */
    public static WebElement getElementFromIdAndText(String id, String text, ChromeDriver driver, int timeOutInSec, boolean throwException) {
        String Xpath = "*//*[starts-with(@id,'" + id + "') and contains (text(),'" + text + "')]";
        return waitUntilElementIsVisible(driver, timeOutInSec, By.xpath(Xpath), throwException);
    }

    /**
     * @param driver
     * @param timeOutInSeconds
     * @param throwException
     */
    public static void waitUntilElementIsNotPresent(ChromeDriver driver, int timeOutInSeconds, By by, boolean throwException) {
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
    public static WebElement waitUntilElementIsVisible(ChromeDriver driver, int timeOutInSeconds, By by, boolean throwException) {
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
     * Attend que le composant ait le texte attendu
     * @param expectedText
     * @param webElem
     * @param timeOutInSeconds
     * @param throwException
     * @return
     */
    public static String waitUntilExpectedText(String expectedText, WebElement webElem, int timeOutInSeconds, boolean throwException) {
        String text = null;
        // Nb de tentative
        int attemptCount = 0;
        if (webElem != null) {
            while (true) {
                try {
                    text = doWaitForExpectedText(expectedText, webElem, timeOutInSeconds);
                    break;
                } catch (Exception e) {
                    // Si le texte attendu n'est pas arrivé
                    if (e.getClass().equals(TimeoutException.class)) {
                        if (throwException) {
                            throw e;
                        }
                        break;
                        // Si on excède le nb max de tentative
                    } else if (attemptCount > 10) {
                        if (throwException) {
                            throw e;
                        }
                        break;
                    }
                    System.out.println(e.getClass() + " attempt to get text n° : " + attemptCount + " on " + webElem.getAttribute("id"));
                    attemptCount++;
                }
            }
            try {
                return text == null ? webElem.getText() : text;
            } catch (StaleElementReferenceException e) {
                return text;
            }
        }
        return text;
    }

    /**
     * @param driver
     * @param by
     * @param timeOutInSec
     * @return
     */
    private static WebElement doWaitForVisibility(ChromeDriver driver, By by, int timeOutInSec) {
        WebElement elem = null;
        Wait wait = new FluentWait<>(driver)
                .withTimeout(timeOutInSec, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(ElementNotInteractableException.class);
        elem = (WebElement) wait.until(new Function<ChromeDriver, WebElement>() {
            public WebElement apply(ChromeDriver driver) {
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
    private static void doWaitForInvisibility(ChromeDriver driver, By by, int timeOutInSec) {
        Wait wait = new FluentWait<>(driver)
                .withTimeout(timeOutInSec, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class);
        // Attend que l'élément ne soit plus affiché
        wait.until(new Function<ChromeDriver, Boolean>() {
            public Boolean apply(ChromeDriver driver) {
                return !driver.findElement(by).isDisplayed();
            }
        });
    }

    /**
     * Attend que le composant ait le texte souhaité
     * @param expectedText
     * @param webElem
     * @param timeOutInSec
     * @return
     */
    private static String doWaitForExpectedText(String expectedText, WebElement webElem, int timeOutInSec) {
        String text = "";
        if (webElem != null) {
            Wait wait = new FluentWait<>(webElem)
                    .withTimeout(timeOutInSec, TimeUnit.SECONDS)
                    .pollingEvery(500, TimeUnit.MILLISECONDS)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(ElementNotVisibleException.class);
            // Attend que le texte ait la valeure attendue
            text = (String) wait.until(new Function<WebElement, String>() {
                public String apply(WebElement webElem) {
                    System.out.println(webElem.getAttribute("id") + " text : " + webElem.getText() + " expected text : " + expectedText);
                    if (webElem.getText().trim().equals(expectedText)) {
                        return webElem.getText();
                    }
                    // Si l'élément n'est pas visible à l'écran (getText() retourne null sur un élément caché/non affiché)
                    else if (webElem.getAttribute("textContent").trim().equals(expectedText)) {
                        return webElem.getAttribute("textContent").trim();
                    }
                    return null;
                }
            });
        }
        return text;
    }

    /**
     * Retourne l'ID de l'élément à partir de son texte
     * @param label
     * @return
     */
    public static String getElementIdByText(String label, ChromeDriver driver) {
        String result = "";
        // Recherche l'élement 'label' à partir du texte
        WebElement elem = getElementFromText(driver, label, 5, false);
        // Blindage
        if (elem != null) {
            // Affectation de l'id de l'élement
            result = elem.getAttribute("id");
        }
        return result;
    }

}
