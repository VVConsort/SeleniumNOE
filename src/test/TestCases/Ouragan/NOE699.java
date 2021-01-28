package TestCases.Ouragan;

import Enums.SendTicketMode;
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

public class NOE699 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedAmountToPay"})
    @Test(description = "Scanner un RA contenant un bon de 15€ car promesse client non tenue")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-699")
    public void noe699(String jsonFilePath, String expectedAmountToPay) throws IOException, InterruptedException {
        // Intégration sur OB du BT Ouragan
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Ouverture du BT Ouragan
        OuraganStep.openWorkOrder(documentCode, driver);
        // Vérification du montant à payer
        BaseStepValue baseStep = getNewBaseStepValue(false);
        baseStep.expectedValue = expectedAmountToPay;
        TicketStep.checkTotalToPay(baseStep);
        // On paie en espèce
        PaymentStepValue payStep = getNewPaymentStepValue(false);
        payStep.sendTicketMode = SendTicketMode.MAIL_ONLY;
        PaymentStep.payWithCash(payStep);
        // On finalise la commande
        PaymentStep.finalizeOrder(payStep);
        // TODO attente accès BDD C Brazier : vérification génération coupon et retour vers OB
    }
}
