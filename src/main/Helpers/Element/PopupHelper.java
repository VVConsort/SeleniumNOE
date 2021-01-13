package Helpers.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Classe utilitaire pour les popups génériques
 */
public class PopupHelper {
    // XPath du bouton 'Ok'
    private static final String okBtnXPath = "//*[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_confirmationPopup_btnOK\"]";
    // Timeout en secondes
    private static final int TIME_OUT_IN_SECONDS = 10;

    /**
     * Clic sur le bouton 'Ok' de la pop up
     * @param driver
     */
    public static void clickOkBtn(WebDriver driver) {
        // Attenc l'apparation de la pop up
        WaitHelper.waitUntilElementIsVisible(driver, TIME_OUT_IN_SECONDS, okBtnXPath,false);
        // Recherche le bouton OK par XPath
        WebElement okBtn = Helpers.Element.WebElementHelper.getElement(driver, By.xpath(okBtnXPath));
        // Clic
        if (okBtn != null) {
            okBtn.click();
        }
    }
}

