package Step;

import Pages.Footer.FooterView;
import Pages.Ticket.Payment.CreditNote.CreditNoteSearchView;
import Pages.Ticket.Payment.CreditNote.CreditNoteUnitView;
import Pages.Ticket.PaymentPanelView;
import Step.Value.PaymentStepValue;
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
            case CREDIT_NOTE -> doPayWithCreditNote(stepValue);
        }
    }

    /**
     * Paiement par avoir
     * @param stepValue
     */
    private static CreditNoteUnitView doPayWithCreditNote(PaymentStepValue stepValue) {
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
        stepValue.isNull(doPayWithCreditNote(stepValue));

    }

    @Step("Fermeture de la fenêtre de recherche d'avoir")
    public static void closeCreditNoteSearchView(WebDriver driver) {
        // Ferme la fenetre de recherche d'avoir
        CreditNoteSearchView creditNoteSearchView = new CreditNoteSearchView(driver);
        creditNoteSearchView.closeCreditNoteSearchView();
    }
}
