package loanclient.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import loanclient.model.LoanReply;
import loanclient.model.LoanRequest;
import messaging.CustomListView;
import messaging.CustomListViewLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class LoanClientController implements Initializable {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private CustomListView<LoanReply, LoanRequest> customListView;

    @FXML
    private TextField tfSsn;
    @FXML
    private TextField tfAmount;
    @FXML
    private TextField tfTime;
    @FXML
    private ListView<CustomListViewLine<LoanReply, LoanRequest>> listView;

    private LoanClientGatewayToBroker gateway;

    public LoanClientController(){}

    @FXML
    public void btnSendLoanRequestClicked(){
        // create the BankInterestRequest
        int ssn = Integer.parseInt(tfSsn.getText());
        int amount = Integer.parseInt(tfAmount.getText());
        int time = Integer.parseInt(tfTime.getText());
        LoanRequest loanRequest = new LoanRequest(UUID.randomUUID().toString(), ssn,amount,time);

        customListView.addSent(loanRequest);

        gateway.send(loanRequest);
        logger.info("Sent the loan request: " + loanRequest);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfSsn.setText("123456");
        tfAmount.setText("80000");
        tfTime.setText("30");

        customListView = new CustomListView<LoanReply, LoanRequest>(listView);

        gateway = new LoanClientGatewayToBroker();
        gateway.setOnMessageReplied((reqObj, replObj) -> {
            logger.info("messageReceived: " + replObj);
            customListView.add(replObj, reqObj);
        });
    }
}
