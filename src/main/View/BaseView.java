package View;

import Helpers.Element.WebElementHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BaseView {

    // Timeout
    private static final int AJAX_WEBELEMENT_LOADING_TIMEOUT = 30;
    // Driver
    protected WebDriver driver;

    /**
     * Set le driver et le timeout de chargement des WebElement
     * @param webDriver
     * @param baseView
     */
    protected void init(WebDriver webDriver, BaseView baseView) {
        this.driver = webDriver;
        // Lazy loading
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, AJAX_WEBELEMENT_LOADING_TIMEOUT);
        PageFactory.initElements(factory, baseView);
    }

    /**
     * Passe la proprieté CSS Zoom à 1 afin d'éviter les problèmes de clic
     */
    private void setZoomTo1() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector(\"html\").setAttribute(\"style\",\"zoom : 1\")");
    }

    /**
     * Met le zoom à 1 et click sur l'élément
     * @param webElement
     */
    protected void click(WebElement webElement) {
        // Met le zoom à 1 pour éviter les problemes de click
        setZoomTo1();
        try {
            webElement.click();
        }
        // Si le DOM a changé on  re recherche l'élément
        catch (Exception ex) {
            // récupère l'ID de l'élément
            String id = webElement.getAttribute("id");
            // On recherche l'élément à partir de l'id extraite
            webElement = WebElementHelper.getElement(driver, By.id(id));
            // reclick
            click(webElement);
        }
    }

    /**
     * Recherche l'élément à partir de son XPath et click
     * @param XPath
     * @return true si l'élément est présent et clické
     */
    protected boolean searchAndClickElement(String XPath) {
        // On tente de récupèrer l'élement à partir du XPath
        WebElement continueLogBtn = WebElementHelper.getElement(driver, By.xpath(XPath));
        // Click si existe
        if (continueLogBtn != null) {
            click(continueLogBtn);
            return true;
        }
        return false;
    }
}
