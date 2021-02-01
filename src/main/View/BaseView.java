package View;

import Helpers.Element.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BaseView {

    // Timeout
    protected static final int AJAX_ELEMENT_MISSING_TIMEOUT = 5;
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
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, AJAX_ELEMENT_MISSING_TIMEOUT);
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
        int exceptionCount = 0;
        try {
            doClick(webElement);
            // Peut arriver lorsque le DOM change/a changé lorsqu'on appelle le click
        } catch (Exception ex) {
            if (exceptionCount < 5) {
                exceptionCount++;
                // On récupère l'id de l'élément
                String id = webElement.getAttribute("id");
                // Cherche l'élément à partir de son id
                WebElement webElem = WebElementHelper.waitUntilElementIsVisible(driver, AJAX_ELEMENT_MISSING_TIMEOUT, By.id(id), true);
                // Tente de reclicker
                doClick(webElem);
            } else {
                throw ex;
            }
        }
    }

    private void doClick(WebElement elem)
    {
        // Met le zoom à 1 pour éviter les problemes de click
        setZoomTo1();
        // Clic clic
        elem.click();
    }

    /**
     * Recherche l'élément à partir de son XPath et click
     * @param XPath
     * @return true si l'élément est présent et clické
     */
    protected boolean findAndClickElement(String XPath, boolean isMandatory) {
        // On tente de récupèrer l'élement à partir du XPath
        WebElement webElem = WebElementHelper.waitUntilElementIsVisible(driver, AJAX_ELEMENT_MISSING_TIMEOUT, By.xpath(XPath), isMandatory);
        // Click si existe
        if (webElem != null) {
            click(webElem);
            return true;
        }
        return false;
    }

    /**
     * Renvoi l'élément si présent sur la view
     * @param xPath
     * @return
     */
    protected WebElement findElement(String xPath, boolean isMandatory) {
        // On tente de récupèrer l'élement à partir du XPath
        WebElement webElem = WebElementHelper.waitUntilElementIsVisible(driver, AJAX_ELEMENT_MISSING_TIMEOUT, By.xpath(xPath), isMandatory);
        return webElem;
    }
}
