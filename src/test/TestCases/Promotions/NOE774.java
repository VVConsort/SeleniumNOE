package TestCases.Promotions;

import Helpers.Element.WaitHelper;
import Helpers.Test.BaseTest;
import Step.*;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE774 extends BaseTest {

    @Parameters({"jsonFilePath"})
    @Test(description = "Remise immédiate : Montage des pneus offert pour l'achat de 2 pneus")
    public void noe774(String jsonFilePath, String expectedDiscountAmount, String discountLabel, String expectedTotal) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Scan du BT intégré
        ScanStep.scanValue(documentCode, currentDriver);
        // On ferme la fenetre des documents associés
        TicketStep.closeMergedDocuments(currentDriver);
        // On attend le chargement
        WaitHelper.waitUntilLoadIsFinished(currentDriver, 20);
        // Controle de la gratuité du montage
        DiscountStepValue discStepValue = getNewDiscountStepValue(false);
        discStepValue.expectedValue = expectedDiscountAmount;
        discStepValue.discountLabel = discountLabel;
        DiscountStep.checkDiscountLineAmount(discStepValue);
        // Controle du montant total
        BaseStepValue ticketStep = getNewBaseStepValue(false);
        ticketStep.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(ticketStep);
        // Vidage du ticket
        TicketStep.deleteWorkOrder(currentDriver);

    }


}
