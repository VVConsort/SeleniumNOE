package TestCases.Loyalty;

import Helpers.Test.BaseTest;
import Step.CustomerStep;
import Step.LoggingStep;
import Step.Value.BaseStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE3639 extends BaseTest {

    @Test(description = "Vérifier que les différents type de clients sont associables au ticket")
    @Parameters({"personLast", "personFirst", "businessSearchName", "businessDisplayName", "fleetSearchName", "fleetDisplayName"})
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-3639")
    public void NOE3639(String personLast, String personFirst, String businessSearchName, String businessDisplayName, String fleetSearchName, String fleetDisplayName) throws MalformedURLException, InterruptedException {
        // Lancement et identification sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Client personne
        searchAndLinkPersonCustomer(personLast, personFirst);
        // Client business
        searchFleetBusinessCustomer(businessSearchName, businessDisplayName);
        // Client flotte
        searchFleetBusinessCustomer(fleetSearchName, fleetDisplayName);
    }

    /**
     * Saisie et recherche le client de type personne
     * @param lastName
     * @param firstName
     */
    private void searchAndLinkPersonCustomer(String lastName, String firstName) {
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Recherche du client
        CustomerStep.searchCustomerByLastAndFirstName(lastName, firstName, stepValue);
        // On associe le premier résultat de la recherche en se basant sur le nom/prénom
        stepValue.expectedValue = firstName + " " + lastName;
        CustomerStep.linkCustomer(stepValue);
        // Controle de l'association au ticket
        checkTicketLink(stepValue.expectedValue.toString());
    }

    /**
     * Saisie et recherche le client de type business/flotte
     * @param searchName
     * @param displayName
     */
    private void searchFleetBusinessCustomer(String searchName, String displayName) {
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Recherche du client
        CustomerStep.searchCustomerByLastAndFirstName(searchName, "", stepValue);
        stepValue.expectedValue = searchName;
        // On associe le premier résultat de la recherche
        CustomerStep.linkCustomer(stepValue);
        // Controle de l'association au ticket
        checkTicketLink(displayName);
    }

    /**
     * Controle l'association du client au ticket
     * @param displayName
     */
    private void checkTicketLink(String displayName) {
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Le nom attendu en affichage sur le ticket
        stepValue.expectedValue = displayName;
        // Controle de l'association au ticket
        CustomerStep.checkLinkedCustomer(stepValue);
    }
}
