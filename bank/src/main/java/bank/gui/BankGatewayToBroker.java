package bank.gui;

import bank.model.BankInterestReply;
import bank.model.BankInterestRequest;
import messaging.MessageReceived;
import messaging.MessageReplied;
import messaging.MessagerGateway;
import messaging.MessagerGatewayJMS;

public class BankGatewayToBroker extends MessagerGateway<BankInterestRequest, BankInterestReply> {

    private final MessagerGatewayJMS<BankInterestRequest, BankInterestReply> gateway = new MessagerGatewayJMS<BankInterestRequest, BankInterestReply>("Bank->Broker", "Broker->Bank", BankInterestRequest.class, BankInterestReply.class);

    public BankGatewayToBroker() {}

    public void send(BankInterestReply message){ gateway.send(message); }

    public void setOnMessageReplied(MessageReplied<BankInterestRequest, BankInterestReply> function) { gateway.setOnMessageReplied(function); }
    public MessageReplied<BankInterestRequest, BankInterestReply> getOnMessageReplied() { return gateway.getOnMessageReplied(); }

    public void setOnMessageReceived(MessageReceived<BankInterestRequest> function) { gateway.setOnMessageReceived(function); }
    public MessageReceived<BankInterestRequest> getOnMessageReceived() { return gateway.getOnMessageReceived(); }
}
