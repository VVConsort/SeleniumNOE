package TestCases.Ouragan;

import Enums.Payment.PaymentMean;
import Enums.Payment.SendTicketMode;
import Helpers.Test.BaseTest;
import Step.*;
import Step.Value.BaseStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

public class NOE655 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedPendingAmount", "expectedState"})
    @Test(description = " Scanner et régler une Réservation Web contenant un article A et un article B")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-655")
    public void noe655(String jsonFilePath, String expectedPendingAmount, String expectedState) throws IOException, InterruptedException, SQLException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Ouverture du BT intégré
        OuraganStep.openWorkOrder(documentCode, driver);
        // Vérification du montant à payer
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedPendingAmount;
        TicketStep.checkTotalToPay(stepValue);
        // Vérification de l'état
        stepValue.expectedValue = expectedState;
        TicketStep.checkOrderState(stepValue);
        // Paiment de la commande en cash
        PaymentStepValue payStepValue = getNewPaymentStepValue(false);
        payStepValue.paymentMean = PaymentMean.CASH;
        payStepValue.sendTicketMode = SendTicketMode.MAIL_ONLY;
        payStepValue.email="noetest@gmail.com";
        PaymentStep.payWithCash(payStepValue);
        // Finalisation
        PaymentStep.finalizeOrder(payStepValue);
        // Fermeture du BT
        TicketStep.deleteWorkOrder(driver);
    }
}
