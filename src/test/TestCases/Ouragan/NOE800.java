package TestCases.Ouragan;

import Enums.PaymentMean;
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

public class NOE800 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedTicketTotal", "expectedTotalToPay"})
    @Test(description = " Scanner un RA contenant des prestations comprises dans le Pass Entretien")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-800")
    public void noe800(String jsonFilePath, String expectedTicketTotal, String expectedTotalToPay) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Fermeture du potentiel ticket ouvert
        TicketStep.deleteTicket(driver);
        // Ouverture du BT intégré
        OuraganStep.openWorkOrder(documentCode, driver);
        BaseStepValue baseStep = getNewBaseStepValue(false);
        // Vérification du montant à payer
        baseStep.expectedValue = expectedTotalToPay;
        TicketStep.checkTotalToPay(baseStep);
        // Vérification du montant du ticket
        baseStep.expectedValue = expectedTicketTotal;
        TicketStep.checkTicketAmount(baseStep);
        PaymentStepValue payStep = getNewPaymentStepValue(false);
        // Mode de paiement 'Entretien mensualisé'
        payStep.paymentMean = PaymentMean.MONTHLY_MAINTENANCE;
        // Envoi du ticket par mail
        payStep.sendTicketMode = SendTicketMode.MAIL_ONLY;
        // Le montant du paiement doit être égal au montant du ticket
        payStep.expectedValue = expectedTicketTotal;
        // Vérification du montant de la ligne de paiement
        PaymentStep.checkPaymentLineAmount(payStep);
        // On finalise le ticket
        PaymentStep.finalizeOrder(payStep);
        // TODO ajout controle base (attend Christophe Brazier)
    }
}
