package Helpers.DataBase;

import java.sql.*;
import java.util.List;

public class PostgreSQLHelper {

    private static Connection connect(String URL, String user, String password) {
        Connection result = null;
        try {
            Class.forName("org.postgresql.Driver");
            result = DriverManager.getConnection(URL, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Connection PostgreSQL ouverture");
        return result;
    }

    /***
     * Ajoute les paramètres à la requete et l'éxécute
     * @param query
     * @param queryParams
     * @return
     */
    public static ResultSet executeQuery(String dbUrl, String dbUser, String dbPassword, String query, List<Object> queryParams) throws SQLException {
        // Connection à la DBB
        Connection connection = connect(dbUrl, dbUser, dbPassword);
        // On crée une requete préparée
        PreparedStatement prepStatement = connection.prepareStatement(query);
        // Ajout des params
        setParams(prepStatement, queryParams);
        // Execution de la requete
        return prepStatement.executeQuery();
    }

    /**
     * Ajoute les paramètres à la requete préparée
     * @param statement
     * @param paramsList
     * @throws SQLException
     */
    private static void setParams(PreparedStatement statement, List<Object> paramsList) throws SQLException {
        int index = 1;
        // Parcourt les paramètres
        for (Object param : paramsList) {
            if (param instanceof String) {
                statement.setString(index, (String) param);
            } else if (param instanceof Date) {
                statement.setDate(index, (Date) param);
            }
            index++;
        }
    }
}
