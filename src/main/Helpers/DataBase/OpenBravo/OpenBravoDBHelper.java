package Helpers.DataBase.OpenBravo;

import Helpers.DataBase.PostgreSQLHelper;
import Helpers.Test.Properties.TestSuiteProperties;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OpenBravoDBHelper {

    // Requete de récup du customerID
    private static final String GET_CUSTOMER_ID_QUERY = "SELECT REFERENCENO FROM c_BPartner WHERE NAME = ? ORDER BY UPDATED DESC";
    //select * from c_BPartner where NAME = 'SiSiSi LeTesting'
    // Requete pour récupèrer le dernier bon d'achat crée
    private static final String GET_LAST_CREATED_COUPON = "select couponcode from obdiscp_coupon where created > ? and em_obcpotf_amount = ?";

    /**
     * Recherche un client par son nom prénom et renvoi son id
     * @param lastName
     * @param firstName
     * @return
     */
    public static String getCustomerID(String lastName, String firstName) throws SQLException {
        ResultSet rs = PostgreSQLHelper.executeQuery(TestSuiteProperties.OB_DATABASE_URL, TestSuiteProperties.OB_DATABASE_USER, TestSuiteProperties.OB_DATABASE_PASSWORD, GET_CUSTOMER_ID_QUERY, getCustomerIDQueryParams(lastName, firstName));
        while (rs.next()) {
            return rs.getString(1);
        }
        return "";
    }

    /**
     * Construit et retourne les paramètres pour la requete de récupération de customerId
     * @param lastName
     * @param firstName
     * @return
     */
    private static List getCustomerIDQueryParams(String lastName, String firstName) {
        List<String> result = new ArrayList<>();
        // Concat Nom Prénom
        result.add(firstName + " " + lastName);
        return result;
    }

    /**
     * Retourne le dernier bon d'achat crée en BDD
     * @return
     * @throws SQLException
     */
    public static String getLastCreatedCoupon(String couponAmount) throws SQLException {
        ResultSet rs = PostgreSQLHelper.executeQuery(TestSuiteProperties.OB_DATABASE_URL, TestSuiteProperties.OB_DATABASE_USER, TestSuiteProperties.OB_DATABASE_PASSWORD, GET_LAST_CREATED_COUPON, getLastCreatedCouponQueryParams(couponAmount));
        while (rs.next()) {
            return rs.getString(1);
        }
        return "";
    }

    /**
     * Construit et retourne les paramètres pour la requete de récupération du dernier coupon généré
     * @return
     */
    private static List getLastCreatedCouponQueryParams(String couponAmount) {
        List<Object> result = new ArrayList();
        // On filtre pour retourner le dernier coupon généré il y'a moins de 5 secs
        //SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // Date du jours
        Date now = new Date(System.currentTimeMillis());
        // Retire 5 seconds
        now.setTime(now.getTime() - 5000);
        // Format et ajoute la date sous le bon mask
        result.add(now);
        // Ajoute le montant du coupon
        result.add(couponAmount);
        // Renvoi les critères
        return result;
    }
}
