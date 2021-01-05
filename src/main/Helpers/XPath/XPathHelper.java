package Helpers.XPath;

public class XPathHelper {
    // Nom des variables contenues dans les ID
    private static final String XPATH_BODY_CONTROL_VAR = "VAR1";
    private static final String XPATH_RENDER_ORDER_LINE_VAR = "VAR2";
    private static final String XPATH_END_VAR = "VAR3";
    // Position de la première variable dans l'id du nom produit
    private static final String PRODUCT_NAME_XPATH_BODY_CONTROL = "tbody_control";
    // Position de la seconde variable dans l'id du nom produit
    private static final String PRODUCT_NAME_XPATH_RENDER_ORDER_LINE = "renderOrderLine";
    // Pour les promotions la variable de fin du montant est décalé de 1 comparée à celle du libellé
    private static final int LINE_DISCOUNT_GROSS_AMOUNT_END_VAR_OFFSET = 1;
    // XPath dynamique quantité ligne
    private static final String LINE_QUANTITY_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_listOrderLines_tbody_controlVAR1_renderOrderLineVAR2_quantity\"]";
    // XPath dynamique prix unitaire ligne
    private static final String LINE_UNIT_PRICE_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_listOrderLines_tbody_controlVAR1_renderOrderLineVAR2_price\"]";
    // XPath dynamique total de la ligne
    private static final String LINE_GROSS_AMOUNT_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_listOrderLines_tbody_controlVAR1_renderOrderLineVAR2_gross\"]";
    // XPath dynamique promotion
    private static final String LINE_DISCOUNT_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_listOrderLines_tbody_controlVAR1_renderOrderLineVAR2_controlVAR3\"]";

    /**
     * Extrait la variable "body_controlVAR" depuis l'id du nom produit
     * @return
     */
    public static String getBodyControlXPathVarFromProductId(String productXPath) {
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
     * Extrait la variable "renderOrderLineVAR" depuis l'id du nom produit
     * @return
     */
    public static String getRenderOrderLineXPathVarFromProductId(String productXPath) {
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
     * Extrait la dernière variable du XPath du nom produit
     * @return
     */
    public static String getEndVariableFromProductId(String productXPath) {
        String result = "";
        // Si le dernier char est un digit
        if (Character.isDigit(productXPath.charAt(productXPath.length() - 1))) {
            // Alors on recupère la valeur de la variable
            result = String.valueOf(productXPath.charAt(productXPath.length() - 1));
        }
        return result;
    }

    /**
     * Complete et renvoie le XPath de montant remise
     * @param var1
     * @param var2
     * @param var3
     * @return
     */
    public static String getLineDiscountGrossAmountXPath(String var1, String var2, String var3) {
        // On ajoute l'offset à la variable de fin
        int numVar3 = Integer.parseInt(var3) + LINE_DISCOUNT_GROSS_AMOUNT_END_VAR_OFFSET;
        return setVars(LINE_DISCOUNT_XPATH, var1, var2, String.valueOf(numVar3));
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

    /**
     * Remplace les variables dans le XPath
     * @param XPath
     * @param var1
     * @param var2
     * @param var3
     * @return
     */
    private static String setVars(String XPath, String var1, String var2, String var3) {
        // On remplace les deux premières variables
        String result = setVars(XPath, var1, var2);
        // La dernière
        result = setEndVar(result, var3);
        return result;
    }

    /**
     * Remplace la variable 3 dans le XPath
     * @param XPath
     * @param var3
     * @return
     */
    private static String setEndVar(String XPath, String var3) {
        String result = XPath;
        result = result.replace(XPATH_END_VAR, var3);
        return result;
    }
}
