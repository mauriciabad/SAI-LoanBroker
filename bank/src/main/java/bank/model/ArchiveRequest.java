package bank.model;

import java.util.Objects;

/**
 *
 * This class stores all information about an request from a bank to offer
 * a loan to a specific client.
 */
@SuppressWarnings("unused")
public class ArchiveRequest {

    private int amount;
    private int time;
    private double interest;
    private String bank;
    private int SSN;

    public ArchiveRequest() {
        super();
        this.amount = 0;
        this.time = 0;
        this.interest = 0;
        this.bank = "";
    }

    public ArchiveRequest(int SSN, int amount, String bank, double interest, int time) {
        super();
        this.amount = amount;
        this.time = time;
        this.interest = interest;
        this.bank = bank;
        this.SSN = SSN;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    @SuppressWarnings("unused")
    public double getInterest() {
        return interest;
    }

    @SuppressWarnings("unused")
    public void setInterest(double interest) {
        this.interest = interest;
    }

    @SuppressWarnings("unused")
    public String getBank() {
        return bank;
    }

    @SuppressWarnings("unused")
    public void setBank(String bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return " amount=" + amount + " time=" + time + "quote=" + this.bank + " interest=" + this.interest;
    }
}
