package TestCases.Ouragan;

import Enums.PaymentMean;
import Enums.SendTicketMode;
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
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-655")
    public void noe655(String jsonFilePath, String expectedPendingAmount, String expectedState) throws IOException, InterruptedException, SQLException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(currentDriver);
        // Ouverture du BT intégré
        OuraganStep.openWorkOrder(documentCode,currentDriver);
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
        PaymentStep.payWithCash(payStepValue);
        // Finalisation
        PaymentStep.finalizeOrder(payStepValue);
        // Fermeture du BT
        TicketStep.deleteWorkOrder(currentDriver);
        /*String ouraganOrderState = OuraganStep.getOrderState(TestSuiteProperties.OURAGAN_DB_URL, TestSuiteProperties.OURAGAN_DB_USER, TestSuiteProperties.OURAGAN_DB_PASSWORD, OuraganJsonHelper.getOrderReference(OuraganJsonHelper.getJsonFromFileURL(jsonFilePath)));
        // Comparaison avec l'état attendu
        softAssert.assertEquals(ouraganOrderState, expectedState);*/
    }
}
