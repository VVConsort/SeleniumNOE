package Step;

import Helpers.DataBase.OpenBravo.OpenBravoDBHelper;
import Helpers.Element.WebElementHelper;
import Helpers.Json.RCUJsonHelper;
import Helpers.Rest.RCU.RCURestHelper;
import Serializable.Customer.Customer;
import Step.Value.BaseStepValue;
import View.Customer.Edit.CustomerEditAddressView;
import View.Customer.Edit.CustomerEditView;
import View.Customer.Search.CustomerSearchResultView;
import View.Customer.Search.CustomerSearchView;
import View.Footer.FooterView;
import View.Ticket.ReceiptView;
import io.qameta.allure.Step;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerStep extends BaseStep {

    @Step("Création d'un client sur OpenBravo")
    public static void createCustomer(Customer customer, BaseStepValue step) throws InterruptedException {
        ///////////FIXME bug sur l'ouverture de la fenetre de recherche, une fois c'est la recherche qui s'affiche une fois le résult  zobi
        CustomerSearchView custSearch = new FooterView(step.driver).clickOnMenuBtn().clickOnSearchCustomer();
        custSearch.clickCancel();
        new FooterView(step.driver).clickOnMenuBtn().clickOnSearchCustomer();
        CustomerSearchResultView searchResultView = new CustomerSearchResultView(step.driver);
        CustomerEditView customerEditView = searchResultView.clickOnNewCustomer();
        ////////////////
        // Saisie les infos clients
        fillCustomerInfos(customer, customerEditView);
        // Saisie les infos d'adresse
        fillCustomerAddress(customer, customerEditView);
        // Sauvegarde, clickSave() renvoi vrai si la création est OK
        boolean isCreated = (customerEditView.clickSave());
        // Si la création est en erreur
        if (!isCreated) {
            // On ferme la fenêtre de création
            CustomerSearchResultView searchView = customerEditView.clickCancel();
            // On ferme la fenêtre de recherche
            searchView.clickClose();
        }
        // Comparaison avec le résultat attendu
        step.assertionMessage = "Création client " + customer.firstName + " " + customer.lastName + " sur OB : ";
        step.isEquals(isCreated);
    }

    @Step("Vérifie : client {cust.firstName} {cust.lastName} présent en base {baseStep.expectedValue}")
    public static void checkCustomerPresenceOnRCU(Customer cust, BaseStepValue baseStep) throws IOException, SQLException {
        String rcuResponse = "";
        // Récupération en BDD OB du customerId si nécessaire
        setCustomerId(cust);
        // Si il n'y a pas de customer id, aucun appel à RCU n'a été fait
        if (cust.customerId != null && !cust.customerId.isEmpty()) {
            // On fait un GET avec ce clientID sur RCU
            rcuResponse = RCURestHelper.getCustomer(cust);
        }
        baseStep.assertionMessage = " Présence client " + cust.firstName + " " + cust.lastName + " sur RCU : ";
        // Vérifie que la réponse correspond à celle attendue
        baseStep.isEquals(!rcuResponse.isEmpty());
    }

    @Step("Vérifie les données du client {cust.firstName} {cust.lastName} {cust.customerId} insérées dans RCU")
    public static void checkRCUCustomerValues(Customer cust, BaseStepValue baseStep) throws IOException, SQLException {
        boolean result = true;
        setCustomerId(cust);
        // Récupération du client sur RCU
        String response = RCURestHelper.getCustomer(cust);
        baseStep.assertionMessage = "Client " + cust.firstName + " " + cust.lastName + " données complètes sur RCU : ";
        // Si la réponse est vide on arrete la
        if (response.isEmpty()) {
            baseStep.isEquals(false);
            return;
        }
        // Prénom
        if (cust.firstName != null && !cust.firstName.equalsIgnoreCase(RCUJsonHelper.getCustomerFirstName(response))) {
            result = false;
            baseStep.assertionMessage = baseStep.assertionMessage.concat("Prénom incorrect, ");
        }
        // Nom
        if (cust.lastName != null && !cust.lastName.equalsIgnoreCase(RCUJsonHelper.getCustomerLastName(response))) {
            result = false;
            baseStep.assertionMessage = baseStep.assertionMessage.concat("Nom incorrect, ");
        }
        // Type
        if (!cust.type.getRCUValue().equalsIgnoreCase(RCUJsonHelper.getCustomerType(response))) {
            result = false;
            baseStep.assertionMessage = baseStep.assertionMessage.concat("Type incorrect, ");
        }
        // Titre
        if (!cust.title.getRCUValue().equals(RCUJsonHelper.getCustomerTitle(response))) {
            result = false;
            baseStep.assertionMessage = baseStep.assertionMessage.concat("Titre incorrect, ");
        }
        // Langue
        if (!cust.language.getRCUValue().equalsIgnoreCase(RCUJsonHelper.getCustomerLanguage(response))) {
            result = false;
            baseStep.assertionMessage = baseStep.assertionMessage.concat("Langue incorrect, ");
        }
        // Phone fixe
        if (cust.phone != null && !cust.phone.isEmpty()) {
            if (!cust.phone.equals(RCUJsonHelper.getCustomerFixPhone(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Téléphone fixe incorrect, ");
            }
        }
        // Phone mobile
        if (cust.mobilePhone != null && !cust.mobilePhone.isEmpty()) {
            if (!cust.mobilePhone.equals(RCUJsonHelper.getCustomerMobilePhone(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Téléphone mobile incorrect, ");
            }
        }
        // Email
        if (cust.email != null && !cust.email.isEmpty()) {
            if (!cust.email.equalsIgnoreCase(RCUJsonHelper.getCustomerEmail(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Email incorrect, ");
            }
        }
        // Optin SMS
        if (!Boolean.FALSE.equals(cust.viaSms) && cust.viaSms != RCUJsonHelper.getSMSOptinValue(response)) {
            result = false;
            baseStep.assertionMessage = baseStep.assertionMessage.concat("Optin SMS incorrect, ");
        }
        // Optin Email
        if (!Boolean.FALSE.equals(cust.viaEmail) && cust.viaEmail != RCUJsonHelper.getEmailOptinValue((response))) {
            result = false;
            baseStep.assertionMessage = baseStep.assertionMessage.concat("Optin email incorrect, ");
        }
        // Si l'adresse de livraison/facturation diff
        if (Boolean.TRUE.equals(cust.sameAddress)) {
            // Adresse livraison ligne 1
            if (cust.shipLocationName != null && !cust.shipLocationName.equalsIgnoreCase(RCUJsonHelper.getCustomerShippingAddress1(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse livraison ligne 1 incorrect, ");
            }
            // Adresse livraison ligne 2
            if (cust.shipLine2 != null && !cust.shipLine2.equalsIgnoreCase(RCUJsonHelper.getCustomerShippingAddress2(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse livraison ligne 2 incorrect, ");
            }
            // Adresse livraison ligne 3
            if (cust.shipLine3 != null && !cust.shipLine3.equalsIgnoreCase(RCUJsonHelper.getCustomerShippingAddress3(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse livraison ligne 3 incorrect, ");
            }
            // Adresse livraison ligne 4
            if (cust.shipLine4 != null && !cust.shipLine4.equalsIgnoreCase(RCUJsonHelper.getCustomerShippingAddress4(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse livraison ligne 4 incorrect, ");
            }
            // Code Postal livraison
            if (cust.shipPostalCode != null && !cust.shipPostalCode.equalsIgnoreCase(RCUJsonHelper.getCustomerShippingAddressPostalCode(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Code postal livraison incorrect, ");
            }
            // Ville livraison
            if (cust.shipCity != null && !cust.shipCity.equalsIgnoreCase(RCUJsonHelper.getCustomerShippingAddressCity(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Ville livraison incorrect, ");
            }
            // Pays livraison
            if (cust.shipCountry != null && !cust.shipCountry.getRCUValue().equalsIgnoreCase(RCUJsonHelper.getCustomerShippingAddressCountryCode(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Pays livraison incorrect, ");
            }
            // Addresse Ligne 1 invoice
            if (cust.locationName != null && !cust.locationName.equalsIgnoreCase(RCUJsonHelper.getCustomerInvoiceAddress1(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse facturation ligne 1 incorrect, ");
            }
            // Adresse invoice ligne 2
            if (cust.line2 != null && !cust.line2.equalsIgnoreCase(RCUJsonHelper.getCustomerInvoiceAddress2(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse facturation ligne 2 incorrect, ");
            }
            // Adresse invoice ligne 3
            if (cust.line3 != null && !cust.line3.equalsIgnoreCase(RCUJsonHelper.getCustomerInvoiceAddress3(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse facturation ligne 3 incorrect, ");
            }
            // Adresse invoice ligne 4
            if (cust.line4 != null && !cust.line4.equalsIgnoreCase(RCUJsonHelper.getCustomerInvoiceAddress4(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse facturation ligne 4 incorrect, ");
            }
            // Code Postal invoice
            if (cust.postalCode != null && !cust.postalCode.equalsIgnoreCase(RCUJsonHelper.getCustomerInvoiceAddressPostalCode(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Code postal facturation incorrect, ");
            }
            // Ville invoice
            if (cust.city != null && !cust.city.equalsIgnoreCase(RCUJsonHelper.getCustomerInvoiceAddressCity(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Ville facturation incorrect, ");
            }
            // Pays invoice
            if (cust.country != null && !cust.country.getRCUValue().equalsIgnoreCase(RCUJsonHelper.getCustomerInvoiceAddressCountryCode(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Pays facturation incorrect, ");
            }

        }
        // Même addresse facturation/livraison
        else {
            // Adresse principal ligne 1
            if (cust.locationName != null && !cust.locationName.equals(RCUJsonHelper.getCustomerPrincipalAddress1(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse principale ligne 1 incorrect, ");
            }
            // Adresse principal ligne 2
            if (cust.line2 != null && !cust.line2.equalsIgnoreCase(RCUJsonHelper.getCustomerPrincipalAddress2(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse principale ligne 2 incorrect, ");
            }
            // AAdresse principal ligne 3
            if (cust.line3 != null && !cust.line3.equalsIgnoreCase(RCUJsonHelper.getCustomerPrincipalAddress3(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse principale ligne 3 incorrect, ");
            }
            // Adresse principal ligne 4
            if (cust.line4 != null && !cust.line4.equalsIgnoreCase(RCUJsonHelper.getCustomerPrincipalAddress4(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Adresse principale ligne 4 incorrect, ");
            }
            // Adresse principal Code Postal
            if (cust.postalCode != null && !cust.postalCode.equalsIgnoreCase(RCUJsonHelper.getCustomerPrincipalAddressPostalCode(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Code postal principal incorrect, ");
            }
            // Adresse principal Ville
            if (cust.city != null && !cust.city.equalsIgnoreCase(RCUJsonHelper.getCustomerPrincipalAddressCity(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Ville principale incorrect, ");
            }
            // Adresse principal Pays
            if (cust.country != null && !cust.country.getRCUValue().equalsIgnoreCase(RCUJsonHelper.getCustomerPrincipalAddressCountryCode(response))) {
                result = false;
                baseStep.assertionMessage = baseStep.assertionMessage.concat("Pays principal incorrect, ");
            }
        }
        baseStep.isTrue(result);
    }

    @Step("Suppression logique du client {cust.firstName} + {cust.lastName} {cust.customerId}")
    public static void archiveCustomer(Customer cust, BaseStepValue baseStep) throws IOException, SQLException {
        // Si le client n'a pas d'id on le récupère dans la DB OB
        setCustomerId(cust);
        // Archiave sur RCU
        baseStep.assertionMessage = " Client " + cust.firstName + " " + cust.lastName + " archivé : ";
        baseStep.isEquals(RCURestHelper.archiveCustomer(cust));
    }

    /**
     * Saisies les infos sur le client
     * @param customer
     * @param custCreateView
     */
    private static void fillCustomerInfos(Customer customer, CustomerEditView custCreateView) {
        // Type de client
        custCreateView.setCustCategory(customer.type.getOBValue());
        // Civilité
        custCreateView.setCustTitle(customer.title.getOBValue());
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
    }

    /**
     * Saisie les champs addresse sur OB à partir des infos clients
     * @param customer
     * @param custCreateView
     */
    private static void fillCustomerAddress(Customer customer, CustomerEditView custCreateView) {

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

    /**
     * Remplie les champs d'édition d'adresse à partir de l'adresse de livraison
     * @param customer
     * @param editView
     */
    public static void fillCustomerShippingAddressEdit(Customer customer, CustomerEditAddressView editView) {
        editView.setLine1(customer.shipLocationName);
        editView.setLine2(customer.shipLine2);
        editView.setLine3(customer.shipLine3);
        editView.setLine4(customer.shipLine4);
        editView.setPostalCode(customer.shipPostalCode);
        editView.setCity(customer.shipCity);
    }

    /**
     * Remplie les champs d'édition d'adresse à partir de l'adresse de facturation
     * @param customer
     * @param editView
     */
    public static void fillCustomerInvoiceAddressEdit(Customer customer, CustomerEditAddressView editView) {
        editView.setLine1(customer.locationName);
        editView.setLine2(customer.line2);
        editView.setLine3(customer.line3);
        editView.setLine4(customer.line4);
        editView.setPostalCode(customer.postalCode);
        editView.setCity(customer.city);
    }

    @Step("Edite les infos du client associé au ticket")
    public static void editCustomerInfos(Customer customer, BaseStepValue stepValue) {
        // Click sur client puis modifier
        CustomerEditView detailView = new ReceiptView(stepValue.driver).openCustomerDetail().modifyCustomer();
        // Remplis les infos clients
        fillCustomerInfos(customer, detailView);
        // Click sauvegarder   et Vérification de la création
        stepValue.assertionMessage = "Validation modifications informations client " + customer.firstName + " " + customer.lastName + " sur OB : ";
        stepValue.isEquals(detailView.clickSave());
    }

    @Step("Edite les adresses du client associé au ticket")
    public static void editCustomerAddress(Customer customer, BaseStepValue stepValue) {
        // En premier l'adresse de livraison
        if (customer.shipPostalCode != null && !customer.shipPostalCode.isEmpty() && customer.shipCity != null && !customer.shipCity.isEmpty()) {
            // Ouvre la vue d'éditon de cette adresse
            CustomerEditAddressView detailView = new ReceiptView(stepValue.driver).openCustomerDetail().modifyAddress().editAddress(customer.shipPostalCode, customer.shipCity);
            // Remplis les infos clients
            fillCustomerShippingAddressEdit(customer, detailView);
            // Sauvegarder
            detailView.save();
        }
        // Adresse de facturation
        if (customer.postalCode != null && !customer.postalCode.isEmpty() && customer.city != null && !customer.city.isEmpty()) {
            // Ouvre la vue d'éditon de cette adresse
            CustomerEditAddressView detailView = new ReceiptView(stepValue.driver).openCustomerDetail().modifyAddress().editAddress(customer.shipPostalCode, customer.shipCity);
            // Remplis les infos clients
            fillCustomerInvoiceAddressEdit(customer, detailView);
            // Sauvegarder
            detailView.save();
        }
    }

    /**
     * Set le customerId en interrogeant la BDD OB si celui-ci n'est pas défini
     * @param cust
     * @return
     * @throws SQLException
     */
    private static void setCustomerId(Customer cust) throws SQLException {
        // Si le client n'a pas d'id on le récupère dans la DB OB
        if (cust.customerId == null || cust.customerId.isEmpty()) {
            cust.customerId = OpenBravoDBHelper.getCustomerID(cust.lastName, cust.firstName);
        }
    }

    /**
     * @param lastName
     * @param stepValue
     */
    //@Step("Associe le client {custLastName} {firstName} au ticket")
    /*public static void linkCustomerToTicketByLastName(String lastName, String firstName, BaseStepValue stepValue) {
        // Recherche du client par nom/prénom et association du premier résultat
        searchCustomerByLastAndFirstName(lastName, firstName, stepValue).clickOnFirstSearchResult();
        // Contrôle que le client est maintenant associé au ticket
        stepValue.expectedValue = firstName + " " + lastName;
    }*/
    @Step("Vérifie que le client {stepValue.expectedValue} est associé au ticket")
    public static void checkLinkedCustomer(BaseStepValue stepValue) {
        ReceiptView receiptView = new ReceiptView(stepValue.driver);
        stepValue.assertionMessage = "Client " + stepValue.expectedValue + " associé au ticket : ";
        stepValue.isEquals(WebElementHelper.waitUntilExpectedText(stepValue.getExpectedValue(), receiptView.getLinkedCustomer(), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

    @Step("Recherche un client par son nom et prénom")
    public static void searchCustomerByLastAndFirstName(String lastName, String firstName, BaseStepValue stepValue) {
        // Clic sur "Recherche client" et affiche la vue de recherche
        CustomerSearchView searchView = new ReceiptView(stepValue.driver).clickSearch();
        // Saisie le nom
        searchView.setLastName(lastName);
        // Saisie le prénom
        searchView.setFirstName(firstName);
        // Applique les filtre et lance la recherche
        searchView.clickApplyFilter();
    }

    @Step("Associe le client {stepValue.expectedValue} au ticket")
    public static void linkCustomer(BaseStepValue stepValue) {
        // Vue résultat de la recherche
        CustomerSearchResultView resultView = new CustomerSearchResultView(stepValue.driver);
        // On click sur le premeir client des résultats
        resultView.linkCustomer(stepValue.getExpectedValue(), true);
    }
}
