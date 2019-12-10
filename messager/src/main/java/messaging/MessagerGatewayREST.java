package messaging;

public class MessagerGatewayREST<TypeReceived, TypeSent> extends MessagerGateway<TypeReceived, TypeSent> {

    private final ApiClient<TypeReceived, TypeSent> apiClient;

    public MessagerGatewayREST(String url, Class<TypeReceived> typeReceived, Class<TypeSent> typeSent) {
        apiClient = new ApiClient<TypeReceived, TypeSent>(url, typeReceived, typeSent);
    }

    public void send(TypeSent message, MessageReceived<TypeReceived> onReply) {
        apiClient.send(message, onReply);
    }

    public void reply(TypeReceived original, TypeSent reply) {}

    public void setOnMessageReceived(MessageReceived<TypeReceived> function) {}
    public MessageReceived<TypeReceived> getOnMessageReceived() { return null; }
}
