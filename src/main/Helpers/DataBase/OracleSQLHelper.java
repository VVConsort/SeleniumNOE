package main.Helpers.DataBase;

import java.sql.*;
import java.util.List;

public class OracleSQLHelper {

    /**
     * Se connecte à la BDD passée en paramètre
     * @param dbUrl
     * @param dbUser
     * @param dbPassword
     * @throws SQLException
     */
    private Connection connect(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        Connection connection = null;
        // Connection à la BDD passée en param
        connection = DriverManager.getConnection("jdbc:oracle:thin:@" + dbUrl, dbUser, dbPassword);
        return connection;
    }

    /***
     * Ajoute les paramètres à la requete et l'éxécute
     * @param query
     * @param queryParams
     * @return
     */
    public ResultSet executeQuery(String dbUrl, String dbUser, String dbPassword, String query, String[] queryParams) throws SQLException {
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
    private void setParams(PreparedStatement statement, String[] paramsList) throws SQLException {
        // Parcourt les paramètres
        for (String param : paramsList) {
            int index = 1;
            statement.setString(index, param);
            index++;
        }
    }


}
