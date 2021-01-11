package View.Footer.Menu.Voucher;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InvalidVoucherView extends BaseView {

    // Boutton OK
    @FindBy (xpath = "//*[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_confirmationPopup_btnOk\"]")
    WebElement okButton;


    public InvalidVoucherView(WebDriver driver) {
        init(driver, this);
    }

    public void clickOK()
    {
        super.click(okButton);
    }
}
