package TestCases.Promotions;

import Enums.Payment.PaymentMean;
import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.PaymentStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE741 extends BaseTest {

    @Parameters({"productCode", "creditNoteCode"})
    @Test(description = "Encaisser un achat et passer un avoir déjà consommé émis depuis OpenBravo")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-741")
    public void noe741(String productCode, String creditNoteCode) throws MalformedURLException, InterruptedException {
        // Log OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Scan article
        ScanStep.scanValue(productCode, driver);
        // On tente d'utiliser l'avoir déjà cramé
        PaymentStepValue payStepValue = getNewPaymentStepValue(false);
        payStepValue.paymentMean = PaymentMean.CREDIT_NOTE;
        payStepValue.paymentId = creditNoteCode;
        PaymentStep.tryUsedCreditNote(payStepValue);
        // Fermeture de la fenêtre de recherche d'avoir
        PaymentStep.closeCreditNoteSearchView(driver);
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
    }
}
