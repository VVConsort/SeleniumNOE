package TestCases.Promotions;

import Step.DiscountStep;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import Helpers.Test.BaseTest;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE749 extends BaseTest {

    @Parameters({"productACode","productBCode", "expectedTotal","discountLabel","expectedDiscountAmount"})
    @Test(description = "Multi-promo : Promotion de 1 produits acheté A, XX€ de remise offert sur le deuxième produit similaire acheté")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-749")
    public void noe749(String productACode, String productBCode,String expectedTotal,String discountLabel,String expectedDiscountAmount) throws MalformedURLException, InterruptedException {
        // Log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Scan des produits
        ScanStep.scanValues(new String[]{productACode,productBCode}, currentDriver);
        // Vérifie la présence et le montant de la promotion
        DiscountStep.checkDiscountLineAmount(newDiscountStepValue(expectedDiscountAmount,discountLabel,null,false));
        // Controle que la réduction est bien appliquée au ticket
        TicketStep.checkTotalToPay(newStepValue(expectedTotal, false));
        // Suppression ticket
        TicketStep.deleteTicket(currentDriver);
    }
}
