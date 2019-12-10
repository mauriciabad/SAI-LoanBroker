package broker.gui;

import bank.model.BankInterestReply;
import bank.model.BankInterestRequest;
import loanclient.model.LoanReply;
import loanclient.model.LoanRequest;
import messaging.MessageReceived;
import messaging.MessageReplied;
import messaging.MessagerGateway;
import messaging.MessagerGatewayJMS;

public class BrokerGatewayToLoanClient extends MessagerGateway<LoanRequest, LoanReply> {

    private final MessagerGatewayJMS<LoanRequest, LoanReply> gateway = new MessagerGatewayJMS<LoanRequest, LoanReply>("Broker->Client", "Client->Broker", LoanRequest.class, LoanReply.class);

    public BrokerGatewayToLoanClient() {}

    public void send(LoanReply message){ gateway.send(message); }

    public void setOnMessageReceived(MessageReceived<LoanRequest> function) { gateway.setOnMessageReceived(function); }
    public MessageReceived<LoanRequest> getOnMessageReceived() { return gateway.getOnMessageReceived(); }
}
