package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import Step.DiscountStep;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import View.Log.LogScreenView;
import View.OpenBravoLauncher;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

/**
 * Remise dédiée Magasin : offre sur une région spécifique
 */
public class NOE787 extends BaseTest {

    @Parameters({"familyDiscountProduct", "discountLabel", "expectedDiscountAmount", "expectedTotalWithDiscount", "expectedTotalWithoutDiscount", "noDiscountPos", "noDiscountChromeProfilePath", "noDiscountChromeProfileName"})
    @Test(description = "Remise dédiée Magasin : offre sur une région spécifique")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-787")
    public void noe787(String familyDiscountProduct, String discountLabel, String expectedDiscountAmount, String expectedTotalWithDiscount, String expectedTotalWithoutDiscount, String noDiscountPos, String noDiscountChromeProfilePath, String noDiscountChromeProfileName) throws MalformedURLException, InterruptedException {
        // Lance et se log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Ajout du produits en promo
        ScanStep.scanValue(familyDiscountProduct, currentDriver);
        // Vérifie l'affichage de la promotion
        // Controle du montant de la promo
        DiscountStep.checkDiscountLineAmount(newDiscountStepValue(expectedDiscountAmount, discountLabel, familyDiscountProduct, false));
        TicketStep.checkTotalToPay(newTicketStepValue(expectedTotalWithDiscount, false));
        // Suppression et fermeture du navigateur
        TicketStep.deleteTicket(currentDriver);
        // Fermeture du navigateur
        closeBrowser();
        // Lancement de la caisse non éligible au promo
        currentDriver = LoggingStep.launchAndLogOB(newLoggingStepValue(TestSuiteProperties.OB_POS_URL, noDiscountPos, noDiscountChromeProfilePath, noDiscountChromeProfileName, TestSuiteProperties.USERNAME, TestSuiteProperties.PASSWORD));
        // Ajout du produit en promo
        ScanStep.scanValue(familyDiscountProduct, currentDriver);
        // Controle le non affichage de la promo
        DiscountStep.isDiscountNotPresent(newStepValue(discountLabel, false));
        //checkDiscountNonDisplay(discountLabel);
        // Controle le montant du ticket
        TicketStep.checkTotalToPay(newStepValue(expectedTotalWithoutDiscount, false));
        //checkTotalToPayWithoutDiscount(expectedTotalWithoutDiscount);
        TicketStep.deleteTicket(currentDriver);
        //deleteTicket();
    }

    @Step("Ajout de l'article {familyDiscountProduct} bénéficiant d'une promotion -10% famille sur le centre de la caisse")
    public void addFamilyDiscountProduct(String familyDiscountProduct) throws InterruptedException {
        // On rentre un produit avec une réduction -10% sur sa famille nord uniquement
        ScanStep.scanValue(familyDiscountProduct, currentDriver);
    }

    @Step("Vérifie l'affichage de la promo {discountLabel}")
    public void checkDiscountLabel(String discountLabel) {
        getTicketPage().isDiscountPresent(discountLabel);
    }

    @Step("Vérifie que le montant de la promotion {discountLabel} est égal à {expectedDiscountAmount}")
    public void checkDiscountLineAmount(String discountLabel, String expectedDiscountAmount) {
        // Récupération du montant de la promotion
        String discountAmount = getTicketPage().getDiscountLineAmount(discountLabel);
        // Vérification
        softAssert.assertEquals(discountAmount, expectedDiscountAmount);
    }

    @Step("Controle que le montant total de la promotion est égale à {expectedTotalWithDiscount}")
    public void checkTotalToPayWithDiscount(String expectedTotalWithDiscount) {
        // On récupère le prix total
        String totalAmount = getTicketPage().getTotalAmount();
        // Comparaison avec la valeur attendue
        softAssert.assertEquals(totalAmount, expectedTotalWithDiscount);
    }

    @Step("Supprime le ticket et ferme OpenBravo")
    public void deleteTicketAndCloseBrowser() {
        deleteTicket();
        closeBrowser();
    }

    @Step("Ouverture d'OpenBravo avec la caisse {noDiscountPos} non éligible à la promotion")
    public void logOnNoDiscountPos(String noDiscountPos, String noDiscountChromeProfilePath, String noDiscountChromeProfileName) throws MalformedURLException {
        // Lancement d'OB avec le cache
        OpenBravoLauncher ob = new OpenBravoLauncher();
        // On lance OB sur la session Chrome contenant le cache de la caisse non éligible à la promo
        currentDriver = ob.launchOpenBravoWithCache(TestSuiteProperties.OB_POS_URL, noDiscountPos, noDiscountChromeProfilePath, noDiscountChromeProfileName);
        // Ecran de loggin
        LogScreenView logScreenView = new LogScreenView(currentDriver);
        // Set l'utilisateur
        logScreenView.setUserName(TestSuiteProperties.USERNAME);
        // Set le mdp
        logScreenView.setPassword(TestSuiteProperties.PASSWORD);
        // Connection
        logScreenView.clickConnexion();
    }

    @Step("Ajout de l'article {familyDiscountProduct} bénéficiant d'une promotion -10% sur un centre différent de celui de la caisse")
    public void addProduct(String familyDiscountProduct) throws InterruptedException {
        // On rentre un produit avec une réduction -10% sur sa famille
        ScanStep.scanValue(familyDiscountProduct, currentDriver);
    }

    @Step("Vérifie la non présence de la promotion {discountLabel} sur le ticket")
    public void checkDiscountNonDisplay(String discountLabel) {
        softAssert.assertEquals(getTicketPage().isDiscountPresent(discountLabel), false);
    }

    @Step("Controle que la promotion n'est pas appliquée au prix total")
    public void checkTotalToPayWithoutDiscount(String expectedTotalWithoutDiscount) {
        // On récupère le prix total
        String totalAmount = getTicketPage().getTotalAmount();
        // Comparaison avec la valeur attendue
        softAssert.assertEquals(totalAmount, expectedTotalWithoutDiscount);
    }
}
