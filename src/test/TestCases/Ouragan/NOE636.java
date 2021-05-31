package TestCases.Ouragan;

import Enums.Payment.SendTicketMode;
import Helpers.Test.BaseTest;
import Step.*;
import Step.Value.BaseStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE636 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedAmountToPay", "depositLabel", "depositQty", "depositUnitPrice", "depositGrossPrice"})
    @Test(description = "Scanner et régler le solde d'une Réservation Magasin")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-636")
    public void noe636(String jsonFilePath, String expectedAmountToPay, String depositLabel, String depositQty, String depositUnitPrice, String depositGrossPrice) throws IOException, InterruptedException {
        // Intégration sur OB du BT Ouragan
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Ouverture du BT Ouragan
        OuraganStep.openWorkOrder(documentCode, driver);
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Controle qty de l'accompte
        stepValue.expectedValue = depositQty;
        TicketLineStep.checkQuantity(depositLabel, stepValue);
        // Controle prix unitaire
        stepValue.expectedValue = depositUnitPrice;
        TicketLineStep.checkUnitPrice(depositLabel, stepValue);
        // Controle prix total ligne
        stepValue.expectedValue = depositGrossPrice;
        TicketLineStep.checkGrossPrice(depositLabel, stepValue);
        // Controle du montant à payer
        stepValue.expectedValue = expectedAmountToPay;
        TicketStep.checkTotalToPay(stepValue);
        // Paiement
        PaymentStepValue payStepValue = getNewPaymentStepValue(false);
        payStepValue.email="noetest@gmail.com";
        payStepValue.sendTicketMode = SendTicketMode.MAIL_ONLY;
        PaymentStep.payWithCash(payStepValue);
        // Finalisation
        PaymentStep.finalizeOrder(payStepValue);
        //TODO controle état BDD
    }
}
