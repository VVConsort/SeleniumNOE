package Helpers.Loading;

import Helpers.Element.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoadingHelper {
    // XPath chargement cache en cours
    private static final String DISPLAYED_LOADING_XPATH = "//*[@id='terminal_containerLoading'][not(contains(@style,'display: none'))]";
    // XPath chargemennt cache finie
    private static final String HIDDEN_LOADING_XPATH = "//*[@id='terminal_containerLoading'][contains(@style,'display: none')]";
    // Timeout pour l'apparation du second chargement
    private static final int VISIBILITY_TIME_OUT = 2;

    /**
     * Attend le chargement du cache
     * @param timeOutInSeconds
     * @param driver
     */
    public static void waitUntilLoadIsFinished(ChromeDriver driver, int timeOutInSeconds) throws InterruptedException {
        WebElement loadingElem = null;
        // Tant qu'il y a un chargement
        do {
            // On attend que l'élément de chargement s'affiche
            loadingElem = WebElementHelper.waitUntilElementIsVisible(driver, VISIBILITY_TIME_OUT, By.xpath(DISPLAYED_LOADING_XPATH), false);
            // Si un chargement est présent
            if (loadingElem != null) {
                // Alors on attend sa fin
                WebElementHelper.waitUntilElementIsVisible(driver, timeOutInSeconds, By.xpath(HIDDEN_LOADING_XPATH), false);
            }
        } while (loadingElem != null);

    }
}


