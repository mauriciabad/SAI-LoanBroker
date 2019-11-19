package bank.gui;

import bank.model.BankInterestReply;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

class BankController implements Initializable {

    private final String bankId;

    @SuppressWarnings("unused")
    @FXML
    public ListView<ListViewLine> lvBankRequestReply;
    @SuppressWarnings("unused")
    @FXML
    public TextField tfInterest;

    public BankController(String queueName, String bankId){
        this.bankId = bankId;
        LoggerFactory.getLogger(getClass()).info("Created BankController with arguments [queueName="+queueName+"] and [bankId="+bankId+"]");
    }

    @SuppressWarnings("unused")
    @FXML
    public void btnSendBankInterestReplyClicked(){
        double interest = Double.parseDouble(tfInterest.getText());
        BankInterestReply bankInterestReply = new BankInterestReply(UUID.randomUUID().toString(), interest, bankId);

        ListViewLine listViewLine = lvBankRequestReply.getSelectionModel().getSelectedItem();
        if (listViewLine!= null){
            listViewLine.setBankInterestReply(bankInterestReply);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lvBankRequestReply.refresh();
                }
            });
         // @TODO send the bankInterestReply
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
