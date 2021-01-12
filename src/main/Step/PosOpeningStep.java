package Step;

import Helpers.Element.WaitHelper;
import Helpers.Element.WebElementHelper;
import View.Log.PosClosingView;
import View.Log.PosOpeningView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class PosOpeningStep {

    @Step("Ouvre la caisse")
    // driver, ?
    public static void openPos(WebDriver driver) {
        // Si cloture nécessaire
        PosClosingView closingView = new PosClosingView(driver);
        // Si une cloture est nécessaire
        if (closingView.enterPosClosing()) {
            closePos(driver, closingView);
        }
        // Vue Ouverture de caisse
        PosOpeningView posOpeningView = new PosOpeningView(driver);
        // Confirme la date d'ouverture si nécessaire
        posOpeningView.clickConfirmOpeningDate();
        // Si une ouverture de caisse est demandée ou si on est déjà en ouverture
        if (posOpeningView.clickEnterPosOpening() || isAlreadyInOpening(driver)) {
            doOpenPos(driver, posOpeningView);
        }
    }

    @Step("Cloture de caisse")
    public static void closePos(WebDriver driver, PosClosingView closingView) {
        // Next
        closingView.clickNextBtn();
        // Next
        closingView.clickNextBtn();
        closingView.clickNextBtn();
        WaitHelper.waitUntilLoadIsFinished(driver, 120);
    }

    @Step("Ouvre la caisse")
    public static void doOpenPos(WebDriver driver, PosOpeningView openingView) {
        // Clic bouton "Suivant"
        openingView.clickNext();
        // Clic bouton "Suivant"
        openingView.clickNext();
        // Approuve la différence de rendue si nécessaire
        openingView.clickApprovalOk();
        // Appuie sur "Finaliser"
        //openingView.clickNext();
        // Attend le chargement du cache
        WaitHelper.waitUntilLoadIsFinished(driver, 120);
    }

    /**
     * Renvoie vrai si l'on est déjà en ouverture de caisse
     * @param driver
     * @return
     */
    private static boolean isAlreadyInOpening(WebDriver driver) {
        return WebElementHelper.getElementFromText("Ouverture caisse", driver) != null;
    }
}
