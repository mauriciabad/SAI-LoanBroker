package messaging;

import javax.jms.TextMessage;

public interface MessageRecieved {
    void onMessage(TextMessage message);
}
