package Helpers.Test.Properties.Loader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BaseLoader {

    protected Properties currentProperties;

    /**
     * Charge les propriétés de la TestSuite à partir du chemin spécifié en paramètre
     * @param propertiesFilePath
     */
    private void loadPropertiesFile(String propertiesFilePath) {
        currentProperties= new Properties();
        File a = new File(propertiesFilePath);
        // On lit le fichier à partir du chemin en param
        try {
            currentProperties.load(new FileReader(a.getCanonicalPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseLoader(String propertiesFilePath) {
        loadPropertiesFile(propertiesFilePath);
    }
}
