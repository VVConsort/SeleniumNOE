package Helpers.Json;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Classe utilitaire pour les JSON Ouragan
 */
public class OuraganJsonHelper {

    // Clée Message id
    private static final String JSON_MESSAGE_ID_KEY = "messageId";
    // Clé JSON pour data
    private static final String JSON_DATA_ARRAY_KEY = "data";
    // Clé JSON pour le num de doc
    private static final String JSON_DOCUMENT_NO_KEY = "documentNo";
    // Clé JSON pour la référence commande
    private static final String JSON_ORDER_REF_KEY = "orderReference";
    // Clé JSON pour le montant
    private static final String JSON_GROSS_AMOUNT_KEY = "grossAmount";
    // Clé JSON pour l'accompte cliet
    private static final String JSON_PAID_DEPOSIT_KEY = "paidDeposit";
    // Clé début tableau ligne
    private static final String JSON_LINES_ARRAY_KEY = "lines";
    // Clé ID de la ligne
    private static final String JSON_LINE_ID_KEY = "lineID";
    // Clé Quantité article ligne
    private static final String JSON_LINE_QUANTITY_KEY = "qty";
    // Clé Nom du produit
    private static final String JSON_LINE_PRODUCT_NAME_KEY = "productName";
    // Clé Prix unitaire ligne
    private static final String JSON_LINE_UNIT_PRICE_KEY = "unitPrice";
    // Clé Prix net total de la ligne
    private static final String JSON_LINE_GROSS_AMOUNT_KEY = "grossAmount";
    // Clé Type de produit
    private static final String JSON_LINE_PRODUCT_TYPE_KEY = "productType";
    // Clé Produit de type "produit"
    private static final String JSON_LINE_PRODUCT_TYPE_PRODUCT_VALUE = "I";
    // Clé Produit de type forfait
    private static final String JSON_LINE_PRODUCT_TYPE_PACKAGE_VALUE = "F";
    // Clé Produit de type service
    private static final String JSON_LINE_PRODUCT_TYPE_SERVICE_VALUE = "S";
    // Clé Promotion web
    private static final String JSON_LINE_ARRAY_WEB_DISCOUNTS_ARRAY_KEY = "webDiscounts";
    // Clé Promotion manuelle
    private static final String JSON_LINE_ARRAY_MANUAL_DISCOUNTS_ARRAY_KEY = "manualDiscounts";
    // Clé ID de la promo
    private static final String JSON_LINE_DISCOUNT_RULE_ID_KEY = "ruleId";
    // Clé Montant de la remise
    private static final String JSON_LINE_DISCOUNT_UNIT_DISCOUNT_KEY = "unitDiscount";

    /**
     * Retourne la trame JSON avec un nouvel messageId
     * @param json
     */
    public static String updateJsonWithNewMessageId(String json) {
        String newUUID  = UUID.randomUUID().toString();
        System.out.println(newUUID);
        JSONObject result = new JSONObject(json)
                .put(JSON_MESSAGE_ID_KEY, newUUID);
        return result.toString();
    }

    /**
     * Retourne le code document
     * @param json
     * @return
     */
    public static String getDocumentCode(String json) {
        JSONObject result = new JSONObject(json);
        return result.getJSONArray(JSON_DATA_ARRAY_KEY).getJSONObject(0).getString(JSON_DOCUMENT_NO_KEY);
    }

    /**
     * Retourne la référence commande
     * @param json
     * @return
     */
    public static String getOrderReference(String json) {
        JSONObject result = new JSONObject(json);
        return result.getJSONArray(JSON_DATA_ARRAY_KEY).getJSONObject(0).getString(JSON_ORDER_REF_KEY);
    }

    /**
     * Retourne le json sous forme de String à partir du chemin spécifié en paramètre
     * @param jsonFilePath
     * @return
     * @throws IOException
     */
    public static String getJsonFromFileURL(String jsonFilePath) throws IOException {
        String result = "";
        // On récupère le json à partir du chemin spécifié en paramètre
        Path fileName = Path.of(jsonFilePath);
        // On retourne le json
        return Files.readString(fileName);
    }

}
