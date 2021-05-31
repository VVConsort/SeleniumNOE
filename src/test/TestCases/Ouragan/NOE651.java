package TestCases.Ouragan;

import Enums.Payment.SendTicketMode;
import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.PaymentStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import io.qameta.allure.Muted;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE651 extends BaseTest {
    @Parameters({"jsonFilePath", "expectedAmountToPay", "expectedState"})
    @Test(description = "Scanner et régler en caisse une réservation Web disponible en magasin")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-651")
    public void noe651(String jsonFilePath, String expectedAmountToPay, String expectedState) throws IOException, InterruptedException {
        // Intégration sur OB du BT Ouragan
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Ouverture du BT Ouragan
        OuraganStep.openWorkOrder(documentCode, driver);
        // Vérification montant à payer
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedAmountToPay;
        TicketStep.checkTotalToPay(stepValue);
        // Vérification état commande
        //TODO Attente C.Brazier pour accès BDD et controle etat/renvoi vers ouragan
        stepValue.expectedValue = expectedState;
        TicketStep.checkOrderState(stepValue);
        // Paiement et finalisation du ticket
        PaymentStepValue payStepValue = getNewPaymentStepValue(false);
        payStepValue.sendTicketMode = SendTicketMode.MAIL_ONLY;
        payStepValue.email="noetest@gmail.com";
        PaymentStep.payWithCash(payStepValue);
        PaymentStep.finalizeOrder(payStepValue);
        //TODO Attente C.Brazier pour accès BDD et controle etat/renvoi vers ouragan
    }
}
