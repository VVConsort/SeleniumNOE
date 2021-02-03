package View.Header;

import Helpers.Input.InputHelper;
import View.BaseView;
import org.openqa.selenium.chrome.ChromeDriver;

public class ScannerView extends BaseView {

    // Délai d'attente en milli après avoir valider une saisie
    private static final int DELAY_AFTER_INPUT_MILLI = 1500;

    public ScannerView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Saisie et valide une valeure
     * @param value
     */
    public void typeAndValidateValue(String value) throws InterruptedException {
        InputHelper input = new InputHelper();
        // Saisie la valeur
        input.type(value, driver);
        //FIXME : conditionner l'attente par la visibiltié d'un élément
        Thread.sleep(DELAY_AFTER_INPUT_MILLI);
    }
}
