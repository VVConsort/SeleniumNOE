package Helpers.Rest.XRay;

import Helpers.Rest.RESTHelper;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class XRayRestHelper extends RESTHelper {
    // Préfxx token d'accès
    private static final String TOKEN_PREFIX = "Bearer ";
    // Clé json id client
    private static final String CLIENT_ID_KEY = "client_id";
    // Clé json secret
    private static final String CLIENT_SECRET_KEY = "client_secret";
    // Clé id/token header
    private static final String AUTH_HEADER_KEY = "Authorization";
    // Token
    private static String accesToken = "";

    /**
     * Récupère le token d'authentification auprès d'XRay si nécessaire
     * @throws IOException
     */
    private static void setToken() throws IOException {
        // Seulement si le token n'a pas déjà été récupéré
        if (accesToken == null || accesToken.isEmpty()) {
            // TODO mettre les différents liens dans les TestSuiteProperties
            Response authResponse = post("https://xray.cloud.xpand-it.com/api/v2/authenticate", getAuthBody());
            if (isResponseOk(authResponse)) {
                accesToken = getAccessTokenFromResponse(authResponse.body().string());
            }
        }
    }

    public static void postReportToXRay(String testName) throws IOException {
        // Récupère le token si nécessaire
        setToken();
        // On met le content du report dans un String
        //target/cucumber-report" + "NOE3639" + ".json"
        String content = Files.readString(Paths.get("target/cucumber-report" + testName + ".json"));
        // Envoi à XRay
        Response postResponse = post("https://xray.cloud.xpand-it.com/api/v2/import/execution/cucumber/", getHeaderArgs(), content);
        // Si le POST est ok
        if (isResponseOk(postResponse)) {
            // Copie et move vers target/sentCucumberReport
            /*Path pathToFile = Paths.get("target/cucumberReports/" + testName + ".json");
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
            Files.write(pathToFile, content.getBytes(StandardCharsets.UTF_8));*/
        }
    }

    /**
     * Retour le corps json pour la demande de token
     * @return
     */
    private static String getAuthBody() {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put(CLIENT_ID_KEY, "E91832EFDA5B4376835FC76B6E3301F8");
        jsonBody.put(CLIENT_SECRET_KEY, "5a681c87af3625cae27fb8fa9cd5b5f16b922e4169882f63528f812d3f41c5c1");
        return jsonBody.toString();
    }

    /**
     * Construit et renvoi le token d'auth à partir de la réponse
     * @param authResponse
     * @return
     */
    private static String getAccessTokenFromResponse(String authResponse) {
        return TOKEN_PREFIX + authResponse.replace("\"", "");
    }

    /**
     * Construit et retourne le header
     * @return
     */
    private static Map getHeaderArgs() {
        Map headerArgs = new HashMap<String, String>();
        headerArgs.put(AUTH_HEADER_KEY, accesToken);
        return headerArgs;
    }
}
