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
        setZoomTo1();
        webElement.click();
    }

    /**
     * Clic sur l'élément si celui-ci est affiché à l'écran et retourne vrai si celui-ci est présent
     * @param XPath
     */
    protected boolean clickIfElementPresent(String XPath)
    {
        // On tente de récupèrer l'élement à partir du XPath
        WebElement continueLogBtn = WebElementHelper.getElement(driver, By.xpath(XPath));
        // Click si existe
        if(continueLogBtn != null)
        {
            click(continueLogBtn);
            return true;
        }
        return false;
    }
}
