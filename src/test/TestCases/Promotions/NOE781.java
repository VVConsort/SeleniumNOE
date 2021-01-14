package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import Step.*;
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
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Ajout du produits en promo
        ScanStep.scanValue(familyDiscountProduct, currentDriver);
        // Vérifie l'affichage de la promotion
        DiscountStep.checkDiscountLineAmount(newDiscountStepValue(expectedDiscountAmount, discountLabel, familyDiscountProduct, false));
        // Controle du montant de la promo
        TicketStep.checkTotalToPay(newTicketStepValue(expectedTotalWithDiscount, false));
        // Suppression et fermeture du navigateur
        TicketStep.deleteTicket(currentDriver);
        // Fermeture du navigateur
        closeBrowser();
        // Lancement de la caisse non éligible au promo
        currentDriver = LoggingStep.launchAndLogOB(newLoggingStepValue(TestSuiteProperties.OB_POS_URL, noDiscountPos, noDiscountChromeProfilePath, noDiscountChromeProfileName, TestSuiteProperties.USERNAME, TestSuiteProperties.PASSWORD));
        // Ouverture de la caisse
        PosOpeningStep.openPos(currentDriver);
        // Ajout du produit en promo
        ScanStep.scanValue(familyDiscountProduct, currentDriver);
        // Controle le non affichage de la promo
        DiscountStep.isDiscountNotPresent(newStepValue(discountLabel, false));
        // Controle le montant du ticket
        TicketStep.checkTotalToPay(newStepValue(expectedTotalWithoutDiscount, false));
        // Vidage ticket
        TicketStep.deleteTicket(currentDriver);
    }
}
