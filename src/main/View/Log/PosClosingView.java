package View.Log;

import Helpers.Element.WebElementHelper;
import View.BaseView;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class PosClosingView extends BaseView {

    // XPath boutton "OK" cloture de caisse
    private static final String ENTER_POS_CLOSING_POPUP_BTN_XPATH = "//*[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_confirmationPopup_btnOK\"]";

    // XPath boutton "Tout supprimer" reçus en attente
    private static final String DELETE_ALL_PENDING_RECEIPTS_BTN_XPATH = " //*[@id=\"terminal_containerWindow_cashUp_closeCashMultiColumn_leftPanel_closeCashLeftPanel_listPendingReceipts_btnDeleteAll\"]";

    // Xpath boutton "Confirmer suppression reçus en attente
    private static final String CONFIRM_PENDING_RECEIPTS_DELETE_BTN_XPATH = "//*[@id=\"terminal_confirmationContainer_dynamicConfirmationPopup_footer_confirmationPopup_btnOui, supprimer\"]";

    // XPath boutton ok "Différence monnaie"
    private static final String APPROVAL_REASON_OK_BTN_XPATH = "//button[@id=\"terminal_containerWindow_cashUp_OBPOS_modalApprovalReason_footer_btnModalApprovalReasonAccept\"]";
    //*[@id="terminal_containerWindow_cashUp_OBPOS_modalApprovalReason_footer_btnModalApprovalReasonAccept"]

    ////*[@id="terminal_containerWindow_loginCashUp_POSS_modalApprovalReason_footer_btnModalApprovalReasonAccept"]
    //Xpath Boutton suivant
    private static final String NEXT_BTN_XPATH ="//*[@id=\"terminal_containerWindow_cashUp_closeCashMultiColumn_leftToolbar_leftToolbar_toolbar_btnNext\"]";

    public PosClosingView(ChromeDriver webDriver) {
        init(webDriver, this);
    }

    /**
     * Entre en cloture de caisse
     */
    public boolean enterPosClosing() {
       return findAndClickElement(ENTER_POS_CLOSING_POPUP_BTN_XPATH,false);
    }

    /**
     * Suppression des reçus en attente
     */
    public void deleteAllPendingReceipts() {
        super.click(WebElementHelper.getElement(driver, By.xpath(DELETE_ALL_PENDING_RECEIPTS_BTN_XPATH)), false);
    }

    /**
     * Confirme la suppression des reçus en attente
     */
    public void confirmPendingReceiptsDelete() {
        super.click(WebElementHelper.getElement(driver, By.xpath(CONFIRM_PENDING_RECEIPTS_DELETE_BTN_XPATH)), false);
    }

    /**
     *
     * @param isMandatory
     */
    public void confirmApprovalReason(boolean isMandatory)
    {
        findAndClickElement(APPROVAL_REASON_OK_BTN_XPATH,isMandatory);
    }

    /**
     * Click sur suivant
     */
    public boolean clickNext(boolean isMandatory) {
       return super.findAndClickElement(NEXT_BTN_XPATH,isMandatory);

    }
}
