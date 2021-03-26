package TestCases.Ouragan;

import Enums.Payment.SendTicketMode;
import Helpers.Test.BaseTest;
import Step.*;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

public class NOE699 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedAmountToPay", "couponAmount"})
    @Test(description = "Scanner un RA contenant un bon d'achat car promesse client non tenue")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-699")
    public void noe699(String jsonFilePath, String expectedAmountToPay, String couponAmount) throws IOException, InterruptedException, SQLException {
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
        payStep.email = "noetest@gmail.com";
        PaymentStep.payWithCash(payStep);
        // On finalise la commande
        PaymentStep.finalizeOrder(payStep);
        // Vérifier qu'un bon d'achat a été généré
        DiscountStepValue discountStep = getNewDiscountStepValue(false);
        discountStep.expectedValue = true;
        DiscountStep.checkCouponIsCreated(couponAmount, discountStep);
    }
}
