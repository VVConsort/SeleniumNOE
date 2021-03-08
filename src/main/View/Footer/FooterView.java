package View.Footer;

import Helpers.Element.WebElementHelper;
import View.BaseView;
import View.Footer.Menu.MenuItemListView;
import View.Ticket.Payment.PaymentPanelView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class FooterView extends BaseView {

    // XPath du bouton "Confirmer suppression"
    private static final String CONFIRM_DELETE_BTN_XPATH = "//button[@id=\"terminal_containerWindow_pointOfSale_modalConfirmReceiptDelete_footer_btnModalApplyDelete\"]";
    // Menu
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_mainMenu_menuHolder_mainMenuButton\"]")
    private WebElement menuBtn;
    // Bouton "Payer"
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_btnTotalToPay\"]")
    private WebElement totalToPayBtn;
    // A payer
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_btnTotalToPay_icon_totalPrinter\"]")
    private WebElement totalToPay;
    // Etat
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_btnTotalToPay_label_labelText_0\"]")
    private WebElement orderStateLbl;
    // Vider ticket
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_buttonDelete\"]")
    private WebElement deleteTicketBtn;

    //
    public FooterView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Vide le ticket en cours
     */
    public void clickOnDeleteTicketBtn() {
        super.click(deleteTicketBtn, false);
    }

    /**
     * Click sur le bouton 'confirmer suppression ticket'
     */
    public void clickOnConfirmDeleteBtn() {
        // Click sur le btn si il est présent
        if (findAndClickElement(CONFIRM_DELETE_BTN_XPATH, false)) {
            // On attend sa disparation
            WebElementHelper.waitUntilElementIsNotPresent(driver, ELEMENT_MISSING_TIMEOUT, By.xpath(CONFIRM_DELETE_BTN_XPATH), true);
        }
    }

    /**
     * Click sur le bouton 'A payer' et retourne la panneau des paiements
     */
    public PaymentPanelView clickOnTotalToPayBtn() {
        // Click sur A payer
        super.click(totalToPayBtn, false);
        return new PaymentPanelView(driver);
    }

    /**
     * Click sur le bouton 'Menu'
     */
    public MenuItemListView clickOnMenuBtn() {
        super.click(menuBtn, false);
        // Renvoi la liste des éléments du menu
        return new MenuItemListView(driver);
    }

    /**
     * Retourne l'état de la commande
     * @return
     */
    public WebElement getOrderStateElem() {
        return orderStateLbl;
    }

    /**
     * Retourne le montant à payer
     * @return
     */
    public WebElement getTotalToPayElem() {
        return totalToPay;
    }
}
