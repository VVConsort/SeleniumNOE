package Step;

import Helpers.Ouragan.OuraganDBHelper;
import Helpers.Ouragan.OuraganOrderLoaderHelper;
import io.qameta.allure.Step;

import java.io.IOException;
import java.sql.SQLException;

public class OuraganStep {

    @Step("Importe le bon de travail Ouragan")
    public static String postWorkOrderToOpenBravo(String pathToJsonFile) throws IOException {
        // Classe utilitaire pour les envoie vers l'OrderLoader
        OuraganOrderLoaderHelper helper = new OuraganOrderLoaderHelper();
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
}
