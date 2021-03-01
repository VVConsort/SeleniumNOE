package View.Footer.Menu.Customer;

import View.BaseView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CustomerSearchView extends BaseView {

    // Bouton Annuler
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalAdvancedFilterBP_footer_modalDialogButton\"]")
    private WebElement cancelBtn;

    public CustomerSearchView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Appuie sur Annuler
     */
    public void clickCancel() {
        click(cancelBtn);
    }
}
