package Step;

import Enums.SendTicketMode;
import Helpers.Test.ReportHelper;
import Step.Value.Payment.FinalizePaymentStepValue;
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

    // TODO
    /*private static void pay(PaymentStepValue stepValue) {
        // Clic sur le boutton "A payer" du footer
        FooterView footerView = new FooterView(stepValue.driver);
        footerView.clickOnTotalToPayBtn();
        // Selon le type de réglement choisi
        switch (stepValue.paymentMean) {
            case CREDIT_NOTE -> getCreditNoteUnitView(stepValue);
        }
    }*/

    @Step("Applique l'avoir {stepValue.paymentId}")
    public static void applyCreditNote(PaymentStepValue stepValue) {
        // Vue unitaire avoir
        CreditNoteUnitView unitView = getCreditNoteUnitView(stepValue);
        ReportHelper.attachScreenshot(stepValue.driver);
        // Appliquer l'avoir
        unitView.clickApplyBtn();
    }

    // TODO : cas ou plusieurs même mode de paiement : différencier sur montant
    @Step("Retire la ligne de paiement {value.paymentMean.label}")
    public static void removePaymentLine(PaymentStepValue value) {
        PaymentPanelView view = new PaymentPanelView(value.driver);
        ReportHelper.attachScreenshot(value.driver);
        // Appuie sur le btn de suppression correspond au mode de paiement
        view.clickRemovePaymentLine(value.paymentMean.getLabel());
    }

    /**
     * Recherche l'avoir et retourne la vue unitaire
     * @param stepValue
     */
    private static CreditNoteUnitView getCreditNoteUnitView(PaymentStepValue stepValue) {
        FooterView footerView = new FooterView(stepValue.driver);
        ReportHelper.attachScreenshot(stepValue.driver);
        // Clic sur le boutton "A payer" du footer
        footerView.clickOnTotalToPayBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
        // Panel de paiement
        PaymentPanelView paymentPanel = new PaymentPanelView(stepValue.driver);
        ReportHelper.attachScreenshot(stepValue.driver);
        // Clic sur "Avoir"
        paymentPanel.clickCreditNoteBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
        // Clic sur "Tout payer"
        CreditNoteSearchView creditNoteSearchView = (CreditNoteSearchView) paymentPanel.clickPayAllBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
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
        ReportHelper.attachScreenshot(driver);
        creditNoteSearchView.closeCreditNoteSearchView();
    }

    @Step("Controle que le montant restant à payer est égal à {stepValue.expectedValue}")
    public static void checkPendingAmount(PaymentStepValue stepValue) {
        // Vue panneau paiement
        PaymentPanelView view = new PaymentPanelView(stepValue.driver);
        // Comparaison avec la valeure attendue
        stepValue.isEquals(view.getPendingAmount());
    }

    @Step("Ajoute le bon d'achat {stepValue.paymentId} au ticket")
    public static void useVoucher(PaymentStepValue stepValue) {
        FooterView footerView = new FooterView(stepValue.driver);
        ReportHelper.attachScreenshot(stepValue.driver);
        // Pop up bon d'achat
        VoucherCodeInputView voucherView = footerView.clickOnMenuBtn().clickOnVoucherBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
        // On entre le code du bon d'achat
        voucherView.enterVoucherCode(stepValue.paymentId);
        ReportHelper.attachScreenshot(stepValue.driver);
        // On click sur OK
        InvalidVoucherView invalidView = voucherView.clickOk();
        ReportHelper.attachScreenshot(stepValue.driver);
        // Si le bon d'achat est invalide
        if (invalidView != null) {
            ReportHelper.attachScreenshot(stepValue.driver);
            // Ferme la fenetre d'erreur
            invalidView.clickOK();
            ReportHelper.attachScreenshot(stepValue.driver);
        }
    }

    @Step("Paie en espèces")
    public static void payWithCash(PaymentStepValue stepValue) {
        ReportHelper.attachScreenshot(stepValue.driver);
        // Vue panneau paiement
        PaymentPanelView panelView = new PaymentPanelView(stepValue.driver);
        ReportHelper.attachScreenshot(stepValue.driver);
        // Vue panneau paiement
        FooterView footerView = new FooterView(stepValue.driver);
        ReportHelper.attachScreenshot(stepValue.driver);
        // Clic sur le boutton "A payer" du footer
        footerView.clickOnTotalToPayBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
        // Sélection du mode de paiement "Espèces"
        panelView.clickCashBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
        // Click sur "Tout payer"
        panelView.clickPayAllBtn();
        ReportHelper.attachScreenshot(stepValue.driver);
    }

    @Step("Finalise le ticket en sélectionnant l'envoi {sendTicketMode.getLabel()}")
    public static void finalizeOrder(PaymentStepValue stepValue) {
        ReportHelper.attachScreenshot(stepValue.driver);
        // Vue panneau paiement
        PaymentPanelView view = new PaymentPanelView(stepValue.driver);
        ReportHelper.attachScreenshot(stepValue.driver);
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
