package Helpers.Json;

import Serializable.Customer.Customer;
import org.json.JSONArray;
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
    // Clé type business/personne
    private static final String TYPE_KEY = "type";
    // Clé titre
    private static final String TITLE_KEY = "title";
    // Clé langage
    private static final String LANGUAGE_KEY = "language";
    // Clé array emails client
    private static final String EMAILS_ARRAY_KEY = "emails";
    // Clé  adresse mail client
    private static final String RCU_EMAIL_ADDRESS = "emailAddress";
    // Clé array addresse client
    private static final String ADDRESS_ARRAY_KEY = "address";
    // Clé type addresse/tél/email
    private static final String RCU_TYPE_KEY = "type";
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
    //////// CLES ADRESSE////////////
    // Clé ligne 1 adr
    private static final String ADR_LINE1_KEY = "address1";
    // Clé ligne 2 adr
    private static final String ADR_LINE2_KEY = "address2";
    // Clé ligne 3 adr
    private static final String ADR_LINE3_KEY = "address3";
    // Clé ligne 4 adr
    private static final String ADR_LINE4_KEY = "address4";
    // Clé postal code
    private static final String POSTAL_CODE_KEY = "postCode";
    // Clé ville
    private static final String CITY_KEY = "city";
    // Clé code pays
    private static final String COUNTRY_CODE_KEY = "countryCode";
    // Valeur de "type" pour l'adresse principal
    private static final String PRINCIPAL_ADDRESS_TYPE_VALUE = "PRINCIPAL";
    // Valeur de "type" pour l'adresse de livraison
    private static final String SHIPPING_ADDRESS_TYPE_VALUE = "DELIVERY";
    // Valeur de "type" pour l'adresse de facturation
    private static final String INVOICE_ADDRESS_TYPE_VALUE = "BILLING";
    // Clé tableau téléphone
    private static final String RCU_PHONE_ARRAY_KEY = "phones";
    // Clé num tél
    private static final String RCU_PHONE_NUMBER_KEY = "phoneNumber";
    // Valeur de "type" pour le tél fixe
    private static final String FIX_PHONE_TYPE_VALUE = "FIX";
    // Valeur de "type" pour le tél mobile
    private static final String MOBILE_PHONE_TYPE_VALUE = "MOB";
    // Clé tableau optin
    private static final String OPTIN_ARRAY_KEY = "optins";
    // Clé ID optin
    private static final String OPTIN_ID_KEY = "id";
    // Clé valeur optin
    private static final String OPTIN_VALUE = "value";
    // Clé optin sms
    private static final String CONTRACTUAL_DOC_OPTIN_KEY = "OPTIN_CONTRACTUAL_DOCUMENTS";
    // Clé optin mail
    private static final String MAIL_OPTIN_KEY = "OPTIN_EMAIL";
    // Clé optin sms
    private static final String SMS_OPTIN_KEY = "OPTIN_SMS";

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
            searchCustBody.put(POSTAL_CODE_KEY, customer.postalCode);
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
     * Retourne l'id client
     * @param json
     * @return
     */
    public static String getCustomerId(String json) {
        JSONObject parser = new JSONObject(json);
        return parser.getString(CUSTOMER_ID_KEY);

    }

    /**
     * Retourne le nom du client
     * @param json
     * @return
     */
    public static String getCustomerLastName(String json) {
        JSONObject parser = new JSONObject(json);
        return parser.getString(LAST_NAME_KEY);
    }

    /**
     * Retourne le prénom du client
     * @param json
     * @return
     */
    public static String getCustomerFirstName(String json) {
        JSONObject parser = new JSONObject(json);
        return parser.getString(FIRST_NAME_KEY);
    }

    /**
     * Retourne la langue du client
     * @param json
     * @return
     */
    public static String getCustomerLanguage(String json) {
        JSONObject parser = new JSONObject(json);
        return parser.getString(LANGUAGE_KEY);
    }

    /**
     * Retourne le type du client (business,person,etc)
     * @param json
     * @return
     */
    public static String getCustomerType(String json) {
        JSONObject parser = new JSONObject(json);
        return parser.getString(TYPE_KEY);
    }

    /**
     * Retourne le titre du client (mr,mrs,compagny)
     * @param json
     * @return
     */
    public static String getCustomerTitle(String json) {
        JSONObject parser = new JSONObject(json);
        return String.valueOf(parser.getInt(TITLE_KEY));
    }

    /**
     * Retourne l'email du client
     * @param json
     * @return
     */
    public static String getCustomerEmail(String json) {
        JSONObject parser = new JSONObject(json);
        return parser.getJSONArray(EMAILS_ARRAY_KEY).getJSONObject(0).getString(RCU_EMAIL_ADDRESS);
    }

    public static String getCustomerShippingAddress1(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(SHIPPING_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE1_KEY);
            }
        }
        return "";
    }

    public static String getCustomerShippingAddress2(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(SHIPPING_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE2_KEY);
            }
        }
        return "";
    }

    public static String getCustomerShippingAddress3(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(SHIPPING_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE3_KEY);
            }
        }
        return "";
    }

    public static String getCustomerShippingAddress4(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(SHIPPING_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE4_KEY);
            }
        }
        return "";
    }

    public static String getCustomerShippingAddressPostalCode(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(SHIPPING_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(POSTAL_CODE_KEY);
            }
        }
        return "";
    }

    public static String getCustomerShippingAddressCountryCode(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(SHIPPING_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(COUNTRY_CODE_KEY);
            }
        }
        return "";
    }

    public static String getCustomerShippingAddressCity(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(SHIPPING_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(CITY_KEY);
            }
        }
        return "";
    }

    public static String getCustomerInvoiceAddress1(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(INVOICE_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE1_KEY);
            }
        }
        return "";
    }

    public static String getCustomerInvoiceAddress2(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(INVOICE_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE2_KEY);
            }
        }
        return "";
    }

    public static String getCustomerInvoiceAddress3(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(INVOICE_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE3_KEY);
            }
        }
        return "";
    }

    public static String getCustomerInvoiceAddress4(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(INVOICE_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE4_KEY);
            }
        }
        return "";
    }

    public static String getCustomerInvoiceAddressPostalCode(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(INVOICE_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(POSTAL_CODE_KEY);
            }
        }
        return "";
    }

    public static String getCustomerInvoiceAddressCountryCode(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(INVOICE_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(COUNTRY_CODE_KEY);
            }
        }
        return "";
    }

    public static String getCustomerInvoiceAddressCity(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(INVOICE_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(CITY_KEY);
            }
        }
        return "";
    }

    public static String getCustomerPrincipalAddress1(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(PRINCIPAL_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE1_KEY);
            }
        }
        return "";
    }

    public static String getCustomerPrincipalAddress2(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(PRINCIPAL_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE2_KEY);
            }
        }
        return "";
    }

    public static String getCustomerPrincipalAddress3(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(PRINCIPAL_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE3_KEY);
            }
        }
        return "";
    }

    public static String getCustomerPrincipalAddress4(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(PRINCIPAL_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(ADR_LINE4_KEY);
            }
        }
        return "";
    }

    public static String getCustomerPrincipalAddressPostalCode(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(PRINCIPAL_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(POSTAL_CODE_KEY);
            }
        }
        return "";
    }

    public static String getCustomerPrincipalAddressCountryCode(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(PRINCIPAL_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(COUNTRY_CODE_KEY);
            }
        }
        return "";
    }

    public static String getCustomerPrincipalAddressCity(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les addresses, 2 max
        JSONArray addresArray = parser.getJSONArray(ADDRESS_ARRAY_KEY);
        // Parcourt des addresses
        for (int i = 0; i < addresArray.length(); i++) {
            // Si c'est l'adresse de livraison
            if (addresArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(PRINCIPAL_ADDRESS_TYPE_VALUE)) {
                return addresArray.getJSONObject(i).getString(CITY_KEY);
            }
        }
        return "";
    }

    public static String getCustomerMobilePhone(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les phones
        JSONArray phoneArray = parser.getJSONArray(RCU_PHONE_ARRAY_KEY);
        // Parcourt des tél
        for (int i = 0; i < phoneArray.length(); i++) {
            if (phoneArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(MOBILE_PHONE_TYPE_VALUE)) {
                return phoneArray.getJSONObject(i).getString(RCU_PHONE_NUMBER_KEY).replaceAll("\\s+", "");
            }
        }
        return "";
    }

    public static String getCustomerFixPhone(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les phones
        JSONArray phoneArray = parser.getJSONArray(RCU_PHONE_ARRAY_KEY);
        // Parcours des tél
        for (int i = 0; i < phoneArray.length(); i++) {
            if (phoneArray.getJSONObject(i).getString(RCU_TYPE_KEY).equals(FIX_PHONE_TYPE_VALUE)) {
                // On retire les espaces avant d'envoyer le num
                return phoneArray.getJSONObject(i).getString(RCU_PHONE_NUMBER_KEY).replaceAll("\\s+", "");
            }
        }
        return "";
    }

    public static Boolean getEmailOptinValue(String json) {
        Boolean result = null;
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les optins
        JSONArray optinsArray = parser.getJSONArray(OPTIN_ARRAY_KEY);
        // On boucle sur les optins
        for (int i = 0; i < optinsArray.length(); i++) {
            if (optinsArray.getJSONObject(i).getString(OPTIN_ID_KEY).equals(MAIL_OPTIN_KEY)) {
                result = optinsArray.getJSONObject(i).getBoolean(OPTIN_VALUE);
            }
        }
        return result;
    }

    public static Boolean getSMSOptinValue(String json) {
        Boolean result = null;
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les optins
        JSONArray optinsArray = parser.getJSONArray(OPTIN_ARRAY_KEY);
        // On boucle sur les optins
        for (int i = 0; i < optinsArray.length(); i++) {
            if (optinsArray.getJSONObject(i).getString(OPTIN_ID_KEY).equals(SMS_OPTIN_KEY)) {
                result = optinsArray.getJSONObject(i).getBoolean(OPTIN_VALUE);
            }

        }
        return result;
    }

    public static String getContractualDocOptinValue(String json) {
        JSONObject parser = new JSONObject(json);
        // Tableau contenant les optins
        JSONArray optinsArray = parser.getJSONArray(OPTIN_ARRAY_KEY);
        // On boucle sur les optins
        for (int i = 0; i < optinsArray.length(); i++) {
            if (optinsArray.getJSONObject(i).getString(OPTIN_ID_KEY).equals(CONTRACTUAL_DOC_OPTIN_KEY)) {
                return optinsArray.getJSONObject(i).getString(OPTIN_VALUE);
            }
        }
        return "";
    }
}
