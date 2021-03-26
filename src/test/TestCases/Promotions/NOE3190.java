package TestCases.Promotions;

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

public class NOE3190 extends BaseTest {

    @Parameters({"jsonFilePath", "productCode", "couponAmount"})
    @Test(description = "Vérifier que l'application d'un bon d'achat supérieur au montant du ticket ne passe pas le montant ticket en négatif")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-3190")
    public void noe3190(String jsonFilePath, String productCode, String couponAmount) throws IOException, InterruptedException, SQLException {
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
        payStep.sendTicketMode = SendTicketMode.MAIL_ONLY;
        payStep.email = "noetest@norauto.com";
        PaymentStep.payWithCash(payStep);
        // On finalise la commande
        PaymentStep.finalizeOrder(payStep);
        // Vérifie la génération de coupon et retourne le code de celui-ci
        DiscountStepValue discountStepValue = getNewDiscountStepValue(false);
        discountStepValue.expectedValue = true;
        String couponCode = DiscountStep.checkCouponCreation(couponAmount, discountStepValue);
        // Ajout de l'article au panier
        ScanStep.scanValue(productCode, driver);
        // On utilise le coupon sur le ticket
        payStep.paymentId = couponCode;
        PaymentStep.useVoucher(payStep);
        // On vérifie que le montant du ticket est égal à 0.00
        BaseStepValue baseStep = getNewBaseStepValue(false);
        baseStep.expectedValue = "0.00";
        TicketStep.checkTotalToPay(baseStep);
    }
}
