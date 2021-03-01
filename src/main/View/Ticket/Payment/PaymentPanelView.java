package View.Ticket.Payment;

import Enums.Payment.PaymentMean;
import Helpers.Element.WebElementHelper;
import Helpers.XPath.XPathPaymentHelper;
import View.BaseView;
import View.Ticket.Payment.CreditNote.CreditNoteSearchView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class PaymentPanelView extends BaseView {
    // Xpath boutton "Tout payer"
    private static final String PAY_ALL_BTN_XPATH = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_exactbutton\"]";
    // XPath du bouton "Finaliser"
    private static final String FINALIZE_BTN_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_donebutton\"]";
    // XPath Boutton 'Avoir'
    private static final String CREDIT_NOTE_BTN_XPATH = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_rightBottomPanel_keyboard_toolbarcontainer_toolbarPayment_btnSide3_GCNV_payment.creditnote_button\"]";
    // XPath du libellé "Le total est de zéro, pas de paiement nécessaire"
    private static final String ALREADY_PAID_LBL_XPATH = " //*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_donezerolbl\"]";
    // Id du contenant des lignes de paiements
    private static final String PAYMENT_CONTENT_ID = "terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_payments";

    // Boutton 'Espece'
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_rightBottomPanel_keyboard_toolbarcontainer_toolbarPayment_btnSide2_OBPOS_payment.cash_button\"]")
    private WebElement cashBtn;

    // Montant restant à règler
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_totalpending\"]")
    private WebElement totalPending;

    // Mode de paiement sélectionné
    private PaymentMean selectedPayment;

    public PaymentPanelView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Clique sur le boutton "Avoir"
     */
    public void clickCreditNoteBtn() {
        findAndClickElement(CREDIT_NOTE_BTN_XPATH, true);
        selectedPayment = PaymentMean.CREDIT_NOTE;
    }

    /**
     * Clique sur le boutton "Espèces"
     */
    public void clickCashBtn() {
        super.click(cashBtn);
        selectedPayment = PaymentMean.CASH;
    }

    /**
     * Appuie sur le boutton "Tout payer"
     */
    public BaseView clickPayAllBtn() {
        // La vue à renvoyer selon le mode dde paiement
        BaseView paymentPage = null;
        // Click sur "Tout payer"
        findAndClickElement(PAY_ALL_BTN_XPATH, true);
        // On renvoie la vue correspondante au mode de paiement sélectionné
        switch (selectedPayment) {
            case CREDIT_NOTE -> paymentPage = new CreditNoteSearchView(driver);
            case CASH -> paymentPage = this;
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
        String removePaymentLineBtnXPath = XPathPaymentHelper.getRemovePaymentButtonAmountXPath(paymentNameId);
        // Recherche du bouttn "Supprimer ligne" associé au mode de paiement
        WebElement removeBtn = WebElementHelper.waitUntilElementIsVisible(driver, 5, By.xpath(removePaymentLineBtnXPath), false);
        if (removeBtn != null) {
            super.click(removeBtn);
            // Attend que le boutton disparaisse
            WebElementHelper.waitUntilElementIsNotPresent(driver, 5, By.xpath(removePaymentLineBtnXPath), false);
        }
    }

    /**
     * Renvoi vrai si la ligne de paiement est présente
     * @param paymentLabel
     * @return
     */
    public boolean hasPaymentLine(String paymentLabel) {
        // On récupère l'élément 'Ligne paiement' à partir de son libellé
        return getPaymentLineIdElemByText(paymentLabel) != null;
    }

    /**
     * Retourne l'élément contenant le restant à règler
     * @return
     */
    public WebElement getPendingAmountElem() {
        return totalPending;
    }

    /**
     * Retourne l'élement "Nom ligne de paiement" à partir de son nom/text
     * @param label
     * @return
     */
    private String getPaymentLineIdElemByText(String label) {
        String result = "";
        // Recherche l'élement 'label' à partir du texte
        WebElement paymentLineElem = WebElementHelper.getElementFromIdAndText(PAYMENT_CONTENT_ID, label, driver);
        // Blindage
        if (paymentLineElem != null) {
            // Affectation de l'id de l'élement
            result = paymentLineElem.getAttribute("id");
        }
        return result;
    }

    /**
     * Finalise la commande
     */
    public SendTicketView clickFinalize() {
        findAndClickElement(FINALIZE_BTN_XPATH, true);
        return new SendTicketView(driver);
    }

    /**
     * Renvoi vrai si la commande est déjà réglée
     * @return
     */
    public boolean isAlreadyPaid() {
        return findElement(ALREADY_PAID_LBL_XPATH, false) != null;
    }

    /**
     * Retourne l'élement contenant le montant de la ligne
     * @param paymentLabel
     * @return
     */
    public WebElement getPaymentLineAmountElem(String paymentLabel) {
        WebElement result = null;
        // On récupère l'id de l'élement 'Nom ligne paiement'
        String paymentNameId = getPaymentLineIdElemByText(paymentLabel);
        // Récupère le Xpath de l'élement 'Montant ligne paiement'
        String lineAmountXPath = XPathPaymentHelper.getPaymentLineAmountXPath(paymentNameId);
        return WebElementHelper.waitUntilElementIsVisible(driver, 5, By.xpath(lineAmountXPath), false);
        // Si le montant est présent
        /*if (lineAmount != null) {
            // On récupère la valeur contenue dans le texte
            amount = lineAmount.getText();
        }
        return result;*/
    }

}
