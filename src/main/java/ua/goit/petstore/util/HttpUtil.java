package ua.goit.petstore.util;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtil<T> {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    protected static final Gson GSON = new Gson();

    protected static final String HOST = "https://petstore.swagger.io/v2";

    protected static HttpRequest sendGet(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    protected static HttpRequest sendDelete(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
    }

    protected static <T> HttpRequest requestWithBody(String methodName, String url, T entity) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-type", "application/json")
                .method(methodName, HttpRequest.BodyPublishers.ofString(GSON.toJson(entity)))
                .build();
    }

    protected static HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
