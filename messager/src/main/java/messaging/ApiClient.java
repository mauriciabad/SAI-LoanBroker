package messaging;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiClient<TypeReceived, TypeSent> {
    private String url;

    private Type typeReceived;
    private Type typeSent;

    private Gson gson = new Gson();
    private HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    public ApiClient(String url, Class<TypeReceived> typeReceived, Class<TypeSent> typeSent){
        this.url = url;
        this.typeReceived = typeReceived;
        this.typeSent = typeSent;
    }

    public void send(TypeSent message, MessageReceived<TypeReceived> onReply) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .header("Accept", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(message, typeSent)))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenAccept(response -> {
                if(response.statusCode() / 100 == 2) {
                    if(onReply != null) onReply.onMessage(gson.fromJson(response.body(), typeReceived));
                }else{
                    System.out.println("Response error " + response.statusCode());
                }
            });
    }
}
