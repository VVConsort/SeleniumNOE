package Helpers.Test.Properties.Loader;

import Helpers.Test.Properties.TestSuiteProperties;

public class TestPropertiesLoader extends BaseLoader {
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
    private static final String OB_BO_URL = "OB_BO_URL";
    private static final String RCU_AUTH_URL = "RCU_AUTH_URL";
    private static final String RCU_GET_TOKEN_AUTH = "RCU_GET_TOKEN_AUTH";
    private static final String RCU_SEARCH_CUST_URL = "RCU_SEARCH_CUST_URL";
    private static final String RCU_ARCHIVE_CUST_URL = "RCU_ARCHIVE_CUST_URL";
    private static final String RCU_GET_CUST_URL = "RCU_GET_CUST_URL";
    private static final String OB_DATABASE_URL = "OB_DATABASE_URL";
    private static final String OB_DATABASE_USER = "OB_DATABASE_USER";
    private static final String OB_DATABASE_PASSWORD = "OB_DATABASE_PASSWORD";

    public TestPropertiesLoader(String propertiesFilePath) {
        super(propertiesFilePath);
    }

    /**
     * Charge les propriétés de la TestSuite à partir du chemin spécifié en paramètre
     * @param propertiesFilePath
     */
    /*public void loadPropertiesFile(String propertiesFilePath) {
        Properties properties = new Properties();
        File a = new File(propertiesFilePath);
        // On lit le fichier à partir du chemin en param
        try {
            properties.load(new FileReader(a.getCanonicalPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setProperties(properties);
    }*/
    public void setProperties() {
        // Affectation des propriétés de la TS
        TestSuiteProperties.OB_POS_URL = currentProperties.getProperty(OB_POS_URL_KEY);
        TestSuiteProperties.TERMINAL_KEY = currentProperties.getProperty(TERMINAL_KEY_KEY);
        TestSuiteProperties.USERNAME = currentProperties.getProperty(USER_KEY);
        TestSuiteProperties.PASSWORD = currentProperties.getProperty(PASSWORD_KEY);
        TestSuiteProperties.CHROME_PROFILE_PATH = currentProperties.getProperty(CHROME_PROFILE_PATH_KEY);
        TestSuiteProperties.CHROME_PROFILE = currentProperties.getProperty(CHROME_PROFILE_KEY);
        TestSuiteProperties.ORDER_LOADER_URL = currentProperties.getProperty(ORDER_LOADER_URL_KEY);
        TestSuiteProperties.ORDER_LOADER_USER = currentProperties.getProperty(ORDER_LOADER_USER_KEY);
        TestSuiteProperties.ORDER_LOADER_PASSWORD = currentProperties.getProperty(ORDER_LOADER_PASSWORD_KEY);
        TestSuiteProperties.ORDER_LOADER_SYNCHRONIZED_PROCESSING = currentProperties.getProperty(ORDER_LOADER_SYNCHRONIZED_PROCESSING_KEY);
        TestSuiteProperties.OURAGAN_DB_URL = currentProperties.getProperty(OURAGAN_DB_URL);
        TestSuiteProperties.OURAGAN_DB_USER = currentProperties.getProperty(OURAGAN_DB_USER);
        TestSuiteProperties.OURAGAN_DB_PASSWORD = currentProperties.getProperty(OURAGAN_DB_PASSWORD);
        TestSuiteProperties.OB_BO_URL = currentProperties.getProperty(OB_BO_URL);
        TestSuiteProperties.RCU_AUTH_URL = currentProperties.getProperty(RCU_AUTH_URL);
        TestSuiteProperties.RCU_GET_TOKEN_ID = currentProperties.getProperty(RCU_GET_TOKEN_AUTH);
        TestSuiteProperties.RCU_SEARCH_CUST_URL = currentProperties.getProperty(RCU_SEARCH_CUST_URL);
        TestSuiteProperties.RCU_ARCHIVE_CUST_URL = currentProperties.getProperty(RCU_ARCHIVE_CUST_URL);
        TestSuiteProperties.RCU_GET_CUST_URL = currentProperties.getProperty(RCU_GET_CUST_URL);
        TestSuiteProperties.OB_DATABASE_URL = currentProperties.getProperty(OB_DATABASE_URL);
        TestSuiteProperties.OB_DATABASE_USER = currentProperties.getProperty(OB_DATABASE_USER);
        TestSuiteProperties.OB_DATABASE_PASSWORD = currentProperties.getProperty(OB_DATABASE_PASSWORD);
    }
}

