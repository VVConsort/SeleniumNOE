package TestCases.Ouragan;

import Enums.Payment.PaymentMean;
import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.PaymentStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE613 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedTotal"})
    @Test(description = "Scanner et payer un RA contenant 1 prestation C et 1 article A")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-613")
    public void noe613(String jsonFilePath, String expectedTotal) throws IOException, InterruptedException {
        // Intégration du BT
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Scan du BT
        OuraganStep.openWorkOrder(documentCode, driver);
        // Vérification du total ticket 149.22
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedTotal;
        TicketStep.checkTicketAmount(stepValue);
        // Click sur " A payer" et vérifier le montant de la ligne de paiement
        PaymentStepValue payStep = getNewPaymentStepValue(false);
        payStep.paymentMean = PaymentMean.FLEET;
        payStep.expectedValue = expectedTotal;
        PaymentStep.checkPaymentLineAmount(payStep);
        // Vérif montant à payer 149.22
        TicketStep.checkTotalToPay(stepValue);
        // Finaliser paiement
        PaymentStep.finalizeOrder(payStep);
    }
}
