package TestCases.Loyalty;

import Helpers.JAXB.Unmarshaller;
import Helpers.Test.BaseTest;
import Serializable.Customer.Customer;
import Serializable.Customer.CustomerList;
import Step.CustomerStep;
import Step.LoggingStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;

public class NOE919 extends BaseTest {

    @Test(description = "Création d'un client en caisse")
    @Parameters({"missingMandatoryFieldFilePath", "createOKFilePath"})
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-919")
    public void noe919(String missingMandatoryFieldFilePath, String createOKFilePath) throws IOException, InterruptedException, JAXBException, SQLException {
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Test infos manquants client
        missingFieldsTest(missingMandatoryFieldFilePath);
        // Test création clients infos ok
        createOkTest(createOKFilePath);
    }

    @Step("Tentative de création clients avec champs obligatoires manquants")
    private void missingFieldsTest(String missingMandatoryFieldFilePath) throws JAXBException, IOException, InterruptedException, SQLException {
        // On récupère les clients à créer
        CustomerList customersToCreate = (CustomerList) Unmarshaller.unmarshall(missingMandatoryFieldFilePath, CustomerList.class);
        // Crée la liste de clients
        createCustomers(customersToCreate);
    }

    @Step("Tentative de création clients avec champs obligatoires renseignés")
    private void createOkTest(String createOKFilePath) throws InterruptedException, SQLException, IOException, JAXBException {
        // On récupère les clients à créer
        CustomerList customersToCreate = (CustomerList) Unmarshaller.unmarshall(createOKFilePath, CustomerList.class);
        // Crée la liste de clients
        createCustomers(customersToCreate);
    }

    /**
     * Crée une liste de client sur OB
     * @param customerList
     * @throws InterruptedException
     */
    private void createCustomers(CustomerList customerList) throws IOException, InterruptedException, SQLException {
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Parcourt des clients à créer
        for (Customer cust : customerList.customers) {
            // Certains clients avec infos obligatoires manquantes doivent lancer une erreur à la création
            stepValue.expectedValue = cust.noErrorOnCreate;
            // Crée le client sur OB
            CustomerStep.createCustomer(cust, stepValue);
            // Vérifie la présence ou pas du client sur RCU
            CustomerStep.checkCustomerPresenceOnRCU(cust, stepValue);
            // Si le client a été crée
            if (cust.noErrorOnCreate) {
                stepValue.expectedValue = cust.firstName + " " + cust.lastName;
                // Vérifie qu'il est associé au ticket
                TicketStep.checkLinkedCustomer(stepValue);
                // Comparaison des données OB/RCU
                CustomerStep.checkRCUCustomerValues(cust, stepValue);
                // Archivage du client
                CustomerStep.archiveCustomer(cust, stepValue);
            }
        }
    }
}
