package Step;

import Helpers.Element.WebElementHelper;
import Helpers.Test.ReportHelper;
import Step.Value.BaseStepValue;
import View.Footer.FooterView;
import View.Ticket.Payment.PaymentPanelView;
import View.Ticket.ReceiptView;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Classe utilitaire pour le ticket
 */
public class TicketStep extends BaseStep {

    /**
     * Vide le ticket en cours
     * @param driver
     */
    @Step("Vide le ticket")
    public static void deleteTicket(ChromeDriver driver) throws InterruptedException {
        FooterView footerView = new FooterView(driver);
        // Clic sur 'Vider sur ticket'
        footerView.clickOnDeleteTicketBtn();
        // Clic sur 'Confirmer suppression'
        footerView.clickOnConfirmDeleteBtn();
        //FIXME On attend une seconde afin de pas fermer Chrome sans que OB ait correctement "vidé" le ticket
        Thread.sleep(1000);
        ReportHelper.attachScreenshot(driver);
    }

    @Step("Vide le ticket")
    public static void deleteWorkOrder(ChromeDriver driver) throws InterruptedException {
        FooterView footerView = new FooterView(driver);
        // Clic sur 'Vider sur ticket'
        footerView.clickOnDeleteTicketBtn();
        //FIXME On attend une seconde afin de pas fermer Chrome sans que OB ait correctement "vidé" le ticket
        Thread.sleep(1000);
        ReportHelper.attachScreenshot(driver);
    }

    @Step("Vérifie que le montant à payer est égal à {stepValue.expectedValue}")
    public static void checkTotalToPay(BaseStepValue stepValue) {
        // Page panier
        FooterView footer = new FooterView(stepValue.driver);
        // Comparaison du total avec la valeure attendue
        stepValue.assertionMessage = "Montant à payer : ";
        stepValue.isEquals(WebElementHelper.waitUntilExpectedText(stepValue.getExpectedValue(), footer.getTotalToPayElem(), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

    @Step("Vérifie que l'état de la commande est à {stepValue.expectedValue}")
    public static void checkOrderState(BaseStepValue stepValue) {
        // Footer
        FooterView footerView = new FooterView(stepValue.driver);
        // Comparaison de l'état ave l'état attendu
        stepValue.isEquals(WebElementHelper.waitUntilExpectedText(stepValue.getExpectedValue(), footerView.getOrderStateElem(), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
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
    public static void checkTicketAmount(BaseStepValue stepValue) {
        ReceiptView receiptView = new ReceiptView(stepValue.driver);
        stepValue.isEquals(WebElementHelper.waitUntilExpectedText(stepValue.getExpectedValue(), receiptView.getTotalAmountElem(), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

    @Step("Vérifie que le client {stepValue.expectedValue} est associé au ticket")
    public static void checkLinkedCustomer(BaseStepValue stepValue) {
        ReceiptView receiptView = new ReceiptView(stepValue.driver);
        stepValue.assertionMessage = "Client " + stepValue.expectedValue + " associé au ticket : ";
        stepValue.isEquals(WebElementHelper.waitUntilExpectedText(stepValue.getExpectedValue(), receiptView.getLinkedCustomer(), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }
}
