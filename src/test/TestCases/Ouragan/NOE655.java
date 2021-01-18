package TestCases.Ouragan;

import Enums.PaymentMean;
import Enums.SendTicketMode;
import Helpers.Test.BaseTest;
import Step.*;
import Step.Value.Payment.FinalizePaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE655 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedPendingAmount", "expectedState", "postPaymentState",})
    @Test(description = " Scanner et régler une Réservation Web contenant un article A et un article B")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-655")
    public void noe655(String jsonFilePath, String expectedPendingAmount, String expectedState, String postPaymentState) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // Vidage du ticket
        TicketStep.deleteTicket(currentDriver);
        // Scan du BT
        ScanStep.scanValue(documentCode, currentDriver);
        // Fermeture des documents associés
        TicketStep.closeMergedDocuments(currentDriver);
        // Vérification du montant à payer
        TicketStep.checkTotalToPay(newStepValue(expectedPendingAmount, false));
        // Vérification de l'état
        TicketStep.checkOrderState(newStepValue(expectedState, false));
        // Paiment de la commande en cash
        PaymentStep.payWithCash(newPaymentStepValue(null, null, PaymentMean.CASH, null, false));
        // Finalisation
        PaymentStep.finalizeOrder(new FinalizePaymentStepValue(SendTicketMode.MAIL_ONLY, null, currentDriver, softAssert, false));
        // Vérification de son état
        System.out.println("done");
        //TODO
        //OuraganStep.checkOrderStat(numCommande,expectedState)

    }
}
