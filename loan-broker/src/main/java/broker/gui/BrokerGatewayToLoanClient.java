package broker.gui;

import loanclient.model.LoanReply;
import loanclient.model.LoanRequest;
import messaging.MessageReceived;
import messaging.MessagerGateway;
import messaging.MessagerGatewayJMS;

public class BrokerGatewayToLoanClient extends MessagerGateway<LoanRequest, LoanReply> {

    private final MessagerGatewayJMS<LoanRequest, LoanReply> gateway = new MessagerGatewayJMS<LoanRequest, LoanReply>("Broker->Client", "Client->Broker", LoanRequest.class, LoanReply.class);

    public BrokerGatewayToLoanClient() {}
    public void send(LoanReply message, MessageReceived<LoanRequest> onReply){ gateway.send(message, onReply); }
    public void reply(LoanRequest original, LoanReply reply) { gateway.reply(original, reply); }
    public void setOnMessageReceived(MessageReceived<LoanRequest> function) { gateway.setOnMessageReceived(function); }
    public MessageReceived<LoanRequest> getOnMessageReceived() { return gateway.getOnMessageReceived(); }
}
