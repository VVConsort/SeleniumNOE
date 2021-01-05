package Pages.Footer;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterView extends BasePage {

    // Menu
    // A payer
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_btnTotalToPay\"]")
    private WebElement totalToPayBtn;
    // Nouveau ticket
    // Vider ticket
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_buttonDelete\"]")
    private WebElement deleteTicketBtn;

    // Bouton confirmation suppression ticket
    @FindBy(xpath="//button[@id=\"terminal_containerWindow_pointOfSale_modalConfirmReceiptDelete_footer_btnModalApplyDelete\"]")
    private WebElement confirmDeleteBtn;

    public FooterView(WebDriver driver) {
        init(driver,this);
    }

    /**
     * Vide le ticket en cours
     */
    public void clickOnDeleteTicketBtn()
    {
        super.click(deleteTicketBtn);
    }

    /**
     * Click sur le bouton 'confirmer suppression ticket'
     */
    public void clickOnConfirmDeleteBtn()
    {
        super.click(confirmDeleteBtn);
    }

    /**
     * Click sur le bouton 'A payer'
     */
    public void clickOnTotalToPayBtn()
    {
        super.click(totalToPayBtn);
    }
}
