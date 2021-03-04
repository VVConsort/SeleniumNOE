package Step;

import Helpers.DataBase.OpenBravo.OpenBravoDBHelper;
import Helpers.Rest.RCU.RCURestHelper;
import Serializable.Customer.Customer;
import Step.Value.BaseStepValue;
import View.CustomerCreateView;
import View.Footer.FooterView;
import View.Footer.Menu.Customer.CustomerSearchResultView;
import View.Footer.Menu.Customer.CustomerSearchView;
import io.qameta.allure.Step;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerStep extends BaseStep {

    @Step("Crée un client")
    public static void createCustomer(Customer customer, BaseStepValue step) throws InterruptedException {
        ///////////FIXME bug sur l'ouverture de la fenetre de recherche, une fois c'est la recherche une fois le résult qui s'affiche zobi
        CustomerSearchView custSearch = new FooterView(step.driver).clickOnMenuBtn().clickOnSearchCustomer();
        custSearch.clickCancel();
        new FooterView(step.driver).clickOnMenuBtn().clickOnSearchCustomer();
        CustomerSearchResultView searchResultView = new CustomerSearchResultView(step.driver);
        CustomerCreateView custCreateView = searchResultView.clickOnNewCustomer();
        ////////////////
        // Remplissage du formulaire à du client
        fillCustomerFields(customer, custCreateView);
        // Sauvegarde, clickSave() renvoi vrai si la création est OK
        boolean isCreated = (custCreateView.clickSave());
        // Si la création est en erreur
        if (!isCreated) {
            // On ferme la fenêtre de création
            CustomerSearchResultView searchView = custCreateView.clickCancel();
            // On ferme la fenêtre de recherche
            searchView.clickClose();
        }
        // Comparaison avec le résultat attendu
        step.isEquals(isCreated);
    }

    @Step("Vérifie : client présent en base {baseStep.expectedValue}")
    public static void checkCustomerPresenceOnRCU(Customer cust, BaseStepValue baseStep) throws IOException, SQLException {
        // Récupération en BDD OB du customerId
        cust.customerId = OpenBravoDBHelper.getCustomerID(cust.lastName, cust.firstName);
        // On fait un GET avec ce clientID sur RCU
        String response = RCURestHelper.getCustomer(cust);
        // Vérifie que la réponse n'est pas vide
        baseStep.isEquals(!response.isEmpty());
    }

    @Step("Vérifie les données client insérées dans RCU")
    public static void checkRCUCustomerValues(Customer cust,BaseStepValue baseStep)
    {

    }

    @Step("Suppression logique du client {cust.customerId}")
    public static void archiveCustomer(Customer cust, BaseStepValue baseStep) throws IOException {
        RCURestHelper.archiveCustomer(cust);
    }

    /**
     * Rempli les champs de la fiche de création à partir du client
     * @param customer
     * @param custCreateView
     */
    private static void fillCustomerFields(Customer customer, CustomerCreateView custCreateView) {
        // Type de client
        custCreateView.setCustCategory(customer.type.getOBValue());
        // Civilité
        custCreateView.setCustTitle(customer.title);
        // Prénom
        custCreateView.setCustFirstName(customer.firstName);
        // Nom
        custCreateView.setCustLastName(customer.lastName);
        // Date de naissance
        //FIXME : peux plus le set par JavaScript (javascript error: Unable to find owning document)s
        //custCreateView.setCustBirthDate(customer.birthdate);
        // Langue
        custCreateView.setCustLanguage(customer.language.getOBValue());
        // Num Mobile
        custCreateView.setMobilePhone(customer.mobilePhone);
        // Num fixe
        custCreateView.setPhone(customer.phone);
        // Email
        custCreateView.setEmail(customer.email);
        //Documents contractuels
        if (customer.invoiceViaEmail)
            custCreateView.checkInvoiceViaEMail();
        // Consentement client
        if (customer.consent)
            custCreateView.checkCustConsent();
        // SMS
        if (customer.viaSms)
            custCreateView.checkViaSms();
        // EMail
        if (customer.viaEmail)
            custCreateView.checkViaEmail();
        // Adresse de facturation/livraison diffs
        if (!customer.sameAddress) {
            // On uncheck sur OB
            custCreateView.uncheckSameAddress();
            // Adresse de livraison
            // Pays
            custCreateView.setShippingCountry(customer.shipCountry.getOBValue());
            // N et nom voie
            custCreateView.setShippingLocation(customer.shipLocationName);
            // Appartement
            custCreateView.setShippingCustLine2(customer.shipLine2);
            // Batiment
            custCreateView.setShippingCustLine3(customer.shipLine3);
            // Lieu dit
            custCreateView.setShippingCustLine4(customer.shipLine4);
            // Code postal
            custCreateView.setShippingPostalCode(customer.shipPostalCode);
            // Ville
            custCreateView.setShippingCity(customer.shipCity);
        }
        // Adresse facturation
        // Pays
        custCreateView.setInvoicingCountry(customer.country.getOBValue());
        // N et nom voie
        custCreateView.setInvoicingLocation(customer.locationName);
        // Appartement
        custCreateView.setInvoicingCustLine2(customer.line2);
        // Batiment
        custCreateView.setInvoicingCustLine3(customer.line3);
        // Lieu dit
        custCreateView.setInvoicingCustLine4(customer.line4);
        // Code postal
        custCreateView.setInvoicingPostalCode(customer.postalCode);
        // Ville
        custCreateView.setInvoicingCity(customer.city);
    }
}
