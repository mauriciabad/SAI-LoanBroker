package messaging;

public interface MessageReceived<T> {
    void onMessage(T message);
}
