package loanclient.gui;

import loanclient.model.LoanReply;
import loanclient.model.LoanRequest;
import messaging.MessageReceived;
import messaging.MessagerGateway;
import messaging.MessagerGatewayJMS;

public class LoanClientGatewayToBroker extends MessagerGateway<LoanReply, LoanRequest> {

    private final MessagerGatewayJMS<LoanReply, LoanRequest> gateway = new MessagerGatewayJMS<LoanReply, LoanRequest>("Client->Broker", "Broker->Client", LoanReply.class, LoanRequest.class);

    public LoanClientGatewayToBroker() {}
    public void send(LoanRequest message, MessageReceived<LoanReply> onReply){ gateway.send(message, onReply); }
    public void reply(LoanReply original, LoanRequest reply) { gateway.reply(original, reply); }
    public void setOnMessageReceived(MessageReceived<LoanReply> onMessageReceived) { gateway.setOnMessageReceived(onMessageReceived); }
    public MessageReceived<LoanReply> getOnMessageReceived() { return gateway.getOnMessageReceived(); }
}
