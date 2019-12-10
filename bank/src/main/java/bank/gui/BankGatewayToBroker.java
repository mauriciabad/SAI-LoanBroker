package bank.gui;

import bank.model.BankInterestReply;
import bank.model.BankInterestRequest;
import messaging.MessageReceived;
import messaging.MessagerGateway;
import messaging.MessagerGatewayJMS;

public class BankGatewayToBroker extends MessagerGateway<BankInterestRequest, BankInterestReply> {

    private final MessagerGatewayJMS<BankInterestRequest, BankInterestReply> gateway = new MessagerGatewayJMS<BankInterestRequest, BankInterestReply>("Bank->Broker", "Broker->Bank", BankInterestRequest.class, BankInterestReply.class);

    public BankGatewayToBroker() {}
    public void send(BankInterestReply message, MessageReceived<BankInterestRequest> onReply){ gateway.send(message, onReply); }
    public void reply(BankInterestRequest original, BankInterestReply reply) { gateway.reply(original, reply); }
    public void setOnMessageReceived(MessageReceived<BankInterestRequest> function) { gateway.setOnMessageReceived(function); }
    public MessageReceived<BankInterestRequest> getOnMessageReceived() { return gateway.getOnMessageReceived(); }
}
