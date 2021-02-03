package Helpers.XPath;

public class XPathLineHelper {
    // Nom des variables contenues dans les ID
    private static final String XPATH_BODY_CONTROL_VAR = "VAR1";
    private static final String XPATH_RENDER_ORDER_LINE_VAR = "VAR2";
    // Position de la première variable dans l'id du nom produit
    private static final String PRODUCT_NAME_XPATH_BODY_CONTROL = "tbody_control";
    // Position de la seconde variable dans l'id du nom produit
    private static final String PRODUCT_NAME_XPATH_RENDER_ORDER_LINE = "renderOrderLine";
    // XPath du montant forfait
    private static final String FORFAIT_TOTAL_PRICE_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_listOrderLines_tbody_controlVAR1_renderOrderLineVAR2_forfaitNameTotal\"]";
    // XPath du montant total service
    private static final String SERVICE_GROSS_PRICE_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_listOrderLines_tbody_controlVAR1_renderOrderLineVAR2_gross\"]";
    // XPath quantité presta
    private static final String SERVICE_QUANTITY_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_listOrderLines_tbody_controlVAR1_renderOrderLineVAR2_quantity\"]";
    // XPath prix unitaire presta
    private static final String SERVICE_UNIT_PRICE ="//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_listOrderLines_tbody_controlVAR1_renderOrderLineVAR2_price\"]";


    /**
     * Extrait la variable "body_controlVAR" depuis l'id du nom forfait
     * @return
     */
    private static String getBodyControlXPathVarFromId(String productXPath) {
        String result = "";
        // On recupère la position du body dans l'id xpath
        int startIndex = productXPath.indexOf(PRODUCT_NAME_XPATH_BODY_CONTROL);
        // On recupère le premier char avec le body
        if (Character.isDigit(productXPath.charAt(startIndex + PRODUCT_NAME_XPATH_BODY_CONTROL.length()))) {
            result = String.valueOf(productXPath.charAt(startIndex + PRODUCT_NAME_XPATH_BODY_CONTROL.length()));
        }
        return result;
    }

    /**
     * Extrait la variable "renderOrderLineVAR" depuis l'id du nom forfait
     * @return
     */
    private static String getRenderOrderLineXPathVarFromId(String productXPath) {
        String result = "";
        // On recupère la position du render order line dans l'id xpath
        int startIndex = productXPath.indexOf(PRODUCT_NAME_XPATH_RENDER_ORDER_LINE);
        // On recupère le premier char avec le render order line
        if (Character.isDigit(productXPath.charAt(startIndex + PRODUCT_NAME_XPATH_RENDER_ORDER_LINE.length()))) {
            result = String.valueOf(productXPath.charAt(startIndex + PRODUCT_NAME_XPATH_RENDER_ORDER_LINE.length()));
        }
        return result;
    }

    /**
     * Retourne le XPath montant forfait
     * @param forfaitLabel
     * @return
     */
    public static String getForfaitTotalPriceXPath(String forfaitLabel) {
        return setVars(FORFAIT_TOTAL_PRICE_XPATH, getBodyControlXPathVarFromId(forfaitLabel), getRenderOrderLineXPathVarFromId(forfaitLabel));
    }

    /**
     * Retourne le XPath prix total du service
     * @param serviceLabel
     * @return
     */
    public static String getServiceGrossPriceXPath(String serviceLabel) {
        return setVars(SERVICE_GROSS_PRICE_XPATH, getBodyControlXPathVarFromId(serviceLabel), getRenderOrderLineXPathVarFromId(serviceLabel));
    }

    /**
     * Retourne le XPath prix total presta
     * @param serviceLabel
     * @return
     */
    public static String getServiceQuantityXPath(String serviceLabel) {
        return setVars(SERVICE_QUANTITY_XPATH, getBodyControlXPathVarFromId(serviceLabel), getRenderOrderLineXPathVarFromId(serviceLabel));
    }

    /**
     * Retourne le XPath quantité presta
     * @param serviceLabel
     * @return
     */
    public static String getServiceUnitPriceXPath(String serviceLabel) {
        return setVars(SERVICE_UNIT_PRICE, getBodyControlXPathVarFromId(serviceLabel), getRenderOrderLineXPathVarFromId(serviceLabel));
    }

    /**
     * Remplace les variables dans le XPath
     * @param XPath
     * @param var1
     * @param var2
     * @return
     */
    private static String setVars(String XPath, String var1, String var2) {
        String result = XPath;
        result = result.replace(XPATH_BODY_CONTROL_VAR, var1);
        result = result.replace(XPATH_RENDER_ORDER_LINE_VAR, var2);
        return result;
    }


}
