package View;

import Helpers.Element.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BaseView {

    // Timeout
    protected static final int ELEMENT_MISSING_TIMEOUT = 15;
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
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, ELEMENT_MISSING_TIMEOUT);
        PageFactory.initElements(factory, baseView);
    }

    /**
     * Passe la proprieté CSS Zoom à 1 afin d'éviter les problèmes de clic
     */
    private void setZoomTo1() {
        driver.executeScript("document.querySelector(\"html\").setAttribute(\"style\",\"zoom : 1\")");
    }

    /**
     * Utilise Javascript pour changer l'attribut d'un élément
     * @param element
     * @param attName
     * @param attValue
     */
    protected void setAttribute(WebElement element, String attName, String attValue) {
        driver.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attName, attValue);
    }

    /**
     * Met le zoom à 1 et click sur l'élément
     * @param webElement
     * @param isMandatory
     */
    protected void click(WebElement webElement, boolean isMandatory) {
        // Nb de tentative
        int attemptCount = 0;
        WebElement elementToClick = webElement;
        while (true) {
            try {
                doClick(elementToClick);
                break;
            } catch (Exception e) {
                if (attemptCount > 10) {
                    if(isMandatory)
                    {
                        throw e;
                    }
                    break;
                }
                elementToClick = WebElementHelper.getElement(driver, By.id(elementToClick.getAttribute("id")));
                System.out.println(e.getClass() + " attempt to click n° : " + attemptCount + " on " + elementToClick.getAttribute("id"));
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
        WebElement webElem = WebElementHelper.waitUntilElementIsVisible(driver, ELEMENT_MISSING_TIMEOUT, By.xpath(XPath), isMandatory);
        // Click si existe
        if (webElem != null) {
            click(webElem, false);
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
        WebElement webElem = WebElementHelper.waitUntilElementIsVisible(driver, ELEMENT_MISSING_TIMEOUT, By.xpath(xPath), isMandatory);
        return webElem;
    }

    /**
     * Envoi le text au composant
     * @param element
     * @param keys
     */
    protected void sendKeys(WebElement element, String keys) {
        if (element != null && keys != null && !keys.isEmpty()) {
            element.sendKeys(keys);
        }
    }
}
