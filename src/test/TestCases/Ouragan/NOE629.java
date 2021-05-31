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

public class NOE629 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedDepositLabel", "expectedDeposit"})
    @Test(description = "Scanner et payer une Réservation contenant un article A avec paiement d'un acompte")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-629")
    public void noe629(String jsonFilePath, String expectedDepositLabel, String expectedDeposit) throws IOException, InterruptedException {
        // Intégration du BT
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Scan du BT
        OuraganStep.openWorkOrder(documentCode, driver);
        // 953 - RESERVATIONS, 40 €
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedDeposit;
        // Vérif prix unitaire
        TicketLineStep.checkUnitPrice(expectedDepositLabel, stepValue);
        // Vérif prix total ligne
        TicketLineStep.checkGrossPrice(expectedDepositLabel, stepValue);
        // Vérif quantité
        stepValue.expectedValue = "1";
        TicketLineStep.checkQuantity(expectedDepositLabel, stepValue);
        // Vérif total à payer
        stepValue.expectedValue = expectedDeposit;
        TicketStep.checkTotalToPay(stepValue);
        TicketStep.deleteWorkOrder(driver);
    }
}
