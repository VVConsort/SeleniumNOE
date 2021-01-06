package Helpers.XPath;

public class XPathPaymentLineHelper {

    // Position de la première variable dans l'id du nom produit
    private static final String PAYMENT_XPATH_BODY_CONTROL = "tbody_control";
    // Position de la seconde variable dans l'id du nom produit
    private static final String PAYMENT_XPATH_RENDER_PAYMENT_LINE = "renderPaymentLine";
    // XPath nom ligne paiement
    private static final String PAYMENT_NAME_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_payments_tbody_controlVAR1_renderPaymentLineVAR2_name\"]";
    // XPath montant ligne paiement
    private static final String PAYMENT_AMOUNT_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_payments_tbody_controlVAR1_renderPaymentLineVAR2_amount\"]";
    // XPath boutton supprimer ligne
    private static final String REMOVE_PAYMENT_BTN_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_payments_tbody_controlVAR1_renderPaymentLineVAR2_removePayment\"]";
    private static final String XPATH_BODY_CONTROL_VAR = "VAR1";
    private static final String XPATH_RENDER_ORDER_LINE_VAR = "VAR2";

    /**
     * Renvoi l'XPath du boutton "suppression ligne paiement" à partir du libellé
     * @param paymentLabel
     * @return
     */
    public static String getRemovePaymentButtonAmountXPath(String paymentLabel) {
        return setVars(REMOVE_PAYMENT_BTN_XPATH, getBodyControlXPathVarFromPaymentLabel(paymentLabel), getRenderOrderLineXPathVarFromPaymentLabel(paymentLabel));
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
     * Extrait la variable "renderOrderLineVAR" depuis l'id du nom produit
     * @return
     */
    private static String getRenderOrderLineXPathVarFromPaymentLabel(String paymentLbl) {
        String result = "";
        // On recupère la position du render order line dans l'id xpath
        int startIndex = paymentLbl.indexOf(PAYMENT_XPATH_RENDER_PAYMENT_LINE);
        // On recupère le premier char avec le render order line
        if (Character.isDigit(paymentLbl.charAt(startIndex + PAYMENT_XPATH_RENDER_PAYMENT_LINE.length()))) {
            result = String.valueOf(paymentLbl.charAt(startIndex + PAYMENT_XPATH_RENDER_PAYMENT_LINE.length()));
        }
        return result;
    }

    /**
     * Extrait la variable "body_controlVAR" depuis l'id du nom produit
     * @return
     */
    private static String getBodyControlXPathVarFromPaymentLabel(String paymentLbl) {
        String result = "";
        // On recupère la position du body dans l'id xpath
        int startIndex = paymentLbl.indexOf(PAYMENT_XPATH_BODY_CONTROL);
        // On recupère le premier char avec le body
        if (Character.isDigit(paymentLbl.charAt(startIndex + PAYMENT_XPATH_BODY_CONTROL.length()))) {
            result = String.valueOf(paymentLbl.charAt(startIndex + PAYMENT_XPATH_BODY_CONTROL.length()));
        }
        return result;
    }
}
