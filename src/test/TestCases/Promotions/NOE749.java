package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.DiscountStep;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE749 extends BaseTest {

    @Parameters({"productACode", "productBCode", "expectedTotal", "discountLabel", "expectedDiscountAmount"})
    @Test(description = "Multi-promo : Promotion de 1 produits acheté A, XX€ de remise offert sur le deuxième produit similaire acheté")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-749")
    public void noe749(String productACode, String productBCode, String expectedTotal, String discountLabel, String expectedDiscountAmount) throws MalformedURLException, InterruptedException {
        // Log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Scan des produits
        ScanStep.scanValues(new String[]{productACode, productBCode}, driver);
        DiscountStepValue discStepValue = getNewDiscountStepValue(false);
        discStepValue.expectedValue = expectedDiscountAmount;
        discStepValue.discountLabel = discountLabel;
        // Vérifie la présence et le montant de la promotion
        DiscountStep.checkDiscountLineAmount(discStepValue);
        // Controle que la réduction est bien appliquée au ticket
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(stepValue);
        // Suppression ticket
        TicketStep.deleteTicket(driver);
    }
}
