package View;

import Helpers.Element.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BaseView {

    // Timeout
    protected static final int AJAX_ELEMENT_MISSING_TIMEOUT = 15;
    // Driver
    protected ChromeDriver driver;

    /**
     * Set le driver et le timeout de chargement des WebElement
     * @param webDriver
     * @param baseView
     */
    protected void init(ChromeDriver webDriver, BaseView baseView) {
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
        // Nb max de tentative
        int attemptCount = 0;
        while (true) {
            try {
                doClick(webElement);
                break;
            } catch (Exception e) {
                if (attemptCount > 5) {
                    throw e;
                }
                attemptCount++;
            }
        }
    }

    private void doClick(WebElement elem) {
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
