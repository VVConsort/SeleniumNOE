package View.Log;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page 'Ouverture de caisse'
 */
public class PosOpeningView extends BaseView {

    // XPath du boutton pop up "Entrer en ouverture de caisse"
    private static final String ENTER_POS_OPENING_POPUP_BTN_XPATH = "//button[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_confirmationPopup_btnOK\"]";
    // XPath "Confirmer date d'ouverture de la caisse"
    private static final String CONFIRM_POS_OPENING_DATE_BTN_XPATH = "//*[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_continueLogin\"]";
    // XPath boutton ok "Différence monnaie"
    private static final String APPROVAL_REASON_OK_BTN_XPATH = "//*[@id=\"terminal_containerWindow_loginCashUp_POSS_modalApprovalReason_footer_btnModalApprovalReasonAccept\"]";

    //*[@id="terminal_containerWindow_loginCashUp_POSS_modalApprovalReason_footer_btnModalApprovalReasonAccept"]

    // Boutton suivant
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_loginCashUp_cashupMultiColumn_leftToolbar_leftToolbar_toolbar_btnNext\"]")
    private WebElement nextBtn;
    // Boutton 'Valider monnaie'
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_loginCashUp_cashupMultiColumn_leftPanel_listPaymentMethods_paymentsList_tbody_control_stepRenderPaymentsLine_buttonOk\"]")
    private WebElement validateCashBtn;

    // XPath boutton "OK" cloture de caisse

    public PosOpeningView(WebDriver webDriver) {
        init(webDriver, this);
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

    /**
     * Confirme la date d'ouverture de la caisse
     */
    public void clickConfirmOpeningDate() {
        searchAndClickElement(CONFIRM_POS_OPENING_DATE_BTN_XPATH);
    }

    /**
     * Confirme l'entrée en ouverture de caisse
     */
    public boolean clickEnterPosOpening() {
        return searchAndClickElement(ENTER_POS_OPENING_POPUP_BTN_XPATH);
    }

    /**
     * Click sur le bouton d'aprobation diff rendu
     */
    public void clickApprovalOk() {
        searchAndClickElement(APPROVAL_REASON_OK_BTN_XPATH);
    }

}
