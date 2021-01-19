package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.DiscountStep;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.DiscountStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE767 extends BaseTest {

    @Parameters({"discountLabel", "discountedProductCode", "firstDiscountedPrice", "secondDiscountedPrice", "thirdDiscountedPrice"})
    @Test(description = "10% sur une sélection produits, puis 20% si 2 produits de cette sélection, puis 30% si 3 produits de cette sélection")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-767")
    public void noe767(String discountLabel, String discountedProductCode, String firstDiscountedPrice, String secondDiscountedPrice, String thirdDiscountedPrice) throws MalformedURLException, InterruptedException {
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Ajout du premier produit
        addDiscountedProductAndCheckDiscount(discountedProductCode, firstDiscountedPrice, discountLabel);
        // Ajout du deuxieme produit
        addDiscountedProductAndCheckDiscount(discountedProductCode, secondDiscountedPrice, discountLabel);
        // Ajout du troisieme produit
        addDiscountedProductAndCheckDiscount(discountedProductCode, thirdDiscountedPrice, discountLabel);
        // Vide le ticket
        TicketStep.deleteTicket(currentDriver);
    }

    /**
     * Ajoute un article et controle le montant de la promo
     * @param productCode
     * @param expectedValue
     * @param discountLabel
     * @throws InterruptedException
     */
    private void addDiscountedProductAndCheckDiscount(String productCode, String expectedValue, String discountLabel) throws InterruptedException {
        // Scan du produit
        ScanStep.scanValue(productCode, currentDriver);
        // Controle du montant de la promop
        DiscountStepValue discStepValue = getNewDiscountStepValue(false);
        discStepValue.expectedValue = expectedValue;
        discStepValue.discountLabel = discountLabel;
        discStepValue.associatedProduct = productCode;
        DiscountStep.checkDiscountLineAmount(discStepValue);
    }
}
