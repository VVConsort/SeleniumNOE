package TestCases.Ouragan;

import Helpers.Test.BaseTest;
import Step.*;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Ignore;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE700 extends BaseTest {

    @Parameters({"jsonFilePath", "discountLabel", "discountAmount", "expectedTotal"})
    @Test(description = "Scanner un RA contenant une remise manuelle")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-700")
    public void noe700(String jsonFilePath, String discountLabel, String discountAmount, String expectedTotal) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Ouverture du BT intégré
        OuraganStep.openWorkOrder(documentCode, driver);
        // Données à controler
        DiscountStepValue discStepValue = new DiscountStepValue(driver, softAssert, false);
        discStepValue.expectedValue = discountAmount;
        discStepValue.discountLabel = discountLabel;
        // Controle du montant de la promo
        DiscountStep.checkDiscountLineAmount(discStepValue);
        // Controle du montant du ticket
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(stepValue);
        // Vidage du ticket
        TicketStep.deleteWorkOrder(driver);
    }
}
