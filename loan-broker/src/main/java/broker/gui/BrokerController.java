package broker.gui;

import bank.model.BankInterestReply;
import bank.model.BankInterestRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import loanclient.model.LoanReply;
import loanclient.model.LoanRequest;
import messaging.CustomListView;
import messaging.CustomListViewLine;
import messaging.Messager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BrokerController implements Initializable {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private CustomListView<BankInterestReply, LoanRequest> customListView;

    @FXML
    private TextField tfSsn;
    @FXML
    private TextField tfAmount;
    @FXML
    private TextField tfTime;
    @FXML
    private ListView<CustomListViewLine<BankInterestReply, LoanRequest>> listView;

    private Messager<LoanRequest, LoanReply> messagerClient;
    private Messager<BankInterestReply, BankInterestRequest> messagerBank;

    public BrokerController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customListView = new CustomListView<BankInterestReply, LoanRequest>(listView);

        messagerClient = new Messager<LoanRequest, LoanReply>("Broker->Client", "Client->Broker", LoanRequest.class, LoanReply.class);
        messagerClient.setOnMessageReceived(msg -> {
            logger.info("messageReceived: " + msg);
            customListView.addSent(msg);
            messagerBank.send(new BankInterestRequest(msg.getId(), msg.getAmount(), msg.getTime()));
        });

        messagerBank = new Messager<BankInterestReply, BankInterestRequest>("Broker->Bank", "Bank->Broker", BankInterestReply.class, BankInterestRequest.class);
        messagerBank.setOnMessageReceived(msg -> {
            logger.info("messageReceived: " + msg);
            customListView.addReceived(msg);
            messagerClient.send(new LoanReply(msg.getId(), msg.getInterest(), msg.getBankId()));
        });
    }
}
