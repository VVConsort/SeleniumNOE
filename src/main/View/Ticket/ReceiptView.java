package View.Ticket;

import Helpers.Element.WebElementHelper;
import Helpers.XPath.XPathDiscountHelper;
import Helpers.XPath.XPathLineHelper;
import View.BaseView;
import View.Customer.CustomerDetailView;
import View.Customer.Search.CustomerSearchView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

    // Nom prénom du client associé au ticket
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_receiptHeader_receiptButtons_formElementBpbutton_coreElementContainer_bpbutton_name\"]")
    private WebElement linkedCustomer;

    // Btn d'édition client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_receiptHeader_receiptButtons_formElementBpbutton_coreElementContainer_bpbutton\"]")
    private WebElement customerDetailBtn;

    // Btn recherche client
    @FindBy(xpath ="//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftPanel_receiptview_receiptHeader_receiptButtons_separator\"]")
    private WebElement searchBtn;


    public ReceiptView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Retourne le composant contenant le nom et prénom du client
     * @return
     */
    public WebElement getLinkedCustomer() {
        return linkedCustomer;
    }

    /**
     * Retourne le montant total du ticket
     * @return
     */
    public WebElement getTotalAmountElem() {
        return totalToPay;
    }

    /**
     * Vérifie l'affichage d'une promotion
     * @param discountLabel
     * @return
     */
    public boolean isDiscountPresent(String discountLabel) {
        return WebElementHelper.getElementIdByText(discountLabel, driver) != null;
    }

    /**
     * Retourne le montant de la promotion
     * @param discountLabel
     * @return
     */
    public WebElement getDiscountLineAmountElem(String discountLabel) {
        WebElement result = null;
        // Récupération ID composant label
        String discountLabelId = WebElementHelper.getElementIdByText(OB_DISCOUNT_LABEL_PREFIX + discountLabel, driver);
        if (!discountLabelId.isEmpty()) {
            // Récupération de l'ID du montant promo
            String amountXPath = XPathDiscountHelper.getLineDiscountGrossAmountXPath(XPathDiscountHelper.getBodyControlXPathVarFromProductId(discountLabelId), XPathDiscountHelper.getRenderOrderLineXPathVarFromProductId(discountLabelId), XPathDiscountHelper.getEndVariableFromProductId(discountLabelId));
            result = WebElementHelper.getElement(driver, By.xpath(amountXPath));
        }
        // On retourne le montant de la promo
        return result;
    }

    /**
     * Retourne l'élément contenant le prix du forfait
     * @param forfaitLabel
     * @return
     */
    public WebElement getForfaitPriceElem(String forfaitLabel) {
        WebElement result = null;
        // Récupération ID composant label
        String forfaitLabelId = WebElementHelper.getElementIdByText(forfaitLabel, driver);
        if (!forfaitLabelId.isEmpty()) {
            // Récupération de l'ID du montant promo
            String priceXPath = XPathLineHelper.getForfaitTotalPriceXPath(forfaitLabelId);
            result = WebElementHelper.getElement(driver, By.xpath(priceXPath));
        }
        // On retourne le composant montant  forfait
        return result;
    }

    /**
     * Retourne l'élément contenant le prix total de la prestation
     * @param serviceLabel
     * @return
     */
    public WebElement getLineGrossPriceElem(String serviceLabel) {
        WebElement result = null;
        // Récupération de l'ID du label
        String serviceLabelId = WebElementHelper.getElementIdByText(serviceLabel, driver);
        if (!serviceLabelId.isEmpty()) {
            // Récupération de l'ID du prix total de la presta
            String grossPriceXPath = XPathLineHelper.getGrossPriceXPath(serviceLabelId);
            result = WebElementHelper.getElement(driver, By.xpath(grossPriceXPath));
        }
        // On retourne le composant prix total
        return result;
    }

    /**
     * Retourne l'élément contenant quantité de la prestation
     * @param serviceLabel
     * @return
     */
    public WebElement getLineQuantityElem(String serviceLabel) {
        WebElement result = null;
        // Récupération de l'ID du label
        String serviceLabelId = WebElementHelper.getElementIdByText(serviceLabel, driver);
        if (!serviceLabelId.isEmpty()) {
            // Récupération de l'ID du prix total de la presta
            String quantityXPath = XPathLineHelper.getQuantityXpath(serviceLabelId);
            result = WebElementHelper.getElement(driver, By.xpath(quantityXPath));
        }
        // On retourne le composant quantité
        return result;
    }

    /**
     * Retourne l'élément contenant quantité de la prestation
     * @param serviceLabel
     * @return
     */
    public WebElement getUnitPriceElem(String serviceLabel) {
        WebElement result = null;
        // Récupération de l'ID du label
        String serviceLabelId = WebElementHelper.getElementIdByText(serviceLabel, driver);
        if (!serviceLabelId.isEmpty()) {
            // Récupération de l'ID du prix total de la presta
            String grossPriceXPath = XPathLineHelper.getUnitPriceXPath(serviceLabelId);
            result = WebElementHelper.getElement(driver, By.xpath(grossPriceXPath));
        }
        // On retourne le composant prix unitaire
        return result;
    }

    /**
     * Click sur le bouton d'édition client et renvoi la vue détail client
      * @return
     */
    public CustomerDetailView openCustomerDetail() {
        super.click(customerDetailBtn, false);
        return new CustomerDetailView(driver);
    }

    /**
     * Appuie sur "Recherche"
     */
    public CustomerSearchView clickSearch(){
        super.click(searchBtn,false);
        // Retourne la vue de recherche client
        return new CustomerSearchView(driver);
    }

}
