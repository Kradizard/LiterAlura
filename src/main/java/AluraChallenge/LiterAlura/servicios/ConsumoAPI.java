package AluraChallenge.LiterAlura.servicios;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ConsumoAPI {
    private String URL_BASE = "https://gutendex.com/books/?search=";
    public String obtenerDatosLibro(String tituloLibro){

        String url = URL_BASE + tituloLibro.replace(" ", "%20");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;

        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();


    }

    //Metodo para convertir el json a un Map y obtener el idioma
    public String getIdioma(Map<String, Object> jsonMap) {
        List<String> languages = (List<String>) jsonMap.get("languages");
        return languages.get(0);
    }
}
