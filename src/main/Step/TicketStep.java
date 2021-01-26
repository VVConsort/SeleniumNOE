package Step;

import Helpers.Element.WebElementHelper;
import Helpers.Test.ReportHelper;
import Step.Value.BaseStepValue;
import View.Footer.FooterView;
import View.Ticket.MergedDocumentsView;
import View.Ticket.Payment.PaymentPanelView;
import View.Ticket.ReceiptView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * Classe utilitaire pour les promotions
 */
public class TicketStep {



    /**
     * Vide le ticket en cours
     * @param driver
     */
    @Step("Vide le ticket")
    public static void deleteTicket(WebDriver driver) {
        FooterView footerView = new FooterView(driver);
        // Clic sur 'Vider sur ticket'
        footerView.clickOnDeleteTicketBtn();
        // Clic sur 'Confirmer suppression'
        footerView.clickOnConfirmDeleteBtn();
        ReportHelper.attachScreenshot(driver);
    }

    @Step("Vide le ticket")
    public static void deleteWorkOrder(WebDriver driver) {
        FooterView footerView = new FooterView(driver);
        // Clic sur 'Vider sur ticket'
        footerView.clickOnDeleteTicketBtn();
        ReportHelper.attachScreenshot(driver);
    }

    @Step("Vérifie que le montant à payer est égal à {stepValue.expectedValue}")
    public static void checkTotalToPay(BaseStepValue stepValue) {
        // Page panier
        FooterView footer = new FooterView(stepValue.driver);
        // Comparaison du total avec la valeure attendue
        stepValue.isEquals(footer.getTotalToPay());
    }

    @Step("Vérifie que l'état de la commande est à {stepValue.expectedValue}")
    public static void checkOrderState(BaseStepValue stepValue) {
        // Footer
        FooterView footerView = new FooterView(stepValue.driver);
        // Comparaison de l'état ave l'état attendu
        stepValue.isEquals(footerView.getOrderState());
    }

    @Step("Vérifie que la commande est déjà réglée")
    public static void checkOrderAlreadyPaid(BaseStepValue stepValue) {
        // Footer
        FooterView footerView = new FooterView(stepValue.driver);
        // click sur "A payer"
        PaymentPanelView payPanel = footerView.clickOnTotalToPayBtn();
        // On controle qu'il n' ya plus rien à payer
        stepValue.isTrue(payPanel.isAlreadyPaid());
    }

    @Step("Vérifie que la valeur du ticket est de {stepValue.expectedValue}")
    public static void checkTicketAmount(BaseStepValue stepValue)
    {
        ReceiptView receiptView = new ReceiptView(stepValue.driver);
        stepValue.isEquals(receiptView.getTotalAmount());
    }
}
