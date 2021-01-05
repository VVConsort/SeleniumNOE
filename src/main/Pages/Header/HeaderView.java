package Pages.Header;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderView extends BasePage {
    // Bouton scanner
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightToolbar_rightToolbar_toolbar_toolbarBtnScan\"]")
    WebElement scannerBtn;

    // Bouton Catalogue

    // Bouton Rechercher

    // Bouton Modifier

    public HeaderView(WebDriver webDriver) {
        init(webDriver, this);
    }

    /**
     * Clic sur le bouton Scanner
     */
    public ScannerView clickOnScanBtn() {
        click(scannerBtn);
        // Retourne un objet Scanner
        return new ScannerView(driver);
    }


}
