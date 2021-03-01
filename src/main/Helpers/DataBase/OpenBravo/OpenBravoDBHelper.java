package Helpers.DataBase.OpenBravo;

import Helpers.DataBase.PostgreSQLHelper;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpenBravoDBHelper {

    // Requete de récup du customerID
    private static final String GET_CUSTOMER_ID_QUERY = "SELECT REFERENCENO FROM c_BPartner WHERE NAME = ?";
    //select * from c_BPartner where NAME = 'SiSiSi LeTesting'

    /**
     * Recherche un client par son nom prénom et renvoi son id
     * @param lastName
     * @param firstName
     * @return
     */
    public static String getCustomerID(String lastName, String firstName) throws SQLException {
        ResultSet rs = PostgreSQLHelper.executeQuery(TestSuiteProperties.OB_DATABASE_URL, TestSuiteProperties.OB_DATABASE_USER, TestSuiteProperties.OB_DATABASE_PASSWORD, GET_CUSTOMER_ID_QUERY, new String[]{getCustomerIDQueryParams(lastName, firstName)});
        while (rs.next()) {
            return rs.getString(1);
        }
        return "";
    }

    /**
     * Retourne les paramètres pour la requete de récupération de customerId
     * @param lastName
     * @param firstName
     * @return
     */
    private static String getCustomerIDQueryParams(String lastName, String firstName) {
        String result = "";
        // Concat Nom Prénom
        result = firstName + " " + lastName;
        return result;
    }
}
