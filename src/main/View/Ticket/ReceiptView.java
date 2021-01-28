package View.Ticket;

import Helpers.Element.WebElementHelper;
import Helpers.XPath.XPathDiscountLineHelper;
import View.BaseView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Ticket
 */
public class ReceiptView extends BaseView {

    // Préfixe du libellé des promotions sur OB
    private static final String OB_DISCOUNT_LABEL_PREFIX = "-- ";
    // Montant total du ticket
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_orderview_totalReceiptLine_totalgross\"]")
    private WebElement totalToPay;

    public ReceiptView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Retourne le montant total du ticket
     * @return
     */
    public String getTotalAmount() {
        return totalToPay.getText();
    }

    /**
     * Vérifie l'affichage d'une promotion
     * @param discountLabel
     * @return
     */
    public boolean isDiscountPresent(String discountLabel) {
        return getDiscountLabelELemIdByText(discountLabel) != null;
    }

    /**
     * Retourne le montant de la promotion
     * @param discountLabel
     * @return
     */
    public String getDiscountLineAmount(String discountLabel) {
        String result = "";
        // Récupération de l'ID du label
        String discountLabelId = getDiscountLabelELemIdByText(discountLabel);
        if (!discountLabelId.isEmpty()) {
            // Récupération de l'ID du montant promo
            String amountXPath = XPathDiscountLineHelper.getLineDiscountGrossAmountXPath(XPathDiscountLineHelper.getBodyControlXPathVarFromProductId(discountLabelId), XPathDiscountLineHelper.getRenderOrderLineXPathVarFromProductId(discountLabelId), XPathDiscountLineHelper.getEndVariableFromProductId(discountLabelId));
            result = Helpers.Element.WebElementHelper.getTextFromElement(driver, By.xpath(amountXPath));
        }
        // On retourne le montant de la promo
        return result;
    }

    /**
     * Retourne l'ID du libellé ligne de la promo
     * @param label
     * @return
     */
    private String getDiscountLabelELemIdByText(String label) {
        String result = "";
        // Recherche l'élement 'label' à partir du texte
        WebElement discoutNameElem = WebElementHelper.getElementFromText(driver, OB_DISCOUNT_LABEL_PREFIX + label, 5, false);
        // Blindage
        if (discoutNameElem != null) {
            // Affectation de l'id de l'élement
            result = discoutNameElem.getAttribute("id");
        }
        return result;
    }
}
