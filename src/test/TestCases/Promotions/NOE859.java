package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE859 extends BaseTest {

    @Parameters({"crossedPriceProduct", "familyDiscountProduct", "expectedTotal"})
    @Test(description = "Acheter un produit A en promotion (Prix barré) et un produit B ayant une offre commerciale de 10% sur la famille du produit")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-859")
    public void noe859(String crossedPriceProduct, String familyDiscountProduct, String expectedTotal) throws MalformedURLException, InterruptedException {
        // Lance et se log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout des produits brix barrés et promos
        ScanStep.scanValues(new String[]{crossedPriceProduct, familyDiscountProduct}, driver);
        // Vérifie le montant total à payer
        BaseStepValue stepValue = new BaseStepValue(driver, softAssert, false);
        stepValue.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(stepValue);
        // Suppression ticket
        TicketStep.deleteTicket(driver);
    }
}

