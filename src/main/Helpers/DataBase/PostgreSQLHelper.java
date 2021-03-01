package Helpers.DataBase;

import java.sql.*;

public class PostgreSQLHelper {

    private static Connection connect(String URL, String user, String password) {
        Connection result = null;
        try {
            Class.forName("org.postgresql.Driver");
            result = DriverManager.getConnection("jdbc:postgresql://localhost:5437/openbravo", "norauto_ro", "ajGAK22ezcJGX76M");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
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
    public static ResultSet executeQuery(String dbUrl, String dbUser, String dbPassword, String query, String[] queryParams) throws SQLException {
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
    private static void setParams(PreparedStatement statement, String[] paramsList) throws SQLException {
        // Parcourt les paramètres
        for (String param : paramsList) {
            int index = 1;
            statement.setString(index, param);
            index++;
        }
    }
}
