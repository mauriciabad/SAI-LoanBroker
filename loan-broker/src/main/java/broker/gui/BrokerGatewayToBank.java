package broker.gui;

import bank.model.BankInterestReply;
import bank.model.BankInterestRequest;
import messaging.MessageReceived;
import messaging.MessagerGateway;
import messaging.MessagerGatewayJMS;

public class BrokerGatewayToBank extends MessagerGateway<BankInterestReply, BankInterestRequest> {

    private final MessagerGatewayJMS<BankInterestReply, BankInterestRequest> gateway = new MessagerGatewayJMS<BankInterestReply, BankInterestRequest>("Broker->Bank", "Bank->Broker", BankInterestReply.class, BankInterestRequest.class);

    public BrokerGatewayToBank() {}
    public void send(BankInterestRequest message, MessageReceived<BankInterestReply> onReply){ gateway.send(message, onReply); }
    public void reply(BankInterestReply original, BankInterestRequest reply) { gateway.reply(original, reply); }
    public void setOnMessageReceived(MessageReceived<BankInterestReply> function) { gateway.setOnMessageReceived(function); }
    public MessageReceived<BankInterestReply> getOnMessageReceived() { return gateway.getOnMessageReceived(); }
}
