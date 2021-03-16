package Helpers.Rest;

import Enums.REST.RESTCodeStatut;
import Enums.REST.RESTMethod;
import com.sun.istack.Nullable;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class RESTHelper {

    /**
     * Envoie une requete POST
     * @param URL
     * @param jsonBody
     * @return
     */
    public static Response post(String URL, Map<String, String> headersArg, String jsonBody) throws IOException {
        // Construction de la requete et appel
        return buildRequestAndCall(URL, RESTMethod.POST, jsonBody, headersArg);
    }

    /**
     * Envoie une requete POST sans corps
     * @param URL
     * @param headersArg
     * @return
     * @throws IOException
     */
    public static Response post(String URL, Map<String, String> headersArg) throws IOException {
        // Construction de la requete et appel
        return buildRequestAndCall(URL, RESTMethod.POST, null, headersArg);
    }

    /**
     * Envoie une requete POST sans header, renvoi le code statut
     * @param URL
     * @param jsonBody
     * @return
     */
    public static Response post(String URL, String jsonBody) throws IOException {
        return post(URL, null, jsonBody);
    }

    /**
     * Envoie une requete GET et renvoi le code statut
     * @param URL
     * @param headersArg
     * @return
     * @throws IOException
     */
    public static Response get(String URL, Map<String, String> headersArg) throws IOException {
        return buildRequestAndCall(URL, RESTMethod.GET, null, headersArg);
    }

    /**
     * Envoie une requete PUT
     * @param URL
     * @param jsonBody
     * @return
     */
    public static Response put(String URL, Map<String, String> headersArg, String jsonBody) throws IOException {
        // Construction de la requete et appel
        return buildRequestAndCall(URL, RESTMethod.PUT, jsonBody, headersArg);
    }

    /**
     * Envoie une requete PUT sans header
     * @param URL
     * @param jsonBody
     * @return
     */
    public static Response put(String URL, String jsonBody) throws IOException {
        // Construction de la requete et appel
        return buildRequestAndCall(URL, RESTMethod.PUT, jsonBody, null);
    }

    /**
     * Construit et renvoi la requete
     * @param URL
     * @param method
     * @param jsonBody
     * @param headersArg
     * @return
     */
    private static Response buildRequestAndCall(String URL, RESTMethod method, @Nullable String jsonBody, @Nullable Map<String, String> headersArg) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody requestBody = null;
        if (jsonBody != null) {
            requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        }
        Request request = new Request.Builder()
                .method(method.getLabel(), requestBody)
                .url(URL)
                .headers(getHeader(headersArg))
                .build();
        // Appel
        Response response = client.newCall(request).execute();
        System.out.println(response.code() + " " + method.getLabel() + " " + URL);
        return response;
    }

    /**
     * Renvoi vrai si le server renvoi OK
     * @param response
     * @return
     */
    protected static boolean isResponseOk(Response response) {
        return response != null && (response.code() == RESTCodeStatut.OK.getCode() || response.code() == RESTCodeStatut.OK_NO_CONTENT.getCode());
    }

    /**
     * Construit et renvoi le header
     * @param headersArg
     * @return
     */
    private static Headers getHeader(Map<String, String> headersArg) {
        // On set les valeures communes
        Headers.Builder builder = new Headers.Builder();
        builder.add("Content-Length", "0");
        builder.add("Accept", "*/*");
        builder.add("Accept-Encoding", "UTF-8");
        builder.add("Connection", "keep-alive");
        // Params supplémentaires
        if (headersArg != null) {
            for (Map.Entry<String, String> entry : headersArg.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }

        // On build et retourne le header
        return builder.build();
    }
}
