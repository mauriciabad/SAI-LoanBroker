package broker.gui;

import bank.model.BankInterestReply;
import bank.model.BankInterestRequest;
import messaging.*;

public class BrokerGatewayToBank extends MessagerGateway<BankInterestReply, BankInterestRequest> {

    private final MessagerGatewayJMS<BankInterestReply, BankInterestRequest> gateway = new MessagerGatewayJMS<BankInterestReply, BankInterestRequest>("Broker->Bank", "Bank->Broker", BankInterestReply.class, BankInterestRequest.class);

    public BrokerGatewayToBank() {}

    public void send(BankInterestRequest message){ gateway.send(message); }

    public void setOnMessageReplied(MessageReplied<BankInterestReply, BankInterestRequest> function) { gateway.setOnMessageReplied(function); }
    public MessageReplied<BankInterestReply, BankInterestRequest> getOnMessageReplied() { return gateway.getOnMessageReplied(); }

    public void setOnMessageReceived(MessageReceived<BankInterestReply> function) { gateway.setOnMessageReceived(function); }
    public MessageReceived<BankInterestReply> getOnMessageReceived() { return gateway.getOnMessageReceived(); }
}
