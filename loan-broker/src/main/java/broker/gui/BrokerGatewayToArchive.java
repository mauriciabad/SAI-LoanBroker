package broker.gui;

import bank.model.ArchiveRequest;
import messaging.MessageReceived;
import messaging.MessagerGateway;
import messaging.MessagerGatewayREST;

public class BrokerGatewayToArchive extends MessagerGateway<Object, ArchiveRequest> {

    private final MessagerGatewayREST<Object, ArchiveRequest> gateway = new MessagerGatewayREST<Object, ArchiveRequest>("http://localhost:8080/archive/rest", Object.class, ArchiveRequest.class);

    public BrokerGatewayToArchive() {}
    public void send(ArchiveRequest message, MessageReceived<Object> onReply){ gateway.send("/accepted", message, null); }
    public void reply(Object original, ArchiveRequest reply) {}
    public void setOnMessageReceived(MessageReceived<Object> function) {}
    public MessageReceived<Object> getOnMessageReceived() { return null; }
}
