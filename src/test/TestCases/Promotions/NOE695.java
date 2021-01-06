package TestCases.Promotions;

import Enums.PaymentMean;
import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.PaymentStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE695 extends BaseTest {

    @Parameters({"productCode", "creditNoteCode","expectedPendingAmount"})
    @Test(description = "Passer un bon d'achat Fidélité A sur la caisse OpenBravo et voir son prix diminuer")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-695")
    public void noe695(String productCode, String creditNoteCode, String expectedPendingAmount) throws MalformedURLException, InterruptedException {
        // Log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // Ajout produit
        ScanStep.scanValue(productCode, currentDriver);
        // Jeu de données step payement
        PaymentStepValue step = newPaymentStepValue(expectedPendingAmount, null, PaymentMean.CREDIT_NOTE, creditNoteCode, false);
        // Ajout avoir
        PaymentStep.applyCreditNote(step);
        // Control prix restant
        PaymentStep.checkPendingAmount(step);
        // Retirer ligne de paiement
        PaymentStep.removePaymentLine(step);
        // Vidage ticket
        TicketStep.deleteTicket(currentDriver);
    }
}
