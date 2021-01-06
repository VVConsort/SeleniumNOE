package Step;

import Helpers.Element.WaitHelper;
import Step.Value.PaymentStepValue;
import View.Footer.FooterView;
import View.Ticket.Payment.CreditNote.CreditNoteSearchView;
import View.Ticket.Payment.CreditNote.CreditNoteUnitView;
import View.Ticket.PaymentPanelView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class PaymentStep {

    // TODO
    private static void pay(PaymentStepValue stepValue) {
        // Clic sur le boutton "A payer" du footer
        FooterView footerView = new FooterView(stepValue.driver);
        footerView.clickOnTotalToPayBtn();
        // Selon le type de réglement choisi
        switch (stepValue.paymentMean) {
            case CREDIT_NOTE -> getCreditNoteUnitView(stepValue);
        }
    }

    @Step("Applique l'avoir {stepValue.paymentId}")
    public static void applyCreditNote(PaymentStepValue stepValue) {
        // Vue unitaire avoir
        CreditNoteUnitView unitView = getCreditNoteUnitView(stepValue);
        // Appliquer l'avoir
        unitView.clickApplyBtn();
    }

    // TODO : cas ou plusieurs même mode de paiement : différencier sur montant
    @Step("Retire la ligne de paiement {value.paymentMean.getLabel()}")
    public static void removePaymentLine(PaymentStepValue value) {
        PaymentPanelView view = new PaymentPanelView(value.driver);
        // Appuie sur le btn de suppression correspond au mode de paiement
        view.clickRemovePaymentLine(value.paymentMean.getLabel());
    }

    /**
     * Recherche l'avoir et retourne la vue unitaire
     * @param stepValue
     */
    private static CreditNoteUnitView getCreditNoteUnitView(PaymentStepValue stepValue) {
        // Clic sur le boutton "A payer" du footer
        FooterView footerView = new FooterView(stepValue.driver);
        footerView.clickOnTotalToPayBtn();
        // Panel de paiement
        PaymentPanelView paymentPanel = new PaymentPanelView(stepValue.driver);
        // Clic sur "Avoir"
        paymentPanel.clickCreditNoteBtn();
        // Clic sur "Tout payer"
        CreditNoteSearchView creditNoteSearchView = (CreditNoteSearchView) paymentPanel.clickPayAllBtn();
        // On entre le numéro de l'avoir
        CreditNoteUnitView creditNoteUnitView = creditNoteSearchView.getCreditNote(stepValue.paymentId);
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
    }

    @Step("Controle que le montant restant à payer est égal à {stepValue.expectedValue}")
    public static void checkPendingAmount(PaymentStepValue stepValue) {
        // Vue panneau paiement
        PaymentPanelView view = new PaymentPanelView(stepValue.driver);
        // Comparaison avec la valeure attendue
        stepValue.isEquals(view.getPendingAmount());
    }
}
