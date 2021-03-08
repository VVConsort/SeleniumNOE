package View;

import Helpers.Element.WebElementHelper;
import View.Footer.Menu.Customer.CustomerSearchResultView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CustomerCreateView extends BaseView {

    // Message au moment de la création pour signaler les infos obligatoires manquantes
    private static final String MISSING_MANDATORY_FIELD_MESSAGE = "Renseigner les champs requis:";
    // XPath du composant saisie naissance
    private static final String CUST_BIRTH_PICKER_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_birthDay_coreElementContainer_birthDay\"]";

    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_birthDay_coreElementContainer_birthDay\"]")
    private WebElement custBirthDate;

    // Sélecteur catégorie client, personne/entreprise
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerCategory_coreElementContainer_customerCategory\"]")
    private WebElement custCategorySelector;

    // Sélecteur civilité client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_greeting_coreElementContainer_greeting\"]")
    private WebElement custTitleSelector;

    // Saisie prénom client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_firstName_coreElementContainer_firstName\"]")
    private WebElement custFirstNameInput;

    // Saisie nom client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_lastName_coreElementContainer_lastName\"]")
    private WebElement custLastNameInput;

    // Saisie langue client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerLanguage_coreElementContainer_customerLanguage\"]")
    private WebElement custLanguageSelector;

    // Saisie mobile client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerPhone_coreElementContainer_customerPhone\"]")
    private WebElement custMobilePhoneInput;

    // Saisie fixe client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_alternativePhone_coreElementContainer_alternativePhone\"]")
    private WebElement custPhoneInput;

    // Saisie email client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerEmail_coreElementContainer_customerEmail\"]")
    private WebElement custEmailInput;

    // PREFERENCE DE CONTACT

    //TODO Programme de fid

    // Documents contractuels
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_invoiceViaEmail_coreElementContainer_invoiceViaEmail\"]")
    private WebElement invoiceViaEmailCheck;

    // Consentement client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_isCustomerConsent_coreElementContainer_isCustomerConsent\"]")
    private WebElement custConsentCheck;

    // SMS
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_viasms_coreElementContainer_viasms\"]")
    private WebElement viaSmsCheck;

    // Email
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_viaemail_coreElementContainer_viaemail\"]")
    private WebElement viaEmailCheck;

    // Utiliser la meme adresse pour livraison et facturation
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_useSameAddrCheck_coreElementContainer_useSameAddrCheck\"]")
    private WebElement sameAddressCheck;

    // ADRESSE Facturation Même adresse livraison/facturation

    // Pays
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerCountry_coreElementContainer_customerCountry\"]")
    private WebElement custInvCountrySelector;

    // N et nom de la voie
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerInvLocName_coreElementContainer_customerInvLocName\"]")
    private WebElement custInvLocationNameInput;

    // Appartement etape porte couloir
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_norcrepInvLine2_coreElementContainer_norcrepInvLine2\"]")
    private WebElement custInvLine2Input;

    // Batiment résidence entrée
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_norcrepInvLine3_coreElementContainer_norcrepInvLine3\"]")
    private WebElement custInvLine3Input;

    // Lieu dit
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_norcrepInvLine4_coreElementContainer_norcrepInvLine4\"]")
    private WebElement custInvLine4Input;

    // Code postal
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerInvPostalCode_coreElementContainer_customerInvPostalCode\"]")
    private WebElement custInvPostalCodeInput;

    // Ville
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerInvCity_coreElementContainer_customerInvCity\"]")
    private WebElement custInvCityInput;

    // Addresse de livraison différente de celle de facturation :

    // Pays
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerShipCountry_coreElementContainer_customerShipCountry\"]")
    private WebElement custShipCountrySelector;

    // N et nom de la voie
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerLocName_coreElementContainer_customerLocName\"]")
    private WebElement custShipLocationNameInput;

    // Appartement etape porte couloir
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_norcrepShipLine2_coreElementContainer_norcrepShipLine2\"]")
    private WebElement custShipLine2Input;

    // Batiment résidence entrée
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_norcrepShipLine3_coreElementContainer_norcrepShipLine3\"]")
    private WebElement custShipLine3Input;

    // Lieu dit
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_norcrepShipLine4_coreElementContainer_norcrepShipLine4\"]")
    private WebElement custShipLine4Input;

    // Code postal
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerPostalCode_coreElementContainer_customerPostalCode\"]")
    private WebElement custShipPostalCodeInput;

    // Ville
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_customerCity_coreElementContainer_customerCity\"]")
    private WebElement custShipCityInput;

    // Bouton Annuler
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_footer_newCustomer_footer_cancelEdit\"]")
    private WebElement cancelBtn;

    // Bouton Sauvegarder
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_footer_newCustomer_footer_newcustomersave\"]")
    private WebElement saveCustomerBtn;

    public CustomerCreateView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Définie le type de client
     * @param custCategory
     */
    public void setCustCategory(String custCategory) {
        if (custCategory != null && !custCategory.isEmpty()) {
            Select categorySelect = new Select(custCategorySelector);
            categorySelect.selectByVisibleText(custCategory);
        }
    }

    /**
     * Définie la civilité
     * @param custTitle
     */
    public void setCustTitle(String custTitle) {
        if (custTitle != null && !custTitle.isEmpty()) {
            Select categorySelect = new Select(custTitleSelector);
            categorySelect.selectByVisibleText(custTitle);
        }
    }

    /**
     * Définie le prénom
     * @param firstName
     */
    public void setCustFirstName(String firstName) {
        sendKeys(custFirstNameInput, firstName);
    }

    /**
     * Définie le nom
     * @param lastName
     */
    public void setCustLastName(String lastName) {
        sendKeys(custLastNameInput, lastName);
    }

    /**
     * Définie la date de naissance
     * @param birthDateData
     */
    public void setCustBirthDate(String birthDateData) {
        // On utilise JavaScript pour changer la valeur du compo
        setAttribute(custBirthDate, "data-date", "12-02-2021");
        setAttribute(custBirthDate, "value", "2021-02-12");
        custBirthDate.submit();
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        /*js.executeScript("document.getElementById('//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_birthDay_coreElementContainer_birthDay\"]').setAttribute('data-date', '12-02-2021')");
        js.executeScript("document.getElementById('//*[@id=\"terminal_containerWindow_pointOfSale_customerCreateAndEdit_body_edit_createcustomers_impl_customerAttributes_line_birthDay_coreElementContainer_birthDay\"]').setAttribute('value', '2021-02-12')");*/
    }

    /**
     * Définie la langue
     * @param custLanguage
     */
    public void setCustLanguage(String custLanguage) {
        if (custLanguage != null && !custLanguage.isEmpty()) {
            Select languageSelect = new Select(custLanguageSelector);
            languageSelect.selectByVisibleText(custLanguage);
        }
    }

    /**
     * Définie le tel mobile
     * @param mobilePhone
     */
    public void setMobilePhone(String mobilePhone) {
        sendKeys(custMobilePhoneInput, mobilePhone);
    }

    /**
     * Définie le tel fixe
     * @param phone
     */
    public void setPhone(String phone) {
        sendKeys(custPhoneInput, phone);
    }

    /**
     * Définie l'email
     * @param email
     */
    public void setEmail(String email) {
        sendKeys(custEmailInput, email);
    }

    /**
     * Check "Documents contractuels"
     */
    public void checkInvoiceViaEMail() {
        super.click(invoiceViaEmailCheck, false);
    }

    /**
     * Check "Consentement client"
     */
    public void checkCustConsent() {
        super.click(custConsentCheck, false);
    }

    /**
     * Check "SMS"
     */
    public void checkViaSms() {
        super.click(viaSmsCheck, false);
    }

    /**
     * Check "Email"
     */
    public void checkViaEmail() {
        super.click(viaEmailCheck, false);
    }

    /**
     * Checkée par défaut, unCheck "Même adresse pour livraison et facturation",
     */
    public void uncheckSameAddress() {
        super.click(sameAddressCheck, false);
    }

    /**
     * Défini le pays
     * @param country
     */
    public void setInvoicingCountry(String country) {
        if (country != null && !country.isEmpty()) {
            Select languageSelect = new Select(custInvCountrySelector);
            languageSelect.selectByVisibleText(country);
        }
    }

    /**
     * Défini "N° et nom de la voie"
     * @param location
     */
    public void setInvoicingLocation(String location) {
        sendKeys(custInvLocationNameInput, location);
    }

    /**
     * Défini "Appartement, étage etc"
     */
    public void setInvoicingCustLine2(String line2) {
        sendKeys(custInvLine2Input, line2);
    }

    /**
     * Défini "Batiment, résident, entrée"
     */
    public void setInvoicingCustLine3(String line3) {
        sendKeys(custInvLine3Input, line3);
    }

    /**
     * Défini "Lieu dit"
     */
    public void setInvoicingCustLine4(String line4) {
        sendKeys(custInvLine4Input, line4);
    }

    /**
     * Défini "Code postal"
     * @param postalCode
     */
    public void setInvoicingPostalCode(String postalCode) {
        sendKeys(custInvPostalCodeInput, postalCode);
    }

    /**
     * Défini "Ville"
     */
    public void setInvoicingCity(String city) {
        sendKeys(custInvCityInput, city);
    }

    /**
     * Défini le pays
     * @param country
     */
    public void setShippingCountry(String country) {
        if (country != null && !country.isEmpty()) {
            Select languageSelect = new Select(custShipCountrySelector);
            languageSelect.selectByVisibleText(country);
        }
    }

    /**
     * Défini "N° et nom de la voie"
     * @param location
     */
    public void setShippingLocation(String location) {
        sendKeys(custShipLocationNameInput, location);
    }

    /**
     * Défini "Appartement, étage etc"
     */
    public void setShippingCustLine2(String line2) {
        sendKeys(custShipLine2Input, line2);
    }

    /**
     * Défini "Batiment, résident, entrée"
     */
    public void setShippingCustLine3(String line3) {
        sendKeys(custShipLine3Input, line3);
    }

    /**
     * Défini "Lieu dit"
     */
    public void setShippingCustLine4(String line4) {
        sendKeys(custShipLine4Input, line4);
    }

    /**
     * Défini "Code postal"
     * @param postalCode
     */
    public void setShippingPostalCode(String postalCode) {
        sendKeys(custShipPostalCodeInput, postalCode);
    }

    /**
     * Défini "Ville"
     */
    public void setShippingCity(String city) {
        sendKeys(custShipCityInput, city);
    }

    /**
     * Ferme la fenêtre et renvoie sur la vue de recherche
     */
    public CustomerSearchResultView clickCancel() {
        click(cancelBtn, false);
        return new CustomerSearchResultView(driver);
    }

    /**
     * Sauvegarde le client, renvoi true si création OK sans erreur
     * @return
     */
    public boolean clickSave() {
        super.click(saveCustomerBtn, false);
        // On attend eventuellement le message d'erreurs "Infos manquantes"
        return WebElementHelper.getElementFromText(driver, MISSING_MANDATORY_FIELD_MESSAGE, ELEMENT_MISSING_TIMEOUT, false) == null;
    }
}
