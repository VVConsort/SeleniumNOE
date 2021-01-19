package Helpers.Test.TestSuiteProperties;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static final String OB_POS_URL_KEY = "OB_POS_URL";
    private static final String TERMINAL_KEY_KEY = "TERMINAL_KEY";
    private static final String USER_KEY = "USER";
    private static final String PASSWORD_KEY = "PASSWORD";
    private static final String CHROME_PROFILE_PATH_KEY = "CHROME_PROFILE_PATH";
    private static final String CHROME_PROFILE_KEY = "CHROME_PROFILE";
    private static final String ORDER_LOADER_URL_KEY = "ORDER_LOADER_URL";
    private static final String ORDER_LOADER_USER_KEY = "ORDER_LOADER_USER";
    private static final String ORDER_LOADER_PASSWORD_KEY = "ORDER_LOADER_PASSWORD";
    private static final String ORDER_LOADER_SYNCHRONIZED_PROCESSING_KEY = "ORDER_LOADER_SYNCHRONIZED_PROCESSING";
    private static final String OURAGAN_DB_URL = "OURAGAN_DB_URL";
    private static final String OURAGAN_DB_USER = "OURAGAN_DB_USER";
    private static final String OURAGAN_DB_PASSWORD = "OURAGAN_DB_PASSWORD";

    /**
     * Charge les propriétés de la TestSuite à partir du chemin spécifié en paramètre
     * @param propertiesFilePath
     */
    public void setTestSuiteProperties(String propertiesFilePath) {
        Properties properties = new Properties();
        File a = new File(propertiesFilePath);
        // On lit le fichier à partir du chemin en param
        try {
            properties.load(new FileReader(a.getCanonicalPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Affectation des propriétés de la TS
        TestSuiteProperties.OB_POS_URL = properties.getProperty(OB_POS_URL_KEY);
        TestSuiteProperties.TERMINAL_KEY = properties.getProperty(TERMINAL_KEY_KEY);
        TestSuiteProperties.USERNAME = properties.getProperty(USER_KEY);
        TestSuiteProperties.PASSWORD = properties.getProperty(PASSWORD_KEY);
        TestSuiteProperties.CHROME_PROFILE_PATH = properties.getProperty(CHROME_PROFILE_PATH_KEY);
        TestSuiteProperties.CHROME_PROFILE = properties.getProperty(CHROME_PROFILE_KEY);
        TestSuiteProperties.ORDER_LOADER_URL = properties.getProperty(ORDER_LOADER_URL_KEY);
        TestSuiteProperties.ORDER_LOADER_USER = properties.getProperty(ORDER_LOADER_USER_KEY);
        TestSuiteProperties.ORDER_LOADER_PASSWORD = properties.getProperty(ORDER_LOADER_PASSWORD_KEY);
        TestSuiteProperties.ORDER_LOADER_SYNCHRONIZED_PROCESSING = properties.getProperty(ORDER_LOADER_SYNCHRONIZED_PROCESSING_KEY);
        TestSuiteProperties.OURAGAN_DB_URL = properties.getProperty(OURAGAN_DB_URL);
        TestSuiteProperties.OURAGAN_DB_USER = properties.getProperty(OURAGAN_DB_USER);
        TestSuiteProperties.OURAGAN_DB_PASSWORD = properties.getProperty(OURAGAN_DB_PASSWORD);
    }
}

