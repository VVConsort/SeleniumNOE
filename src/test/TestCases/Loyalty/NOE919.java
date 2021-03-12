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

public class NOE919 extends BaseTest {

    @Test(description = "Création client en caisse")
    @Parameters({"missingMandatoryFieldFilePath", "createOKFilePath"})
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-919")
    public void noe919(String missingMandatoryFieldFilePath, String createOKFilePath) throws Exception {
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Test infos manquants client
        missingFieldsTest(missingMandatoryFieldFilePath);
        // Test création clients infos ok
        createOkTest(createOKFilePath);
    }

    @Step("Tente de créer des clients ayant des informations obligatoires manquantes")
    private void missingFieldsTest(String missingMandatoryFieldFilePath) throws JAXBException {
        // On récupère les clients à créer
        CustomerList customersToCreate = (CustomerList) Unmarshaller.unmarshall(missingMandatoryFieldFilePath, CustomerList.class);
        BaseStepValue baseStep = getNewBaseStepValue(false);
        // Parcourt les clients à tenter de créer
        for (Customer customer : customersToCreate.customers) {
            // Certains clients avec infos obligatoires manquantes doivent lancer une erreur à la création
            baseStep.expectedValue = customer.noErrorOnCreate;
            // Tente de créer le client sur OB
            step("Vérifie qu'il est impossible de créer le client" + customer.firstName + " " + customer.lastName + " sur OpenBravo", () -> {
                CustomerStep.createCustomer(customer, baseStep);
            });
            // Controle non présence RCU
            step("Contrôle que le client n'a pas été crée sur RCU", () -> {
                try {
                    CustomerStep.checkCustomerPresenceOnRCU(customer, baseStep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Step("Création de client ayant les informations nécessaires")
    private void createOkTest(String createOKFilePath) throws JAXBException {
        // On récupère les clients à créer
        CustomerList customersToCreate = (CustomerList) Unmarshaller.unmarshall(createOKFilePath, CustomerList.class);
        BaseStepValue baseStep = getNewBaseStepValue(false);
        // Parcourt des clients à créer
        for (Customer customer : customersToCreate.customers) {
            // Certains clients avec infos obligatoires manquantes doivent lancer une erreur à la création
            baseStep.expectedValue = customer.noErrorOnCreate;
            // Crée le client sur OB
            step("Vérifie que le client" + customer.firstName + " " + customer.lastName + " est crée sur OpenBravo", () -> {
                CustomerStep.createCustomer(customer, baseStep);
            });
            // Controle  présence RCU
            step("Contrôle que le client a été crée sur RCU", () -> {
                try {
                    CustomerStep.checkCustomerPresenceOnRCU(customer, baseStep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // Controle données RCU
            step("Contrôle l'intégrité des données insérées sur RCU", () -> {
                try {
                    CustomerStep.checkRCUCustomerValues(customer, baseStep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // Association ticket
            step("Contrôle que le client est associé au ticket", () -> {
                        baseStep.expectedValue = customer.firstName + " " + customer.lastName;
                        // Vérifie qu'il est associé au ticket
                        TicketStep.checkLinkedCustomer(baseStep);
                    }
            );
            // Archivage du client
            step("Archive le client sur RCU", () -> {
                        // Vérifie qu'il est associé au ticket
                        baseStep.expectedValue = true;
                        try {
                            CustomerStep.archiveCustomer(customer, baseStep);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }
}
