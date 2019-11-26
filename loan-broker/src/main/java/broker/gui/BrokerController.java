package broker.gui;

import bank.model.BankInterestRequest;
import broker.model.BrokerRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import loanclient.model.LoanRequest;
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
    private ListView<ListViewLine> lvLoanRequestReply;

    private Messager messagerClient;
    private Messager messagerBank;

    public BrokerController(){
    }

    /**
     * This method returns the line of lvMessages which contains the given loan request.
     * @param request BankInterestRequest for which the line of lvMessages should be found and returned
     * @return The ListViewLine line of lvMessages which contains the given request
     */
    private ListViewLine getRequestReply(BrokerRequest request) {

        for (int i = 0; i < lvLoanRequestReply.getItems().size(); i++) {
            ListViewLine rr =  lvLoanRequestReply.getItems().get(i);
            if (rr.getBrokerRequest() != null && rr.getBrokerRequest() == request) {
                return rr;
            }
        }

        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messagerClient = new Messager("Client->Broker", LoanRequest.class, "Broker->Bank", BankInterestRequest.class);
        messagerClient.setOnMessageReceieved(msg -> {
            logger.info("messageReceieved: " + msg);
            // ListViewLine listViewLine = new ListViewLine((BrokerRequest) msg);
            // this.lvLoanRequestReply.getItems().add(listViewLine);
        });
        messagerClient.setOnMessageListUpdated(() -> {
            //logger.info("ReceivedMessages: " + messagerClient.getReceivedMessages());
            //logger.info("SentMessages: " + messagerClient.getSentMessages());
        });
        messagerClient.enableAutoRedirect((objFrom) -> {
            LoanRequest o = (LoanRequest) objFrom;
            return new BankInterestRequest(o.getId(), o.getAmount(), o.getTime());
        });
    }
}
