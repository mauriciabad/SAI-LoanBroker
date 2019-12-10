package broker.gui;

public class CreditAgencyRequest {
    private int ssn;

    public CreditAgencyRequest() {}

    public CreditAgencyRequest(int ssn) {
        this.ssn = ssn;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }
}
