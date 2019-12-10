package messaging;

public class MessagerGatewayREST<TypeReceived, TypeSent>{

    private final ApiClient<TypeReceived, TypeSent> apiClient;

    public MessagerGatewayREST(String baseUrl, Class<TypeReceived> typeReceived, Class<TypeSent> typeSent) {
        apiClient = new ApiClient<TypeReceived, TypeSent>(baseUrl, typeReceived, typeSent);
    }

    public void send(String endpoint, TypeSent message, MessageReceived<TypeReceived> onReply) {
        apiClient.send(endpoint, message, onReply);
    }
}
