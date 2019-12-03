package bank.gui;

import bank.model.BankInterestReply;
import bank.model.BankInterestRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import messaging.ListViewLine;
import messaging.Messager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

class BankController implements Initializable {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private final String bankId;

    @SuppressWarnings("unused")
    @FXML
    public ListView<ListViewLine> listView;
    @SuppressWarnings("unused")
    @FXML
    public TextField tfInterest;

    private Messager messager;

    public BankController(String queueName, String bankId){
        this.bankId = bankId;
        LoggerFactory.getLogger(getClass()).info("Created BankController with arguments [queueName="+queueName+"] and [bankId="+bankId+"]");
    }

    @SuppressWarnings("unused")
    @FXML
    public void btnSendBankInterestReplyClicked(){
        double interest = Double.parseDouble(tfInterest.getText());
        BankInterestReply bankInterestReply = new BankInterestReply(UUID.randomUUID().toString(), interest, bankId);

        ListViewLine listViewLine = listView.getSelectionModel().getSelectedItem();
        if (listViewLine!= null){
            listViewLine.setRepl(bankInterestReply);
            Platform.runLater(() -> listView.refresh());
            messager.send(bankInterestReply);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messager = new Messager("Broker->Bank", BankInterestRequest.class, "Bank->Broker", BankInterestReply.class);
        messager.setOnMessageReceieved(msg -> {
            logger.info("messageReceived: " + msg);
            ListViewLine.addReq(listView, msg);
        });
        messager.setOnMessageListUpdated(() -> {
            //logger.info("ReceivedMessages: " + messager.getReceivedMessages());
            //logger.info("SentMessages: " + messager.getSentMessages());
        });
    }
}
