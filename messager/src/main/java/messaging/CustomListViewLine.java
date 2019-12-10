package messaging;

public class CustomListViewLine<TypeReceived, TypeSent> {
	
	private TypeSent sent;
	private TypeReceived received;

	public CustomListViewLine(TypeSent sent, TypeReceived received) { this.sent = sent; this.received = received; }
	public TypeSent getSent() { return sent; }
	public TypeReceived getReceived() { return received; }
	public void setSent(TypeSent sent) { this.sent = sent; }
	public void setReceived(TypeReceived received) { this.received = received; }

	@Override
	public String toString() {
		return ((sent != null) ? sent.toString() : "no request") + "  -->  " + ((received != null) ? received.toString() : "waiting for loan reply...");
	}
}
