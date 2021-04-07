package TestCases.Promotions;

import Enums.Payment.PaymentMean;
import Enums.Payment.SendTicketMode;
import Helpers.Test.BaseTest;
import Step.*;
import Step.Value.DiscountStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

public class NOE681 extends BaseTest {

    @Parameters({"jsonFilePath", "couponAmount"})
    @Test(description = "Bon Achat : Génération d'un bon d'achat \"Promesse client\"")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-681")
    public void noe681(String jsonFilePath, String couponAmount) throws IOException, InterruptedException, SQLException {
        // Envoie du relevé atelier vers OB pour génèrer un bon d'achat promesse client
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Fermeture du potentiel ticket ouvert
        TicketStep.deleteTicket(driver);
        // Ouverture du BT intégré
        OuraganStep.openWorkOrder(documentCode, driver);
        // On paie en espèce
        PaymentStepValue payStep = getNewPaymentStepValue(false);
        payStep.paymentMean = PaymentMean.CASH;
        payStep.sendTicketMode = SendTicketMode.MAIL_ONLY;
        payStep.email = "noetest@norauto.com";
        PaymentStep.payWithCash(payStep);
        // On finalise la commande
        PaymentStep.finalizeOrder(payStep);
        // Vérifie la génération de coupon et retourne le code de celui-ci
        DiscountStepValue discountStepValue = getNewDiscountStepValue(false);
        discountStepValue.expectedValue = true;
        DiscountStep.checkCouponCreation(couponAmount, discountStepValue);
    }
}
