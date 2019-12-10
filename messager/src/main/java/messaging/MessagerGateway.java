package messaging;

public abstract class MessagerGateway<TypeReceived, TypeSent> {

    public abstract void send(TypeSent message);

    public abstract void setOnMessageReceived(MessageReceived<TypeReceived> function);
    public abstract MessageReceived<TypeReceived> getOnMessageReceived();
}
