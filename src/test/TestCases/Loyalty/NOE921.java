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

public class NOE921 extends BaseTest {

    // Dans le cas ou l'on modifie un client PERSON->BUSINESS le lastName n'est plus modifiable dans la fiche mais il reste utilisé pour l'identification du client en base
    private String _customerId = "";

    @Test(description = "Modification d'un client en caisse")
    @Parameters({"createCustomerFilePath", "modifyCustomerFilePath"})
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-921")
    public void noe921(String createCustomerFilePath, String modifyCustomerFilePath) throws IOException, InterruptedException, JAXBException, SQLException {
        // Récupère le client à créer
        CustomerList customerToCreate = (CustomerList) Unmarshaller.unmarshall(createCustomerFilePath, CustomerList.class);
        // On récupère le client à modifier
        CustomerList customersToModify = (CustomerList) Unmarshaller.unmarshall(modifyCustomerFilePath, CustomerList.class);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Création du client à modifier
        createCustomerToEdit(customerToCreate);
        // Modification du client
        editCustomerInfosAndCheckInRcu(customersToModify);
        // Archivage du client
        archive(customersToModify);
    }

    @Step("Création du client à modifier")
    private void createCustomerToEdit(CustomerList custList) throws JAXBException, InterruptedException, IOException, SQLException {
        BaseStepValue stepValue = getNewBaseStepValue(true);
        // Parcourt de la liste (un seul client)
        for (Customer cust : custList.customers) {
            stepValue.expectedValue = cust.noErrorOnCreate;
            // Crée le client
            CustomerStep.createCustomer(cust, stepValue);
            // Vérifie la présence sur RCU
            CustomerStep.checkCustomerPresenceOnRCU(cust, stepValue);
            // Vérifie qu'il est associé au ticket
            stepValue.expectedValue = cust.firstName + " " + cust.lastName;
            CustomerStep.checkLinkedCustomer(stepValue);
            // Stockage de l'id
            _customerId = cust.customerId;
        }
    }

    @Step("Modification des infos clients")
    private void editCustomerInfosAndCheckInRcu(CustomerList custList) throws JAXBException, IOException, SQLException {
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Parcourt de la liste
        for (Customer cust : custList.customers) {
            stepValue.expectedValue = cust.noErrorOnCreate;
            // Modification des infos
            CustomerStep.editCustomerInfos(cust, stepValue);
            //TODO Modification des adresses  quand rework RCU-OB sera fait
            // On remet le customerId
            cust.customerId = _customerId;
            // Vérification des modifications dans RCU
            CustomerStep.checkRCUCustomerValues(cust, stepValue);
        }
    }

    @Step("Archivage sur RCU")
    private void archive(CustomerList custList) throws IOException, SQLException {
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Les clients de la liste correspondant tous au même client RCU/OB (customerId identique), on en archive un seul
        Customer custToKill = custList.customers.get(0);
        // Reaffecton du customerId
        custToKill.customerId = _customerId;
        CustomerStep.archiveCustomer(custToKill, stepValue);
    }
}
