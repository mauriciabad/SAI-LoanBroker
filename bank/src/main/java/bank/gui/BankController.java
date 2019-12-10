package bank.gui;

import bank.model .BankInterestReply;
import bank.model.BankInterestRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import messaging.CustomListView;
import messaging.CustomListViewLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

class BankController implements Initializable {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private CustomListView<BankInterestRequest, BankInterestReply> customListView;

    private final String bankId;

    @SuppressWarnings("unused")
    @FXML
    public ListView<CustomListViewLine<BankInterestRequest, BankInterestReply>> listView;
    @SuppressWarnings("unused")
    @FXML
    public TextField tfInterest;

    private BankGatewayToBroker gateway = new BankGatewayToBroker();

    public BankController(String queueName, String bankId){
        this.bankId = bankId;
        LoggerFactory.getLogger(getClass()).info("Created BankController with arguments [queueName="+queueName+"] and [bankId="+bankId+"]");
    }

    @SuppressWarnings("unused")
    @FXML
    public void btnSendBankInterestReplyClicked(){
        double interest = Double.parseDouble(tfInterest.getText());
        BankInterestReply bankInterestReply = new BankInterestReply(UUID.randomUUID().toString(), interest, bankId);

        CustomListViewLine<BankInterestRequest, BankInterestReply> listViewLine = listView.getSelectionModel().getSelectedItem();
        if (listViewLine!= null){
            customListView.add(bankInterestReply, listViewLine.getReceived());
            gateway.reply(listViewLine.getReceived(), bankInterestReply);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customListView = new CustomListView<BankInterestRequest, BankInterestReply>(listView);

        gateway.setOnMessageReceived(msg -> {
            logger.info("messageReceived: " + msg);
            customListView.addReceived(msg);
        });
    }
}
