package TestCases.Loyalty;

import Helpers.JAXB.Unmarshaller;
import Helpers.Test.BaseTest;
import Serializable.Customer.Customer;
import Serializable.Customer.CustomerList;
import Step.CustomerStep;
import Step.LoggingStep;
import Step.Value.BaseStepValue;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;

public class NOE919 extends BaseTest {

    @Test(description = "Création d'un client en caisse : contrôle champs obligatoires")
    @Parameters({"missingMandatoryFieldJsonPath"})
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-919")
    public void noe919(String missingMandatoryFieldJsonPath) throws IOException, InterruptedException, JAXBException, SQLException {
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Test infos manquants client
        missingFieldsTest(missingMandatoryFieldJsonPath);

    }

    @Step("Tentative de création clients avec champs obligatoires manquants")
    private void missingFieldsTest(String missingMandatoryFieldJsonPath) throws JAXBException, IOException, InterruptedException, SQLException {
        // On récupère les clients à créer
        CustomerList customersToCreate = (CustomerList) Unmarshaller.unmarshall(missingMandatoryFieldJsonPath, CustomerList.class);
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
            // Certains clients avec infos obligatoire manquantes doivent lancer une erreur à la création
            stepValue.expectedValue = cust.noErrorOnCreate;
            // Crée le client sur OB
            CustomerStep.createCustomer(cust, stepValue);
            // Set les résultats de recherche attendue
            stepValue.expectedValue = cust.noErrorOnCreate ? true : false;
            // Vérifie la présence ou pas du client sur RCU
            CustomerStep.checkCustomerPresenceOnRCU(cust, stepValue);
            // Si le client a été crée, on l'archive
            if (cust.noErrorOnCreate) {
                CustomerStep.archiveCustomer(cust, stepValue);
            }
        }
    }


}
