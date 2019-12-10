package messaging;

public class MessagerGatewayJMS<TypeReceived, TypeSent> extends MessagerGateway<TypeReceived, TypeSent> {

    private final Messager<TypeReceived, TypeSent> messager;

    public MessagerGatewayJMS(String queueTo, String queueFrom, Class<TypeReceived> typeReceived, Class<TypeSent> typeSent) {
        messager = new Messager<TypeReceived, TypeSent>(queueTo, queueFrom, typeReceived, typeSent);
        messager.setOnMessageReplied(this.getOnMessageReplied());
    }

    @Override
    public void send(TypeSent message) {
        messager.send(message);
    }
}
