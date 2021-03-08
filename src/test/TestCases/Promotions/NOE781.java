package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import Step.*;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import Step.Value.LoggingStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

/**
 * Remise dédiée Magasin : offre sur une région spécifique
 */
public class NOE781 extends BaseTest {

    @Parameters({"familyDiscountProduct", "discountLabel", "expectedDiscountAmount", "expectedTotalWithDiscount", "expectedTotalWithoutDiscount", "noDiscountPos", "noDiscountChromeProfilePath", "noDiscountChromeProfileName"})
    @Test(description = "Remise dédiée Magasin : offre sur une région spécifique")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-787")
    public void noe781(String familyDiscountProduct, String discountLabel, String expectedDiscountAmount, String expectedTotalWithDiscount, String expectedTotalWithoutDiscount, String noDiscountPos, String noDiscountChromeProfilePath, String noDiscountChromeProfileName) throws MalformedURLException, InterruptedException {
        // Lance et se log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout du produits en promo
        ScanStep.scanValue(familyDiscountProduct, driver);
        // Valeur à controler
        DiscountStepValue discountStepValue = getNewDiscountStepValue(false);
        discountStepValue.discountLabel = discountLabel;
        discountStepValue.expectedValue = expectedDiscountAmount;
        discountStepValue.associatedProduct = familyDiscountProduct;
        // Vérifie l'affichage de la promotion
        DiscountStep.checkDiscountLineAmount(discountStepValue);
        // Controle du montant de la promo
        BaseStepValue ticketStep = getNewBaseStepValue(false);
        ticketStep.expectedValue = expectedTotalWithDiscount;
        TicketStep.checkTotalToPay(ticketStep);
        // Suppression et fermeture du navigateur
        TicketStep.deleteTicket(driver);
        // Fermeture du navigateur
        closeBrowser();
        // Lancement de la caisse non éligible au promo
        LoggingStepValue logStep = new LoggingStepValue(TestSuiteProperties.OB_POS_URL, noDiscountPos, noDiscountChromeProfilePath, noDiscountChromeProfileName, TestSuiteProperties.USERNAME, TestSuiteProperties.PASSWORD);
        driver = LoggingStep.launchAndLogToOpenBravo(logStep);
        // On affecte le nouveau driver
        discountStepValue.driver = driver;
        // Fermeture/Ouverture si nécessaire
        PosOpeningStep.closePos(driver);
        PosOpeningStep.openPos(driver);
        // Ajout du produit en promo
        ScanStep.scanValue(familyDiscountProduct, driver);
        // Controle le non affichage de la promo
        DiscountStep.isDiscountNotPresent(discountStepValue);
        // Controle le montant du ticket
        ticketStep.expectedValue = expectedTotalWithoutDiscount;
        TicketStep.checkTotalToPay(ticketStep);
        // Vidage ticket
        TicketStep.deleteTicket(driver);
    }
}
