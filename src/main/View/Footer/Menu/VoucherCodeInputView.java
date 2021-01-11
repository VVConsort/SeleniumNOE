package View.Footer.Menu;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VoucherCodeInputView extends BaseView {

    // Saisie code
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_OBDISCP_modalInsertCouponCode_body_formElementCouponcode_coreElementContainer_couponcode\"]")
    private WebElement voucherCodeInput;

    // Bouton OK
    @FindBy(xpath ="//*[@id=\"terminal_containerWindow_pointOfSale_OBDISCP_modalInsertCouponCode_footer_okButton\"]")
    private WebElement okButton;

    public VoucherCodeInputView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Entre le code du bon d'achat
     * @param voucherCode
     */
    public void enterVoucherCode(String voucherCode) {
        voucherCodeInput.sendKeys(voucherCode);
    }

    /**
     * Clic sur le boutton ok
     */
    public void clickOkButton()
    {
        super.click(okButton);
    }

}
