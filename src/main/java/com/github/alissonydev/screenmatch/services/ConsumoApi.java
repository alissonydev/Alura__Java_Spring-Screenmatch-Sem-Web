package com.github.alissonydev.screenmatch.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    private static final String YOUR_API_KEY = System.getenv("YOUR_API_KEY");

    public String obterDados(String endereco) {

        HttpResponse<String> response;
        HttpClient client = null;

        try {
            client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco + YOUR_API_KEY))
                    .build();

            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        client.close();
        return json;
    }
}
