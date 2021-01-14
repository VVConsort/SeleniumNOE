package View.Footer.Menu.Voucher;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InvalidVoucherView extends BaseView {

    // Xpath Boutton OK
    private static final String BTN_OK_XPATH = "//*[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_confirmationPopup_btnOk\"]";


    public InvalidVoucherView(WebDriver driver) {
        init(driver, this);
    }

    public void clickOK()
    {
        super.searchAndClickElement(BTN_OK_XPATH);
    }
}
