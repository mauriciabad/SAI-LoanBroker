package broker.gui;

import broker.model.BrokerRequest;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import messaging.Messager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
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

    private Gson gson = new Gson();

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
        messagerClient = new Messager("loanClient", "bank");
        messagerClient.setOnMessageReceieved(msg -> {
            try {
                String json = msg.getText();
                BrokerRequest brokerRequest = gson.fromJson(json, BrokerRequest.class);
                ListViewLine listViewLine = new ListViewLine(brokerRequest);
                this.lvLoanRequestReply.getItems().add(listViewLine);

            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        messagerClient.setOnMessageListUpdated(() -> {
            //logger.info("ReceivedMessages: " + messagerClient.getReceivedMessages());
            //logger.info("SentMessages: " + messagerClient.getSentMessages());
        });
    }
}
