package Step;

import Helpers.Ouragan.OuraganOrderLoaderHelper;
import io.qameta.allure.Step;

import java.io.IOException;

public class OuraganStep {

    @Step("Importe le bon de travail Ouragan")
    public static String postWorkOrderToOpenBravo(String pathToJsonFile) throws IOException {
        // Classe utilitaire pour les envoie vers l'OrderLoader
        OuraganOrderLoaderHelper helper = new OuraganOrderLoaderHelper();
        // Envoie la commande vers OB avec un nouvel messageId et retourne le code document
        return helper.updateMessageIdAndPostToOB(pathToJsonFile,true);
    }
}
