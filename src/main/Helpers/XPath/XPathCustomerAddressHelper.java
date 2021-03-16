package Helpers.XPath;

public class XPathCustomerAddressHelper {

    // Nom des variables contenues dans les ID
    private static final String XPATH_BODY_CONTROL_VAR = "VAR1";
    // Position de la première variable dans l'id du nom produit
    private static final String ADDRESS_NAME_BODY_CONTROL = "tbody_control";

    // XPath nom addresse
    private static final String XPATH_ADDRESS_NAME ="//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomeraddress_body_listBpsLoc_bpsloclistitemprinter_tbody_control2_listBpsLocLine_identifier\"]";

    // XPath btn contextuel adresse client
    private static final String XPATH_CUSTOMER_ADDRESS_CONTEXT_BTN ="//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomeraddress_body_listBpsLoc_bpsloclistitemprinter_tbody_controlVAR1_listBpsLocLine_btnContextMenu_listButton\"]";
    /**
     * Retourne l'XPath du montant de la ligne de paiement
     * @param paymentLabel
     * @return
     */

    public static String getAddressContextBtnXPath(String paymentLabel) {
        return setVars(XPATH_CUSTOMER_ADDRESS_CONTEXT_BTN, getBodyControlXPathVarFromAddressName(paymentLabel));
    }
    /**
     * Remplace les variables dans le XPath
     * @param XPath
     * @param var1
     * @param var2
     * @return
     */
    private static String setVars(String XPath, String var1) {
        String result = XPath;
        result = result.replace(XPATH_BODY_CONTROL_VAR, var1);
        return result;
    }

    /**
     * Extrait la variable "renderOrderLineVAR" depuis l'id du nom forfait
     * @return
     */
    private static String getBodyControlXPathVarFromAddressName(String addressNameXPath) {
        String result = "";
        // On recupère la position du render order line dans l'id xpath
        int startIndex = addressNameXPath.indexOf(ADDRESS_NAME_BODY_CONTROL);
        // On recupère le premier char avec le render order line
        if (Character.isDigit(addressNameXPath.charAt(startIndex + ADDRESS_NAME_BODY_CONTROL.length()))) {
            result = String.valueOf(addressNameXPath.charAt(startIndex + ADDRESS_NAME_BODY_CONTROL.length()));
        }
        return result;
    }
}
