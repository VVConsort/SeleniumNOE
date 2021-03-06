package Step;

import Helpers.Loading.LoadingHelper;
import Helpers.DataBase.Ouragan.OuraganDBHelper;
import Helpers.Rest.Ouragan.OuraganRESTHelper;
import Helpers.Test.ReportHelper;
import View.Ticket.MergedDocumentsView;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.sql.SQLException;

public class OuraganStep {

    // Temps de chargement du BT
    private static final int BT_LOADING_TIME = 5000;

    @Step("Importe le bon de travail Ouragan")
    public static String postWorkOrderToOpenBravo(String pathToJsonFile) throws IOException {
        // Classe utilitaire pour les envoie vers l'OrderLoader
        OuraganRESTHelper helper = new OuraganRESTHelper();
        // Envoie la commande vers OB avec un nouvel messageId et retourne le code document
        return helper.updateMessageIdAndPostToOB(pathToJsonFile, true);
    }

    @Step("Retourne l'état de la commande {orderRef] dans Ouragan")
    public static String getOrderState(String dbUrl, String dbUser, String dbPassword, String orderRef) throws SQLException {
        // Classe utilitaire pour les requetes BDD Ouragan
        OuraganDBHelper dbHelper = new OuraganDBHelper();
        // On éxécute la requete et retourne l'état
        return dbHelper.getStateOfOrder(dbUrl, dbUser, dbPassword, orderRef);
    }

    /**
     * Ferme la fenetre des documents associés
     * @param driver
     */
    @Step("Fermeture des documents associés")
    private static void closeMergedDocuments(ChromeDriver driver) throws InterruptedException {
        MergedDocumentsView mergedDoc = new MergedDocumentsView(driver);
        // Si la fenetre des documents associés est ouverte
        if (mergedDoc.isPresent()) {
            mergedDoc.clickCancelButton();
        }
    }

    @Step("Ouverture du BT n° {docCode}")
    public static void openWorkOrder(String docCode, ChromeDriver currentDriver) throws InterruptedException {
        //Scan du BT
        ScanStep.scanValue(docCode, currentDriver);
        // Fermeture potentielle des docs associés
        closeMergedDocuments(currentDriver);
        // Attente chargement cache
        LoadingHelper.waitUntilLoadIsFinished(currentDriver, 30);
        //Thread.sleep(BT_LOADING_TIME);
        ReportHelper.attachScreenshot(currentDriver);
    }
}
