package View.Ticket;

import Enums.PaymentMean;
import Helpers.Element.WaitHelper;
import Helpers.XPath.XPathPaymentLineHelper;
import View.BaseView;
import View.Ticket.Payment.CreditNote.CreditNoteSearchView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentPanelView extends BaseView {
    // Xpath boutton "Tout payer"
    private static final String PAY_ALL_BTN_XPATH = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_exactbutton\"]";

    // Boutton 'Avoir'
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_rightBottomPanel_keyboard_toolbarcontainer_toolbarPayment_btnSide3_GCNV_payment.creditnote_button\"]")
    private WebElement creditNoteBtn;

    // Montant restant à règler
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_totalpending\"]")
    private WebElement totalPending;

    // Mode de paiement sélectionné
    private PaymentMean selectedPayment;

    public PaymentPanelView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Clique sur le boutton "Avoir"
     */
    public void clickCreditNoteBtn() {
        super.click(creditNoteBtn);
        selectedPayment = PaymentMean.CREDIT_NOTE;
    }

    /**
     * Appuie sur le boutton "Tout payer"
     */
    public BaseView clickPayAllBtn() {
        // La vue à renvoyer selon le mode dde paiement
        BaseView paymentPage = null;
        // Si l'élément est présent et clické
        if (super.searchAndClickElement(PAY_ALL_BTN_XPATH)) {
            // On renvoie la vue correspondante au mode de paiement sélectionné
            switch (selectedPayment) {
                case CREDIT_NOTE -> paymentPage = new CreditNoteSearchView(driver);
                // TODO : les autres mode de paiement
            }
        }
        return paymentPage;
    }

    /**
     * Appuie sur le boutton "Suppression ligne de paiement"
     * @param paymentLabel
     */
    public void clickRemovePaymentLine(String paymentLabel) {
        // On récupère l'id de l'élement 'Nom ligne paiement'
        String paymentNameId = getPaymentLineIdElemByText(paymentLabel);
        // Récupère le Xpath du boutton suppression paiement
        String removePaymentLineBtnXPath = XPathPaymentLineHelper.getRemovePaymentButtonAmountXPath(paymentNameId);
        // Recherche du bouttn "Supprimer ligne" associé au mode de paiement
        WebElement removeBtn = Helpers.Element.WebElementHelper.getElement(driver, By.xpath(removePaymentLineBtnXPath));
        if (removeBtn != null) {
            super.click(removeBtn);
            // Attend que le boutton disparaisse
            WaitHelper.waitUntilElementIsNotVisible(driver, 5, removePaymentLineBtnXPath, false);
        }
    }

    /**
     * Retourne le restant à règler
     * @return
     */
    public String getPendingAmount() {
        return totalPending.getText();
    }

    /**
     * Retourne l'élement "Nom ligne de paiement" à partir de son nom/text
     * @param label
     * @return
     */
    private String getPaymentLineIdElemByText(String label) {
        String result = "";
        // Recherche l'élement 'label' à partir du texte
        //WebElement paymentLineElem = WebElementHelper.getElementFromText(label, driver);
        WebElement paymentLineElem = Helpers.Element.WebElementHelper.getElementFromTextAndClass(label, driver);
        // Blindage
        if (paymentLineElem != null) {
            // Affectation de l'id de l'élement
            result = paymentLineElem.getAttribute("id");
        }
        return result;
    }

}
