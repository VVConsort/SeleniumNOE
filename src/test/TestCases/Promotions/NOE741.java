package TestCases.Promotions;

import Enums.PaymentMean;
import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.PaymentStep;
import Step.ScanStep;
import Step.TicketStep;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE741 extends BaseTest {

    @Parameters({"productCode", "creditNoteCode"})
    @Test(description = " encaisser un achat et passer un avoir déjà consommé émis depuis OpenBravo")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-741")
    public void noe741(String productCode, String creditNoteCode) throws MalformedURLException, InterruptedException {
        // Log OB
        currentDriver = LoggingStep.launchAndLogOB();
        // Scan article
        ScanStep.scanValue(productCode, currentDriver);
        // On tente d'utiliser l'avoir déjà cramé
        PaymentStep.tryUsedCreditNote(newPaymentStepValue(null, null, PaymentMean.CREDIT_NOTE, creditNoteCode, false));
        // Fermeture de la fenêtre de recherche d'avoir
        PaymentStep.closeCreditNoteSearchView(currentDriver);
        // Vidage du ticket
        TicketStep.deleteTicket(currentDriver);
    }
}
