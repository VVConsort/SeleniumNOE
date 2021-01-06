package View;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BasePage {

    // Timeout
    private static final int TIME_OUT = 30;
    // Driver
    protected WebDriver driver;

    /**
     * Set le driver et le timeout de chargement des WebElement
     * @param webDriver
     * @param basePage
     */
    protected void init(WebDriver webDriver, BasePage basePage) {
        this.driver = webDriver;
        // Lazy loading
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, TIME_OUT);
        PageFactory.initElements(factory, basePage);
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
}
