package bank.model;

import java.util.Objects;

/**
 *
 * This class stores all information about an request from a bank to offer
 * a loan to a specific client.
 */
@SuppressWarnings("unused")
public class BankInterestRequest {

    private String id;
    private int ssn;
    private int amount;
    private int time;
    private int credit;
    private int history;

    public BankInterestRequest() {
        super();
        this.id = "";
        this.ssn = 0;
        this.amount = 0;
        this.time = 0;
        this.credit = 0;
        this.history = 0;
    }

    public BankInterestRequest(String id, int ssn, int amount, int time, int credit, int history) {
        super();
        this.id = id;
        this.ssn = ssn;
        this.amount = amount;
        this.time = time;
        this.credit = credit;
        this.history = history;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return " ssn=" + ssn + " amount=" + amount + " time=" + time + " credit=" + credit + " history=" + history ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankInterestRequest that = (BankInterestRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
