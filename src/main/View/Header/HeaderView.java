package View.Header;

import View.BaseView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class HeaderView extends BaseView {
    // Bouton scanner
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightToolbar_rightToolbar_toolbar_toolbarBtnScan\"]")
    WebElement scannerBtn;

    // Bouton Catalogue

    // Bouton Rechercher

    // Bouton Modifier

    public HeaderView(ChromeDriver webDriver) {
        init(webDriver, this);
    }

    /**
     * Clic sur le bouton Scanner
     */
    public ScannerView clickOnScanBtn() {
        super.click(scannerBtn, false);
        // Retourne un objet Scanner
        return new ScannerView(driver);
    }


}
