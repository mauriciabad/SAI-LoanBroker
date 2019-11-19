package broker.gui;


import broker.model.BrokerReply;
import broker.model.BrokerRequest;

/**
 * This class is an item/line for a ListViewLine. It makes it possible to put both BankInterestRequest and BankInterestReply object in one item in a ListViewLine.
 */
class ListViewLine {
	
	private BrokerRequest brokerRequest;
	private BrokerReply brokerReply;
	
	public ListViewLine(BrokerRequest brokerRequest) {
		setBrokerRequest(brokerRequest);
		setBrokerReply(null);
	}	
	
	public BrokerRequest getBrokerRequest() {
		return brokerRequest;
	}
	
	private void setBrokerRequest(BrokerRequest brokerRequest) {
		this.brokerRequest = brokerRequest;
	}

	
	public void setBrokerReply(BrokerReply brokerReply) {
		this.brokerReply = brokerReply;
	}

    /**
     * This method defines how one line is shown in the ListViewLine.
     * @return
     *  a) if BankInterestReply is null, then this item will be shown as "loanRequest.toString ---> waiting for loan reply..."
     *  b) if BankInterestReply is not null, then this item will be shown as "loanRequest.toString ---> loanReply.toString"
     */
	@Override
	public String toString() {
	   return brokerRequest.toString() + "  --->  " + ((brokerReply !=null)? brokerReply.toString():"waiting for loan reply...");
	}
	
}
