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

public class NOE683 extends BaseTest {

    @Parameters({"productCode", "voucherCode", "expectedTotal"})
    @Test(description = "Passer un bon d'achat A (Non Fidélité) sur la caisse OpenBravo et émis depuis OpenBravo")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-683")
    public void noe683(String productCode, String voucherCode, String expectedTotal) throws MalformedURLException, InterruptedException {
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Ajout du produit
        ScanStep.scanValue(productCode, currentDriver);
        // Ajout du bon d'achat
        PaymentStep.useVoucher(newPaymentStepValue(null, null, PaymentMean.VOUCHER, voucherCode, false));
        // Controle du prix du ticket
        TicketStep.checkTotalToPay(newStepValue(expectedTotal, false));
        // Vidage du ticket
        TicketStep.deleteTicket(currentDriver);
    }
}
