package messaging;

public abstract class MessagerGateway<TypeReceived, TypeSent> {

    public abstract void send(TypeSent message);

    public abstract void setOnMessageReplied(MessageReplied<TypeReceived, TypeSent> function);
    public abstract MessageReplied<TypeReceived, TypeSent> getOnMessageReplied();

    public abstract void setOnMessageReceived(MessageReceived<TypeReceived> function);
    public abstract MessageReceived<TypeReceived> getOnMessageReceived();
}
