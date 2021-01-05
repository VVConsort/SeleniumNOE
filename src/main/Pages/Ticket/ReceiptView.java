package Pages.Ticket;

import Helpers.Element.WebElementHelper;
import Helpers.XPath.XPathHelper;
import Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Ticket
 */
public class ReceiptView extends BasePage {

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
        return getDiscountLabelELemByText(discountLabel) != null;
    }

    /**
     * Retourne le montant de la promotion
     * @param discountLabel
     * @return
     */
    public String getDiscountLineAmount(String discountLabel) {
        // On cherche le label de la promotion
        WebElement discountLabelElem = getDiscountLabelELemByText(discountLabel);
        // Si l'élément n'existe pas
        if (discountLabelElem == null) {
            return "";
        }
        // Récupération de l'ID du label
        String discountLabelId = discountLabelElem.getAttribute("id");
        // Récupération de l'ID du montant promo
        String amountXPath = XPathHelper.getLineDiscountGrossAmountXPath(XPathHelper.getBodyControlXPathVarFromProductId(discountLabelId), XPathHelper.getRenderOrderLineXPathVarFromProductId(discountLabelId), XPathHelper.getEndVariableFromProductId(discountLabelId));
        // On retourne le montant de la promo
        return WebElementHelper.getTextFromElement(driver, By.xpath(amountXPath));
    }

    /**
     * Retourne l'élement libellé ligne de la promo
     * @param label
     * @return
     */
    private WebElement getDiscountLabelELemByText(String label) {
        // Recherche l'élement 'label' à partir du texte
        return WebElementHelper.getElement(driver, By.xpath("*//div[text()='" + OB_DISCOUNT_LABEL_PREFIX + label + "']"));

    }
}
