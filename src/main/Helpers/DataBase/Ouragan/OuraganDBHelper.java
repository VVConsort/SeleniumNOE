package Helpers.DataBase.Ouragan;

import main.Helpers.DataBase.OracleSQLHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OuraganDBHelper {

    // Requete SQL pour récupèrer l'état d'une commande
    private static final String GET_ORDER_STATE_QUERY = "SELECT ETARESCLI FROM RESERVATION WHERE NUMCDECLI = ?";
    // Colonne "Etat commande"
    private static final String ORDER_STATE_COLUMN_NAME = "ETARESCLI";
    // Classe utilitaire pour les appels BDD Oracle
    private OracleSQLHelper _dbHelper;

    /**
     * Retourne l'état de la commande à partir de son documentCode
     * @param documentCode
     * @return
     */
    public String getStateOfOrder(String dbURL, String dbUser, String dbPassword, String documentCode) throws SQLException {
        String result = "";
        // On éxécute la requete de récupération d'état
        ResultSet queryResult = getDBHelper().executeQuery(dbURL, dbUser, dbPassword, GET_ORDER_STATE_QUERY, new String[]{documentCode});
        // Blindage
        if (queryResult != null) {
            // Parcourt du résultat
            while (queryResult.next()) {
                // On retourne l'état
                result = queryResult.getString(ORDER_STATE_COLUMN_NAME);
            }
        }
        return result;
    }

    public void updateOrderState(String dbURL, String dbUser, String dbPassword,String documentCode)
    {

    }

    /**
     * Instancie si besoin et retourne la classe utilitaire pour les requetes BDD
     * @return
     */
    private OracleSQLHelper getDBHelper() {
        if (_dbHelper == null) {
            _dbHelper = new OracleSQLHelper();
        }
        return _dbHelper;
    }
}
