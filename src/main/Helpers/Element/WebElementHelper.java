package Helpers.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Classe utilitaire pour la recherche de WebElement
 */
public class WebElementHelper {


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
////*[@id="terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_payments_tbody_control_renderPaymentLine3_removePayment"]
    /**
     * Retourne l'élement à partir de son text
     * @param label
     * @return
     */
    public static WebElement getElementFromText(String label, WebDriver driver) {
        // Recherche l'élement 'label' à partir du texte
        return getElement(driver, By.xpath("*//div[text()='" + label + "']"));
        //*[@id="terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_payments_tbody_control_renderPaymentLine2_name"]
    }

    public static WebElement getElementFromTextAndClass(String label,WebDriver driver)
    {
        String Xpath = "*//div[starts-with(@id,'terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_payments') and contains (text(),'"+label+"')]";
        return getElement(driver,By.xpath(Xpath));
    }
    ////*[@id="terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_payments_tbody_control_renderPaymentLine3_name"]

    //div[starts-with(@id,'terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent') and contains (text(),'Avoir')]")
}
