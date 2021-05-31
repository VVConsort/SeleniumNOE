package TestCases.Ouragan;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.TicketLineStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE583 extends BaseTest {

    @Parameters({"jsonFilePath", "forfaitLabel", "forfaitPrice", "serviceLabel", "serviceGrossPrice", "serviceQuantity", "serviceUnitPrice", "expectedTotal"})
    @Test(description = "Scanner un RA contenant un forfait E et une prestation C")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-583")
    public void noe583(String jsonFilePath, String forfaitLabel, String forfaitPrice, String serviceLabel, String serviceGrossPrice, String serviceQuantity, String serviceUnitPrice, String expectedTotal) throws IOException, InterruptedException {
        // Intégration du BT
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Scan et ouverture du BT intégré
        OuraganStep.openWorkOrder(documentCode, driver);
        BaseStepValue baseValue = getNewBaseStepValue(false);
        // Controle du forfait
        baseValue.expectedValue = forfaitPrice;
        TicketLineStep.checkForfaitPrice(forfaitLabel, baseValue);
        // Controle quantité presta
        baseValue.expectedValue = serviceQuantity;
        TicketLineStep.checkQuantity(serviceLabel, baseValue);
        // Controle prix unitaire presta
        baseValue.expectedValue = serviceUnitPrice;
        TicketLineStep.checkUnitPrice(serviceLabel, baseValue);
        // Controle montant total presta
        baseValue.expectedValue = serviceGrossPrice;
        TicketLineStep.checkGrossPrice(serviceLabel, baseValue);
        // Controle montant total du ticket
        baseValue.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(baseValue);
        // Vidage du BT
        TicketStep.deleteWorkOrder(driver);
    }
}
