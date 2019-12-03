package broker.gui;

import bank.model.BankInterestReply;
import bank.model.BankInterestRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import loanclient.model.LoanReply;
import loanclient.model.LoanRequest;
import messaging.ListViewLine;
import messaging.Messager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BrokerController implements Initializable {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    private TextField tfSsn;
    @FXML
    private TextField tfAmount;
    @FXML
    private TextField tfTime;
    @FXML
    private ListView<ListViewLine> listView;

    private Messager messagerClient;
    private Messager messagerBank;

    public BrokerController(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messagerClient = new Messager("Client->Broker", LoanRequest.class, "Broker->Bank", BankInterestRequest.class);
        messagerClient.setOnMessageReceieved(msg -> {
            logger.info("messageReceived: " + msg);
            ListViewLine.addReq(listView, msg);
        });
        messagerClient.setOnMessageListUpdated(() -> {
            //logger.info("ReceivedMessages: " + messagerClient.getReceivedMessages());
            //logger.info("SentMessages: " + messagerClient.getSentMessages());
        });
        messagerClient.enableAutoRedirect((objFrom) -> {
            LoanRequest o = (LoanRequest) objFrom;
            return new BankInterestRequest(o.getId(), o.getAmount(), o.getTime());
        });


        messagerBank = new Messager("Bank->Broker", BankInterestReply.class, "Broker->Client", LoanReply.class);
        messagerBank.setOnMessageReceieved(msg -> {
            logger.info("messageReceived: " + msg);
            ListViewLine.addRepl(listView, msg);
        });
        messagerBank.setOnMessageListUpdated(() -> {
            //logger.info("ReceivedMessages: " + messagerBank.getReceivedMessages());
            //logger.info("SentMessages: " + messagerBank.getSentMessages());
        });
        messagerBank.enableAutoRedirect((objFrom) -> {
            BankInterestReply o = (BankInterestReply) objFrom;
            return new LoanReply(o.getId(), o.getInterest(), o.getBankId());
        });
    }
}
