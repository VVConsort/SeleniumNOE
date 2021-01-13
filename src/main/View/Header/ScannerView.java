package View.Header;

import Helpers.Input.InputHelper;
import View.BaseView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScannerView extends BaseView {

    // Délai d'attente en milli après avoir valider une saisie
    private static final int DELAY_AFTER_INPUT_MILLI = 1000;

    // XPath listener  saisie clavier
    private static final String KEY_LISTENER_XPATH = "//input[@id=\"_focusKeeper\"]";

    public ScannerView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Saisie et valide une valeure
     * @param value
     */
    public void typeAndValidateValue(String value) throws InterruptedException {
        WebElement listener = getListener();
        InputHelper input = new InputHelper();
        // Saisie la valeur
        input.type(value, listener);
        //FIXME : conditionner l'attente par la visibiltié d'un élément
        Thread.sleep(DELAY_AFTER_INPUT_MILLI);
    }

    /**
     * Retourne le listener clavier
     * @return
     */
    private WebElement getListener()
    {
        return Helpers.Element.WebElementHelper.getElement(driver, By.xpath(KEY_LISTENER_XPATH));
    }
}
