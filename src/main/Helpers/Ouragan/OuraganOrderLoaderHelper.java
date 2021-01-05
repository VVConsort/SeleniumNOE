package Helpers.Ouragan;

import Helpers.Rest.RESTHelper;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import org.testng.Assert;

import java.io.IOException;

/**
 * Classe utilitaire pour l'import des BR Ouragan
 */
public class OuraganOrderLoaderHelper {

    /**
     * Poste un BT Ouragan vers OB
     *
     * @param orderLoaderSynchronizedProcessing
     * @param json
     * @param orderLoaderUrl
     * @param orderLoaderUser
     * @param orderLoaderPassword
     * @return
     */
    public int postOuraganOrderToOpenBravo(String json, String orderLoaderUrl, String orderLoaderUser, String orderLoaderPassword, String orderLoaderSynchronizedProcessing) {
        // Post vers l'OrderLoader et recupération du code réponse
        return RESTHelper.postJson(getCompleteOrderLoaderURL(orderLoaderUrl, orderLoaderUser, orderLoaderPassword, orderLoaderSynchronizedProcessing), OuraganJsonHelper.updateJsonWithNewMessageId(json));
    }

    /**
     * Retourne l'URL completée de l'OrderLoader
     *
     * @param orderLoaderUrl
     * @param orderLoaderUser
     * @param orderLoaderPassword
     * @param synchronizedProcessing
     */
    private String getCompleteOrderLoaderURL(String orderLoaderUrl, String orderLoaderUser, String orderLoaderPassword, String synchronizedProcessing) {
        String result;
        result = orderLoaderUrl + "?user=" + orderLoaderUser + "&password=" + orderLoaderPassword + "&synchronizedProcessing=" + synchronizedProcessing;
        return result;
    }

    /**
     * On charge la commande en utilisant les paramètres OrderLoader définié dans le Properties de la TestSuite
     *
     * @param json
     * @return
     */
    public int postOuraganOrderToOpenBravo(String json) throws IOException {
        return postOuraganOrderToOpenBravo(json, TestSuiteProperties.ORDER_LOADER_URL, TestSuiteProperties.ORDER_LOADER_USER, TestSuiteProperties.ORDER_LOADER_PASSWORD, TestSuiteProperties.ORDER_LOADER_SYNCHRONIZED_PROCESSING);
    }

    /**
     * Met a jour le messageId et poste la commande vers l'OrderLoader OB
     *
     * @param jsonPath
     * @param assertResponseCode si vrai alors hard check la réponse à l'envoi
     * @return le code document
     * @throws IOException
     */
    public String updateMessageIdAndPostToOB(String jsonPath, boolean assertResponseCode) throws IOException {
        // On recupère le json à partir du chemin
        String json = OuraganJsonHelper.getJsonFromFileURL(jsonPath);
        // On change le messageId
        json = OuraganJsonHelper.updateJsonWithNewMessageId(json);
        // On post le JSON vers OB
        int responseCode = postOuraganOrderToOpenBravo(json);
        // Si assertResponseCode on test le code retour
        if(assertResponseCode)
        {
            Assert.assertEquals(responseCode,RESTHelper.OK_RESPONSE_CODE);
        }
        // On retourne le code document
        return OuraganJsonHelper.getDocumentCode(json);
    }
}
