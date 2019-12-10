package messaging;

public abstract class MessagerGateway<TypeReceived, TypeSent> {
    public MessageReplied<TypeReceived, TypeSent> onMessageReceived = null;

    abstract void send(TypeSent message);

    public final void setOnMessageReplied(MessageReplied<TypeReceived, TypeSent> function) { this.onMessageReceived = function; }
    public final MessageReplied<TypeReceived, TypeSent> getOnMessageReplied() { return this.onMessageReceived; }

}
