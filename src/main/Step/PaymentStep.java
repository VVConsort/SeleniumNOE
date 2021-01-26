package Step;

import Enums.SendTicketMode;
import Helpers.Test.ReportHelper;
import Step.Value.Payment.PaymentStepValue;
import View.Footer.FooterView;
import View.Footer.Menu.Voucher.InvalidVoucherView;
import View.Footer.Menu.Voucher.VoucherCodeInputView;
import View.Ticket.Payment.CreditNote.CreditNoteSearchView;
import View.Ticket.Payment.CreditNote.CreditNoteUnitView;
import View.Ticket.Payment.PaymentPanelView;
import View.Ticket.Payment.SendTicketView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class PaymentStep {

    @Step("Applique l'avoir {stepValue.paymentId}")
    public static void applyCreditNote(PaymentStepValue stepValue) {
        // Vue unitaire avoir
        CreditNoteUnitView unitView = getCreditNoteUnitView(stepValue);
        // Appliquer l'avoir
        unitView.clickApplyBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
    }

    @Step("Vérifie que le mode de paiement {value.paymentId} est présent")
    public static void containsPaymentLine(PaymentStepValue value) {
        FooterView footerView = new FooterView(value.driver);
        // Clic sur le boutton "A payer" du footer
        footerView.clickOnTotalToPayBtn();
        // Vue panneau paiement
        PaymentPanelView payView = new PaymentPanelView(value.driver);
        // Test si la ligne de paiement est présente
        value.isTrue(payView.hasPaymentLine(value.paymentMean.getLabel()));
    }

    @Step("Vérifie que le montant de la ligne {value.paymentMean.label} est égale à {value.expectedValue}")
    public static void checkPaymentLineAmount(PaymentStepValue value) {
        FooterView footerView = new FooterView(value.driver);
        // Clic sur le boutton "A payer" du footer
        footerView.clickOnTotalToPayBtn();
        // Vue panneau paiement
        PaymentPanelView view = new PaymentPanelView(value.driver);
        // Test le montant de la ligne avec celui attendu
        value.isEquals(view.getPaymentLineAmount(value.paymentMean.getLabel()));
    }

    // TODO : cas ou plusieurs même mode de paiement : différencier sur montant
    @Step("Retire la ligne de paiement {value.paymentMean.label}")
    public static void removePaymentLine(PaymentStepValue value) {
        FooterView footerView = new FooterView(value.driver);
        // Clic sur le boutton "A payer" du footer
        footerView.clickOnTotalToPayBtn();
        // Vue panneau paiement
        PaymentPanelView view = new PaymentPanelView(value.driver);
        // Appuie sur le btn de suppression correspond au mode de paiement
        view.clickRemovePaymentLine(value.paymentMean.getLabel());
        ReportHelper.attachScreenshot(value.driver);
    }

    /**
     * Recherche l'avoir et retourne la vue unitaire
     * @param stepValue
     */
    private static CreditNoteUnitView getCreditNoteUnitView(PaymentStepValue stepValue) {
        FooterView footerView = new FooterView(stepValue.driver);
        // Clic sur le boutton "A payer" du footer
        footerView.clickOnTotalToPayBtn();
        // Panel de paiement
        PaymentPanelView paymentPanel = new PaymentPanelView(stepValue.driver);
        // Clic sur "Avoir"
        paymentPanel.clickCreditNoteBtn();
        // Clic sur "Tout payer"
        CreditNoteSearchView creditNoteSearchView = (CreditNoteSearchView) paymentPanel.clickPayAllBtn();
        // On entre le numéro de l'avoir
        CreditNoteUnitView creditNoteUnitView = creditNoteSearchView.getCreditNote(stepValue.paymentId);
        ReportHelper.attachScreenshot(stepValue.driver);
        // Retourne la vue unitaire
        return creditNoteUnitView;
    }

    /**
     * @param stepValue
     */
    @Step("Tente de payer avec l'avoir déjà utilisé {stepValue.paymentId}")
    public static void tryUsedCreditNote(PaymentStepValue stepValue) {
        // Controle que l'avoir est déjà utilisé/n'existe pas
        stepValue.isNull(getCreditNoteUnitView(stepValue));
    }

    @Step("Fermeture de la fenêtre de recherche d'avoir")
    public static void closeCreditNoteSearchView(WebDriver driver) {
        // Ferme la fenetre de recherche d'avoir
        CreditNoteSearchView creditNoteSearchView = new CreditNoteSearchView(driver);
        creditNoteSearchView.closeCreditNoteSearchView();
        ReportHelper.attachScreenshot(driver);
    }

    //@Step("Controle que le montant restant à payer est égal à {stepValue.expectedValue}")
    public static void checkPendingAmount(PaymentStepValue stepValue) {
        // Vue panneau paiement
        PaymentPanelView view = new PaymentPanelView(stepValue.driver);
        // Comparaison avec la valeure attendue
        stepValue.isEquals(view.getPendingAmount());
    }

    @Step("Ajoute le bon d'achat {stepValue.paymentId} au ticket")
    public static void useVoucher(PaymentStepValue stepValue) {
        FooterView footerView = new FooterView(stepValue.driver);
        // Pop up bon d'achat
        VoucherCodeInputView voucherView = footerView.clickOnMenuBtn().clickOnVoucherBtn();
        // On entre le code du bon d'achat
        voucherView.enterVoucherCode(stepValue.paymentId);
        // On click sur OK
        InvalidVoucherView invalidView = voucherView.clickOk();
        // Si le bon d'achat est invalide
        if (invalidView != null) {
            // Ferme la fenetre d'erreur
            invalidView.clickOK();
        }
        ReportHelper.attachScreenshot(stepValue.driver);
    }

    @Step("Paie en espèces")
    public static void payWithCash(PaymentStepValue stepValue) {
        // Vue panneau paiement
        PaymentPanelView panelView = new PaymentPanelView(stepValue.driver);
        // Vue panneau paiement
        FooterView footerView = new FooterView(stepValue.driver);
        // Clic sur le boutton "A payer" du footer
        footerView.clickOnTotalToPayBtn();
        // Sélection du mode de paiement "Espèces"
        panelView.clickCashBtn();
        // Click sur "Tout payer"
        panelView.clickPayAllBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
    }

    @Step("Finalise le ticket en sélectionnant l'envoi {sendTicketMode.getLabel()}")
    public static void finalizeOrder(PaymentStepValue stepValue) {
        FooterView footer = new FooterView(stepValue.driver);
        // Vue panneau paiement
        PaymentPanelView view = footer.clickOnTotalToPayBtn();
        // Finalise la commande
        sendTicket(stepValue.sendTicketMode, view.clickFinalize());
        ReportHelper.attachScreenshot(stepValue.driver);
    }

    /**
     * Envoie le ticket selon le mode sélectionné
     * @param sendTicketMode
     * @param sendTicketView
     */
    private static void sendTicket(SendTicketMode sendTicketMode, SendTicketView sendTicketView) {
        switch (sendTicketMode) {
            case MAIL_ONLY -> sendTicketView.clickMailOnly();
        }
    }
}
