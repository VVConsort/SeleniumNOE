package Step;

import Helpers.Element.WaitHelper;
import View.Log.PosOpeningView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class PosOpeningStep {

    @Step("Ouvre la caisse")
    // driver, ?
    public static void openPos(WebDriver driver) {
        // Vue Ouverture de caisse
        PosOpeningView posOpeningView = new PosOpeningView(driver);
        // Confirme la date d'ouverture si nécessaire
        posOpeningView.confirmPosOpeningDate();
        // Confirme l'entrée en ouverture de caisse
        posOpeningView.enterPosOpening();
        // Clic bouton "Suivant"
        posOpeningView.clickNext();
        // Appuie sur "Finaliser"
        posOpeningView.clickNext();
        // Appuie sur "Traiter imprimer"
        // posOpeningView.clickNext();
        // Attend le chargement du cache
        WaitHelper.waitUntilLoadIsFinished(driver, 120);
    }
}
