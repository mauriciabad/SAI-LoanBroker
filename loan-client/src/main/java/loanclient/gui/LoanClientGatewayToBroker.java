package loanclient.gui;

import loanclient.model.LoanReply;
import loanclient.model.LoanRequest;
import messaging.MessageReceived;
import messaging.MessageReplied;
import messaging.MessagerGateway;
import messaging.MessagerGatewayJMS;

public class LoanClientGatewayToBroker extends MessagerGateway<LoanReply, LoanRequest> {

    private final MessagerGatewayJMS<LoanReply, LoanRequest> gateway = new MessagerGatewayJMS<LoanReply, LoanRequest>("Client->Broker", "Broker->Client", LoanReply.class, LoanRequest.class);

    public LoanClientGatewayToBroker() {}

    public void send(LoanRequest message){ gateway.send(message); }

    public void setOnMessageReplied(MessageReplied<LoanReply, LoanRequest> function) { gateway.setOnMessageReplied(function); }
    public MessageReplied<LoanReply, LoanRequest> getOnMessageReplied() { return gateway.getOnMessageReplied(); }

    public void setOnMessageReceived(MessageReceived<LoanReply> function) { gateway.setOnMessageReceived(function); }
    public MessageReceived<LoanReply> getOnMessageReceived() { return gateway.getOnMessageReceived(); }
}
