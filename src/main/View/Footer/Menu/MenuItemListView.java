package View.Footer.Menu;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuItemListView extends BaseView {

    // "Boutton" bon d'achat
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_mainMenu_menuHolder_menuScroller_menuCoupons\"]")
    private WebElement voucherBtn;

    public MenuItemListView(WebDriver driver) {
        init(driver,this);
    }

    /**
     * Click sur "Bon d'achat"
     */
    public VoucherCodeInputView clickOnVoucherBtn()
    {
        super.click(voucherBtn);
        // Retourne le composant de saisie de bon d'achat
        return new VoucherCodeInputView(driver);
    }
}
