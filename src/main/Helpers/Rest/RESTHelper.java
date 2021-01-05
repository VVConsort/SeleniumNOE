package Helpers.Rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RESTHelper {

    // Code de réponse OK
    public static final int OK_RESPONSE_CODE =200;
    /**
     * Post une requete à JSON vers l'URL spécifiée
     * @param URL
     * @param json
     * @return
     */
    public static int postJson(String URL, String json ) {
        // Création du client rest
        Client client = Client.create();
        // URL
        WebResource webResource = client.resource(URL);
        // JSON
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);
        // Si il y'a une erreur
        if (response.getStatus() != 200) {
            System.out.println("Failed : HTTP error code : " + response.getStatus());
            String error = response.getEntity(String.class);
            System.out.println("Error: " + error);
        }
        // Retourne la réponse au POST
        return response.getStatus();
    }
}
