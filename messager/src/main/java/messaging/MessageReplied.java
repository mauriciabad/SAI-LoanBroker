package messaging;

public interface MessageReplied<TypeReceived, TypeSent> {
    void onMessage(TypeSent reqObj, TypeReceived replObj);
}