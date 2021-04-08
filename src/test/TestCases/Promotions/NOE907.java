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

public class NOE907 extends BaseTest {

    @Parameters({"productCode", "cardToSCan", "expectedTotalToPay"})
    @Test(description = "Carte de collaborateur: Vérification du plafond de 30%max appliqué avec une promotion marketing existante")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-907")
    public void noe907(String productCode, String cardToSCan, String expectedTotalToPay) throws MalformedURLException, InterruptedException {
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout du produit disposant de -10%
        ScanStep.scanValue(productCode, driver);
        // Scan de la carte collab
        ScanStep.scanValue(cardToSCan, driver);
        // Contrôle que le montant total de remise est égal à 30%
        BaseStepValue baseStep = getNewBaseStepValue(false);
        baseStep.expectedValue = expectedTotalToPay;
        TicketStep.checkTotalToPay(baseStep);
        // Vidage du ticket
        TicketStep.deleteTicket(driver);

    }
}
