package View.Footer.Menu.Voucher;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class RemoveVoucherView extends AddVoucherView {

    // Saisie code
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_OBDISCP_modalRemoveCouponCode_body_formElementCouponcode_coreElementContainer_couponcode\"]")
    protected WebElement voucherCodeInput;

    // Bouton OK
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_OBDISCP_modalRemoveCouponCode_footer_okRemoveButton\"]")
    protected WebElement okButton;

    public RemoveVoucherView(ChromeDriver driver) {
        super(driver);
        super.voucherCodeInput = this.voucherCodeInput;
        super.okButton = this.okButton;
    }

    /**
     * Entre le code du bon d'achat
     * @param voucherCode
     */
    /*public void enterVoucherCode(String voucherCode) {
        voucherCodeInput.sendKeys(voucherCode);
    }*/

}
