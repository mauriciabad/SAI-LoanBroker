package broker.gui;

public class CreditAgencyReply {
    private int creditScore;
    private int history;

    public CreditAgencyReply() {}

    public CreditAgencyReply(int creditScore, int history) {
        this.creditScore = creditScore;
        this.history = history;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }
}
