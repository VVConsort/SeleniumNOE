package Step;

import Helpers.DataBase.OpenBravo.OpenBravoDBHelper;
import Helpers.Json.RCUJsonHelper;
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
        String rcuResponse = "";
        // Récupération en BDD OB du customerId
        cust.customerId = OpenBravoDBHelper.getCustomerID(cust.lastName, cust.firstName);
        // Si il n'y a pas de customer id, aucun appel à RCU n'a été fait
        if (cust.customerId != null && !cust.customerId.isEmpty()) {
            // On fait un GET avec ce clientID sur RCU
            rcuResponse = RCURestHelper.getCustomer(cust);
        }
        // Vérifie que la réponse correspond à celle attendue
        baseStep.isEquals(!rcuResponse.isEmpty());
    }

    @Step("Vérifie les données client insérées dans RCU")
    public static void checkRCUCustomerValues(Customer cust, BaseStepValue baseStep) throws IOException {
        boolean result = true;
        // Récupération du client sur RCU
        String response = RCURestHelper.getCustomer(cust);
        // Si la réponse est vide on arrete la
        if (response.isEmpty()) {
            baseStep.isEquals(false);
            return;
        }
        // Prénom
        if (!cust.firstName.equalsIgnoreCase(RCUJsonHelper.getCustomerFirstName(response))) {
            result = false;
            System.out.println("Prénom incorrect");
        }
        // Nom
        if (!cust.lastName.equalsIgnoreCase(RCUJsonHelper.getCustomerLastName(response))) {
            result = false;
            System.out.println("Nom incorrect");
        }
        // Type
        if (!cust.type.getRCUValue().equals(RCUJsonHelper.getCustomerType(response))) {
            result = false;
            System.out.println("Type incorrect");
        }
        // Langue
        if (!cust.language.getRCUValue().equals(RCUJsonHelper.getCustomerLanguage(response))) {
            result = false;
            System.out.println("Langue RCU incorrect");
        }
        // Phone fixe
        if (cust.phone != null && !cust.phone.isEmpty()) {
            if (!cust.phone.equals(RCUJsonHelper.getCustomerFixPhone(response))) {
                result = false;
                System.out.println("Tel fixe incorrect");
            }
        }
        // Phone mobile
        if (cust.mobilePhone != null && !cust.mobilePhone.isEmpty()) {
            if (!cust.mobilePhone.equals(RCUJsonHelper.getCustomerMobilePhone(response))) {
                result = false;
                System.out.println("Tel mobile incorrect");
            }
        }
        // Email
        if (cust.email != null && !cust.email.isEmpty()) {
            if (!cust.email.equalsIgnoreCase(RCUJsonHelper.getCustomerEmail(response))) {
                result = false;
                System.out.println("Email incorrect");
            }
        }
        // Optin SMS
        if (!cust.viaSms == RCUJsonHelper.getSMSOptinValue(response)) {
            result = false;
            System.out.println("Optin SMS incorrect");
        }
        // Optin Email
        if (!cust.viaEmail == RCUJsonHelper.getEmailOptinValue((response))) {
            result = false;
            System.out.println("Optin Email incorrect");
        }
        // Adresse livraison ligne 1
        if (!cust.shipLocationName.equals(RCUJsonHelper.getCustomerShippingAddress1(response))) {
            result = false;
            System.out.println("Adresse livraison ligne 1 incorrect");
        }
        // Adresse livraison ligne 2
        if (!cust.shipLine2.equals(RCUJsonHelper.getCustomerShippingAddress2(response))) {
            result = false;
            System.out.println("Adresse livraison ligne 2 incorrect");
        }
        // Adresse livraison ligne 3
        if (!cust.shipLine3.equals(RCUJsonHelper.getCustomerShippingAddress3(response))) {
            result = false;
            System.out.println("Adresse livraison ligne 3 incorrect");
        }
        // Adresse livraison ligne 4
        if (!cust.shipLine4.equals(RCUJsonHelper.getCustomerShippingAddress4(response))) {
            result = false;
            System.out.println("Adresse livraison ligne 4 incorrect");
        }
        // Code Postal livraison
        if (!cust.shipPostalCode.equals(RCUJsonHelper.getCustomerShippingAddressPostalCode(response))) {
            result = false;
            System.out.println("Code postal livraison incorrect");
        }
        // Ville livraison
        if (!cust.shipCity.equals(RCUJsonHelper.getCustomerShippingAddressCity(response))) {
            result = false;
            System.out.println("Ville livraison incorrect");
        }
        // Pays livraison
        if (!cust.shipCountry.getRCUValue().equals(RCUJsonHelper.getCustomerShippingAddressCountryCode(response))) {
            result = false;
            System.out.println("Pays livraison incorrect");
        }
        baseStep.isTrue(result);
    }

    @Step("Suppression logique du client {cust.customerId}")
    public static void archiveCustomer(Customer cust, BaseStepValue baseStep) throws IOException {
        baseStep.isEquals(RCURestHelper.archiveCustomer(cust));
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
        //FIXME : peux plus le set par JavaScript (javascript error: Unable to find owning document)
        //custCreateView.setCustBirthDate(customer.birthdate);
        // Langue
        if (customer.language != null) {
            custCreateView.setCustLanguage(customer.language.getOBValue());
        }
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
            if (customer.shipCountry != null) {
                custCreateView.setShippingCountry(customer.shipCountry.getOBValue());
            }
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
        if (customer.country != null) {
            custCreateView.setInvoicingCountry(customer.country.getOBValue());
        }
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
