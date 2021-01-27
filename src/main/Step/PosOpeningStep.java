package Step;

import Helpers.Element.WebElementHelper;
import Helpers.Loading.LoadingHelper;
import Helpers.Test.ReportHelper;
import View.Log.PosClosingView;
import View.Log.PosOpeningView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class PosOpeningStep {

    @Step("Ouvre la caisse")
    public static void openPos(WebDriver driver) throws InterruptedException {
        // Vue Ouverture de caisse
        PosOpeningView posOpeningView = new PosOpeningView(driver);
        // Confirme la date d'ouverture si nécessaire
        posOpeningView.clickConfirmOpeningDate(false);
        // Si une ouverture de caisse est demandée ou si on est déjà en ouverture
        if (posOpeningView.clickEnterPosOpening() || isAlreadyInOpening(driver)) {
            doOpenPos(driver, posOpeningView);
        }
        ReportHelper.attachScreenshot(driver);
    }

    @Step("Cloture de caisse")
    public static boolean closePos(WebDriver driver) throws InterruptedException {
        // Flag indiquant que la caisse a été cloturé, dans le cas ou celle-ci l'a été il faut se re logger sur la caisse
        boolean posHasBeenClosed = false;
        // Si cloture nécessaire
        PosClosingView closingView = new PosClosingView(driver);
        // Si une cloture est nécessaire
        if (closingView.enterPosClosing()) {
            doClosePos(driver, closingView);
            posHasBeenClosed = true;
        }
        ReportHelper.attachScreenshot(driver);
        return posHasBeenClosed;
    }

    /**
     * Ouvre la caisse
     * @param driver
     * @param openingView
     * @throws InterruptedException
     */
    private static void doOpenPos(WebDriver driver, PosOpeningView openingView) throws InterruptedException {
        // Clic bouton "Suivant"
        openingView.clickNext();
        // Clic bouton "Suivant"
        openingView.clickNext();
        // Approuve la différence de rendue si nécessaire
        openingView.clickApprovalOk(false);
        // Attend le chargement du cache
        LoadingHelper.waitUntilLoadIsFinished(driver, 120);
    }

    /**
     * Clos la caisse
     * @param driver
     * @param closingView
     * @throws InterruptedException
     */
    private static void doClosePos(WebDriver driver, PosClosingView closingView) throws InterruptedException {
        // Next
        closingView.clickNextBtn();
        // Next
        closingView.clickNextBtn();
        // Finaliser
        closingView.clickNextBtn();
        LoadingHelper.waitUntilLoadIsFinished(driver, 120);
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
