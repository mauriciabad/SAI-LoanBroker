package messaging;

import javafx.scene.control.ListView;

public class ListViewLine {
	
	private Object req;
	private Object repl = null;

	public ListViewLine(Object req) { this.req = req; }
	public Object getReq() { return req; }
	public Object getRepl() { return repl; }
	public void setReq(Object req) { this.req = req; }
	public void setRepl(Object repl) { this.repl = repl; }

	public static void setRepl(ListView<ListViewLine> listView, Object request) {
		for (int i = 0; i < listView.getItems().size(); i++) {
			ListViewLine pair =  listView.getItems().get(i);
			if (pair.getReq() != null && pair.getReq() == request) {
				pair.setRepl(request);
				return;
			}
		}
	}

	@Override
	public String toString() {
		return req.toString() + "  -->  " + ((repl != null) ? repl.toString() : "waiting for loan reply...");
	}
}
