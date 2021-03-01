package Helpers.Json;

import Serializable.Customer.Customer;
import org.json.JSONObject;

public class RCUJsonHelper {

    // Clé token
    private static final String ACCES_TOKEN_JSON_KEY = "access_token";
    // Clé "page"
    private static final String PAGE_KEY = "page";
    // CLé "pageSize"
    private static final String PAGE_SIZE_KEY = "pageSize";
    // Clé nom
    private static final String LAST_NAME_KEY = "lastName";
    // Clé prénom
    private static final String FIRST_NAME_KEY = "firstName";
    // Clé mail
    private static final String EMAIL_KEY = "email";
    // Clé code postal
    private static final String POST_CODE_KEY = "postCode";
    // Clé type business/personne
    private static final String TYPE_KEY = "type";
    // Clé objet recherche client b2c
    private static final String B2C_OBJECT_KEY = "b2cCustomers";
    // Clé objet recherche client b2b
    private static final String B2B_OBJECT_KEY = "b2bCustomers";
    // Clé nombre résultat
    private static final String TOTAL = "total";
    // Clé id client rcu
    private static final String CUSTOMER_ID_KEY = "customerId";
    // Clé tableau résultat recherche
    private static final String CUST_SEARCH_RESULT_ARRAY_KEY = "customerSearches";
    // Clé flag archivé
    private static final String ARCHIVED_KEY = "archived";

    /**
     * Extrait et retourne le token d'accès RCU
     * @return
     */
    public static String getAccesTokenFromAuthResponse(String authResp) {
        // Construction du json à partir de la rép
        JSONObject jsonResp = new JSONObject(authResp);
        // Retour du token
        return jsonResp.getString(ACCES_TOKEN_JSON_KEY);
    }

    /**
     * Construit et retourne le corps contenant les critères pour la recherche client
     * @param customer
     * @return
     */
    public static String getSearchCustomerBody(Customer customer) {
        JSONObject searchCustBody = new JSONObject();
        // Champ obligatoire
        searchCustBody.put(PAGE_KEY, 1);
        searchCustBody.put(PAGE_SIZE_KEY, 50);
        // Nom
        if (customer.lastName != null && !customer.lastName.isEmpty()) {
            searchCustBody.put(LAST_NAME_KEY, customer.lastName);
        }
        // Prénom
        if (customer.firstName != null && !customer.firstName.isEmpty()) {
            searchCustBody.put(FIRST_NAME_KEY, customer.firstName);
        }
        // Mail
        if (customer.email != null && !customer.email.isEmpty()) {
            searchCustBody.put(EMAIL_KEY, customer.email);
        }
        // Code postal
        if (customer.postalCode != null && !customer.postalCode.isEmpty()) {
            searchCustBody.put(POST_CODE_KEY, customer.postalCode);
        }
        // Type
        searchCustBody.put(TYPE_KEY, customer.type.getRCUValue());
        return searchCustBody.toString();
    }

    /**
     * Construit et retourne le corps pour l'archivage client
     * @return
     */
    public static String getArchiveCustomerBody() {
        JSONObject archiveCustBody = new JSONObject();
        return archiveCustBody.put(ARCHIVED_KEY, true).toString();
    }

    /**
     * Renvoi le nombre de résultat pour la rechercher en b2c
     * @param json
     * @return
     */
    private static String getB2CSearchTotal(String json) {
        JSONObject parser = new JSONObject(json);
        return parser.getJSONObject(B2C_OBJECT_KEY).get(TOTAL).toString();
    }

    /**
     * Renvoi le nombre de résultat pour la rechercher en b2b
     * @param json
     * @return
     */
    private static String getB2BSearchTotal(String json) {
        JSONObject parser = new JSONObject(json);
        return parser.getJSONObject(B2B_OBJECT_KEY).get(TOTAL).toString();
    }

    /**
     * Retourne le nombre de résultat de  la recherche selon le type de client
     * @param json
     * @param isPerson
     * @return
     */
    public static String getSearchResultTotal(String json, boolean isPerson) {
        return isPerson ? getB2CSearchTotal(json) : getB2BSearchTotal(json);

    }

    /**
     * Extrait et retourne l'id client
     * @param json
     * @return
     */
    public static String getB2CCustomerId(String json) {
        JSONObject parser = new JSONObject(json);
        // On prend arbitrement le premier client retourné
        return parser.getJSONObject(B2C_OBJECT_KEY).getJSONArray(CUST_SEARCH_RESULT_ARRAY_KEY).getJSONObject(0).get(CUSTOMER_ID_KEY).toString();
    }

    /**
     * Extrait et retourne l'id client
     * @param json
     * @return
     */
    public static String getB2BCustomerId(String json) {
        JSONObject parser = new JSONObject(json);
        // On prend arbitrement le premier client retourné
        return parser.getJSONObject(B2B_OBJECT_KEY).getJSONArray(CUST_SEARCH_RESULT_ARRAY_KEY).getJSONObject(0).get(CUSTOMER_ID_KEY).toString();
    }

    /**
     * Retourne le nombre de résultat de  la recherche selon le type de client
     * @param json
     * @param isPerson
     * @return
     */
    public static String getCustomerId(String json, boolean isPerson) {
        return isPerson ? getB2CCustomerId(json) : getB2BCustomerId(json);

    }



}
