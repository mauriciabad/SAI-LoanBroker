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

    private BrokerGatewayToLoanClient gatewayClient = new BrokerGatewayToLoanClient();
    private BrokerGatewayToBank gatewayBank = new BrokerGatewayToBank();

    public BrokerController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customListView = new CustomListView<BankInterestReply, LoanRequest>(listView);

        gatewayClient.setOnMessageReceived(original -> {
            logger.info("messageReceived: " + original);
            customListView.addSent(original);

            BankInterestRequest sent = new BankInterestRequest(original.getId(), original.getAmount(), original.getTime());
            gatewayBank.send(sent, (reply) -> {
                logger.info("messageReplied: " + reply);
                customListView.add(original, reply);

                gatewayClient.reply(original, new LoanReply(reply.getId(), reply.getInterest(), reply.getBankId()));
            });
        });
    }
}
