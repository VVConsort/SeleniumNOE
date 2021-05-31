package TestCases.Ouragan;

import Enums.Payment.SendTicketMode;
import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.PaymentStep;
import Step.TicketStep;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class DocumentCodeUnicity extends BaseTest {

    @Parameters({"jsonFilePath"})
    @Test(description = "Scanner et régler le solde d'une Réservation Magasin")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-636")
    public void documentCodeUnicity(String jsonFilePath) throws IOException, InterruptedException {
        // Intégration sur OB du BT Ouragan

        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();

        while (true) {
            String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
            System.out.println("Document code : " + documentCode);
            // Vidage du ticket
            TicketStep.deleteTicket(driver);
            // Ouverture du BT Ouragan
            OuraganStep.openWorkOrder(documentCode, driver);
            // Paiement
            PaymentStepValue payStepValue = getNewPaymentStepValue(false);
            payStepValue.sendTicketMode = SendTicketMode.MAIL_ONLY;
            payStepValue.email = "vvanhaute@norauto.com";
            PaymentStep.payWithCash(payStepValue);
            // Finalisation
            PaymentStep.finalizeOrder(payStepValue);
            Thread.sleep(1000);
        }


        //TODO controle état BDD
    }
}