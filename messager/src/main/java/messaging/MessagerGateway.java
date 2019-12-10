package messaging;

public abstract class MessagerGateway<TypeReceived, TypeSent> {

    public void send(TypeSent message) { this.send(message, null); }
    public abstract void send(TypeSent message, MessageReceived<TypeReceived> onReply);

    public abstract void reply(TypeReceived original, TypeSent reply);

    public abstract void setOnMessageReceived(MessageReceived<TypeReceived> onMessageReceived);
    public abstract MessageReceived<TypeReceived> getOnMessageReceived();
}
