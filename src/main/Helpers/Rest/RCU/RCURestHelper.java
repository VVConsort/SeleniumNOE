package Helpers.Rest.RCU;

import Enums.Customer.CustomerType;
import Enums.REST.RESTCodeStatut;
import Helpers.Json.RCUJsonHelper;
import Helpers.Rest.RESTHelper;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import Serializable.Customer.Customer;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RCURestHelper extends RESTHelper {

    // Clé id/token header
    private static final String AUTH_HEADER_KEY = "Authorization";
    // Clé code pays header
    private static final String COUNTRY_CODE_HEADER_KEY = "countryCode";
    // Clé code langage header
    private static final String LANGUAGE_CODE_HEADER_KEY = "language";
    // Clé customer id header
    private static final String CUSTOMER_ID_KEY = "customerId";

    // Préfxx token d'accès
    private static final String TOKEN_PREFIX = "bearer ";
    // Token
    private static String accesToken = "";

    /**
     * Recherche et retourne le client à partir de son id
     * @param customer
     * @return
     * @throws IOException
     */
    public static String getCustomer(Customer customer) throws IOException {
        // Récupère le token si nécessaire
        setToken();
        // GET et récup de la réponse
        //get(String URL, Map<String, String> headersArg)
        Response response = get(getGetCustomerURL(customer), getHeaderArgs(customer.country.getRCUValue(), customer.language.getRCUValue()));
        // Si l'appel est OK
        if (isResponseOk(response)) {
            // Construction du json à partir de la rép
            JSONObject jsonBody = new JSONObject(response.body().string());
            return jsonBody.toString();
        }
        return "";
    }

    /**
     * Retourne le client à partir du nom/prénom/mail
     * @param cust
     * @return
     * @throws IOException
     */
    public static String searchCustomer(Customer cust) throws IOException {
        // Récupère le token si nécessaire
        setToken();
        //Test branches
        // POST et récup de la réponse
        Response response = post(TestSuiteProperties.RCU_SEARCH_CUST_URL, getHeaderArgs(cust.country.getRCUValue(), cust.language.getRCUValue()), RCUJsonHelper.getSearchCustomerBody(cust));
        // Si l'appel est OK
        if (isResponseOk(response)) {
            // Construction du json à partir de la rép
            JSONObject jsonBody = new JSONObject(response.body().string());
            // On stock l'id client si il y a un seul résultat
            if (RCUJsonHelper.getSearchResultTotal(jsonBody.toString(), cust.type.equals(CustomerType.PERSON)).equals("1")) {
                cust.customerId = RCUJsonHelper.getCustomerId(jsonBody.toString(), cust.type.equals(CustomerType.PERSON));
            }
            return jsonBody.toString();
        }
        return "";
    }

    /**
     * Renvoi vrai si le server renvoi OK
     * @param response
     * @return
     */
    private static boolean isResponseOk(Response response) {
        return response != null && (response.code() == RESTCodeStatut.OK.getCode() || response.code() == RESTCodeStatut.OK_NO_CONTENT.getCode());
    }

    /**
     * Récupère le token auprès d'RCU si nécessaire
     * @throws IOException
     */
    private static void setToken() throws IOException {
        // Seulement si le token n'a pas déjà été récupéré
        if (accesToken == null || accesToken.isEmpty()) {
            // On passe l'ID/ident dans le header pour récupèrer le token
            Map headerArg = new HashMap<String, String>();
            headerArg.put(AUTH_HEADER_KEY, TestSuiteProperties.RCU_GET_TOKEN_ID);
            // POST, récup du token
            Response response = post(TestSuiteProperties.RCU_AUTH_URL, headerArg, "{}");
            // Si l'appel est OK
            if (isResponseOk(response)) {
                // Stockage du token et son prefix
                accesToken = TOKEN_PREFIX + RCUJsonHelper.getAccesTokenFromAuthResponse(response.body().string());
            }
        }
    }

    /**
     * Construit et retourne les arguments header pour la recherche client
     * @param countryCode
     * @param language
     * @return
     */
    private static Map getHeaderArgs(String countryCode, String language) {
        Map headerArgs = new HashMap<String, String>();
        setTokenInHeader(headerArgs);
        headerArgs.put(COUNTRY_CODE_HEADER_KEY, countryCode);
        headerArgs.put(LANGUAGE_CODE_HEADER_KEY, language);
        return headerArgs;
    }

    /**
     * Met le token dans le header
     * @param headerArgs
     */
    private static void setTokenInHeader(Map<String, String> headerArgs) {
        headerArgs.put(AUTH_HEADER_KEY, accesToken);
    }

    /**
     * Retourne l'url du GET complétée avec l'id client
     * @return
     */
    private static String getGetCustomerURL(Customer cust) {
        return TestSuiteProperties.RCU_GET_CUST_URL + cust.customerId;
    }

    /**
     * @param cust
     * @return
     */
    /*private Map getArchiveCustomerHeaderArgsMap(Customer cust) {
        Map headerArgs = new HashMap<String, String>();
        setTokenInHeader(headerArgs);
        headerArgs.put(CUSTOMER_ID_KEY, cust.customerId);
        headerArgs.put(LANGUAGE_CODE_HEADER_KEY, cust.language.getRCUValue());
        return headerArgs;
    }*/

    /**
     * Supprime logiquement un client en le flagant comme archivé
     * @param cust
     * @return
     * @throws IOException
     */
    public static boolean archiveCustomer(Customer cust) throws IOException {
        // Récupère le token si nécessaire
        setToken();
        // PUT avec le customerId afin de l'archiver
        Response response = put(getArchiveCustomerURL(cust.customerId), getHeaderArgs(cust.country.getRCUValue(), cust.language.getRCUValue()), RCUJsonHelper.getArchiveCustomerBody());
        return isResponseOk(response);
    }

    /**
     * Construit et retourne l'URL pour le get customer
     * @param customerId
     * @return
     */
    private static String getArchiveCustomerURL(String customerId) {
        return TestSuiteProperties.RCU_ARCHIVE_CUST_URL + customerId;
    }

}
