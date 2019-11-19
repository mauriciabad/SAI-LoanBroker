package broker.model;

import java.util.Objects;

/**
 *
 * This class stores all information about a bank offer
 * as a response to a client loan request.
 */
public class BrokerReply {

        private String id;
        private double interest; // the interest that the bank offers for the requested loan
        private String bankID; // the unique quote identification of the bank which makes the offer

    public BrokerReply() {
        super();
        this.id = "";
        this.interest = 0;
        this.bankID = "";
    }
    public BrokerReply(String id, double interest, String quoteID) {
        super();
        this.id = id;
        this.interest = interest;
        this.bankID = quoteID;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankId) {
        this.bankID = bankId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrokerReply brokerReply = (BrokerReply) o;
        return id.equals(brokerReply.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return " interest="+String.valueOf(interest) + " quoteID="+String.valueOf(bankID);
    }
}
