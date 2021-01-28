package Step;

import Helpers.Element.WebElementHelper;
import Helpers.Loading.LoadingHelper;
import Helpers.Test.ReportHelper;
import View.Log.PosClosingView;
import View.Log.PosOpeningView;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeDriver;

public class PosOpeningStep {

    @Step("Ouvre la caisse")
    public static void openPos(ChromeDriver driver) throws InterruptedException {
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
    public static void closePos(ChromeDriver driver) throws InterruptedException {
        // Si cloture nécessaire
        PosClosingView closingView = new PosClosingView(driver);
        // Si une cloture est nécessaire
        if (closingView.enterPosClosing()) {
            doClosePos(driver, closingView);
        }
        ReportHelper.attachScreenshot(driver);

    }

    /**
     * Ouvre la caisse
     * @param driver
     * @param openingView
     * @throws InterruptedException
     */
    private static void doOpenPos(ChromeDriver driver, PosOpeningView openingView) throws InterruptedException {
        boolean hasNextBtn;
        // Tant qu'il y a un bouton "Suivant" à l'écran
        do {
            hasNextBtn = openingView.clickNext(false);
            Thread.sleep(500);
            // Approuve la différence de rendue si nécessaire
            openingView.confirmApprovalReason(false);
        } while (hasNextBtn);
        // Attend le chargement du cache
        LoadingHelper.waitUntilLoadIsFinished(driver, 120);
    }

    /**
     * Clos la caisse
     * @param driver
     * @param closingView
     * @throws InterruptedException
     */
    private static void doClosePos(ChromeDriver driver, PosClosingView closingView) throws InterruptedException {
        boolean hasNextBtn;
        // Tant qu'il y a un bouton "Suivant" à l'écran
        do {
            hasNextBtn = closingView.clickNext(false);
            Thread.sleep(500);
            // Approuve la différence de rendue si nécessaire
            closingView.confirmApprovalReason(false);
        } while (hasNextBtn);
        // Attend chargement
        LoadingHelper.waitUntilLoadIsFinished(driver, 120);
        // On se relog
        LoggingStep.logToOpenBravo(driver);
    }

    /**
     * Renvoie vrai si l'on est déjà en ouverture de caisse
     * @param driver
     * @return
     */
    private static boolean isAlreadyInOpening(ChromeDriver driver) {
        return WebElementHelper.getElementFromText("Ouverture caisse", driver) != null;
    }
}
