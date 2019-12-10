package broker.gui;

import messaging.MessageReceived;
import messaging.MessagerGateway;
import messaging.MessagerGatewayREST;

public class BrokerGatewayToCreditAgency extends MessagerGateway<CreditAgencyReply, Integer> {

    private final MessagerGatewayREST<CreditAgencyReply, Integer> gateway = new MessagerGatewayREST<CreditAgencyReply, Integer>("http://localhost:8080/credit/rest", CreditAgencyReply.class, Integer.class);

    public BrokerGatewayToCreditAgency() {}
    public void send(Integer message, MessageReceived<CreditAgencyReply> onReply){ gateway.send("/history/"+message, null, onReply); }
    public void reply(CreditAgencyReply original, Integer reply) {}
    public void setOnMessageReceived(MessageReceived<CreditAgencyReply> function) {}
    public MessageReceived<CreditAgencyReply> getOnMessageReceived() { return null; }
}
