package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.PaymentStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE3190 extends BaseTest {

    @Parameters({"productCode", "voucherCode"})
    @Test(description = "Vérifier que l'application d'un bon d'achat supérieur au montant du ticket ne passe pas le montant ticket en négatif")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-3190")
    public void noe3190(String productCode, String voucherCode) throws IOException, InterruptedException {
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Fermeture du potentiel ticket ouvert
        TicketStep.deleteTicket(driver);
        // Ajout de l'article au panier
        ScanStep.scanValue(productCode, driver);
        // On utilise le coupon sur le ticket
        PaymentStepValue payStep = getNewPaymentStepValue(false);
        payStep.paymentId = voucherCode;
        PaymentStep.useVoucher(payStep);
        // On vérifie que le montant du ticket est égal à 0.00
        BaseStepValue baseStep = getNewBaseStepValue(false);
        baseStep.expectedValue = "0.00";
        TicketStep.checkTotalToPay(baseStep);
        // Retire le voucher du ticket
        PaymentStep.removeVoucher(payStep);
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
    }
}
