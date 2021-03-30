package View.Customer.Search;

import View.BaseView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CustomerSearchView extends BaseView {

    // Bouton Annuler
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalAdvancedFilterBP_footer_modalDialogButton\"]")
    private WebElement cancelBtn;

    // Nom
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalAdvancedFilterBP_body_filters_formElementnorcrepLastName_coreElementContainer_inputnorcrepLastName\"]")
    private WebElement lastNameInput;

    // Prénom
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalAdvancedFilterBP_body_filters_formElementnorcrepFirstName_coreElementContainer_inputnorcrepFirstName\"]")
    private WebElement firstNameInput;

    // Appliquer Filtre
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalAdvancedFilterBP_footer_btnApply\"]")
    private WebElement applyFilterBtn;

    public CustomerSearchView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Appuie sur Annuler
     */
    public void clickCancel() {
        click(cancelBtn, false);
    }

    /**
     * Saisie le nom du client recherché
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.sendKeys(lastNameInput, lastName);
    }

    /**
     * Saisie le prénom du client recherché
     */
    public void setFirstName(String firstName) {
        this.sendKeys(firstNameInput, firstName);
    }

    /**
     * Appuie sur "Applique Filtres"
     */
    public CustomerSearchResultView clickApplyFilter() {
        super.click(applyFilterBtn, false);
        // Retourne la vue des résultats de la recherche
        return new CustomerSearchResultView(driver);
    }
}
