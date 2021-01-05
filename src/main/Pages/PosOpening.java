package Pages;

import Helpers.Element.PopupHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page 'Ouverture de caisse'
 */
public class PosOpening extends BasePage {

    // Boutton 'Suivant'
    //@FindBy(xpath = "//button[@id=\"terminal_containerWindow_loginCashUp_cashupMultiColumn_leftToolbar_leftToolbar_toolbar_btnNext\"]")
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_cashUp_closeCashMultiColumn_leftToolbar_leftToolbar_toolbar_btnNext\"]")
    WebElement nextBtn;

    //*[@id="terminal_containerWindow_cashUp_closeCashMultiColumn_leftToolbar_leftToolbar_toolbar_btnNext"]

    // Boutton 'Valider'
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_loginCashUp_cashupMultiColumn_leftPanel_listPaymentMethods_paymentsList_tbody_control_stepRenderPaymentsLine_buttonOk\"]")
    WebElement validateCashBtn;

    // Boutton 'OK' de la pop Succes d'ouverture
    @FindBy(xpath = "//button[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_confirmationPopup_btnOK\"]")
    WebElement successOpeningOkBtn;

    // Label ouverture de caisse
    //String posOpeningLabelXPath = "//*[@id=\"terminal_containerWindow_loginCashUp_cashupMultiColumn_rightToolbar_rightToolbar_toolbar_btnCountCash_label_labelText_0\"]";

    public PosOpening(WebDriver webDriver) {
        init(webDriver, this);
    }

    /**
     * Ouvre la caisse en validant le montant en espece
     */
    public void validateCashAndOpenPos() {
        // Valide la pop et entre en ouverture de caisse
        PopupHelper.clickOkBtn(driver);
        // Clic bouton "Suivant"
        clickNext();
        // Valide le détail monnaie
        clickValidateCash();
        // Appuie sur finaliser
        clickNext();
        // Confirme le succès de l'ouverture
        PopupHelper.clickOkBtn(driver);
    }

    /**
     * Click sur le boutton 'Suivant'
     */
    public void clickNext() {
        super.click(nextBtn);
    }

    /**
     * Click sur le détail 'Monnaie/Espece"
     */
    public void clickValidateCash() {
        super.click(validateCashBtn);
    }

}
