package View.Footer.Menu.Voucher;

import Helpers.Element.WaitHelper;
import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VoucherCodeInputView extends BaseView {

    // XPath bouton OK voucher incorrect
    private static final String INVALID_VOUCHER_POP_UP_XPATH = "//*[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup\"]";
    // Bouton OK
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_OBDISCP_modalInsertCouponCode_footer_okButton\"]")
    private WebElement okButton;
    // Saisie code
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_OBDISCP_modalInsertCouponCode_body_formElementCouponcode_coreElementContainer_couponcode\"]")
    private WebElement voucherCodeInput;

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
    public InvalidVoucherView clickOk() {
        InvalidVoucherView result = null;
        super.click(okButton);
        // On attend éventuellement l'affichage de la popup "Bon d'achat invalide"
        WebElement invalidVoucherBtn = WaitHelper.waitUntilElementIsVisible(driver, 10, INVALID_VOUCHER_POP_UP_XPATH, false);
        // Si le coupon n'est pas valide
        if (invalidVoucherBtn != null) {
            // On retourne une vue "Bon d'achat invalide"
            result = new InvalidVoucherView(driver);
        }
        return result;
    }

}
