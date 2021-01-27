package View.Footer.Menu.Voucher;

import View.BaseView;
import org.openqa.selenium.WebDriver;

public class InvalidVoucherView extends BaseView {

    // Xpath Boutton OK
    private static final String BTN_OK_XPATH = "//*[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_confirmationPopup_btnOk\"]";

    public InvalidVoucherView(WebDriver driver) {
        init(driver, this);
    }

    public void clickOK() {
        findAndClickElement(BTN_OK_XPATH, true);
    }
}
