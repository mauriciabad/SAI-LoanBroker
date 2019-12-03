package messaging;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class ListViewLine {
	
	private Object req;
	private Object repl;

	public ListViewLine(Object req, Object repl) { this.req = req; this.repl = repl; }
	public Object getReq() { return req; }
	public Object getRepl() { return repl; }
	public void setReq(Object req) { this.req = req; }
	public void setRepl(Object repl) { this.repl = repl; }

	public static void addRepl(ListView<ListViewLine> listView, Object repl) {
		boolean found = false;
		/*
		for (int i = 0; i < listView.getItems().size() && !found; i++) {
			ListViewLine pair =  listView.getItems().get(i);
			if (pair.getRepl() != null && pair.getRepl() == repl) {
				pair.setRepl(repl);
				found = true;
			}
		}
		 */
		if(!found) {
			Platform.runLater(() -> {
				listView.getItems().add(new ListViewLine(null, repl));
				listView.refresh();
			});
		}
	}

	public static void addReq(ListView<ListViewLine> listView, Object req) {
		boolean found = false;
		/*
		for (int i = 0; i < listView.getItems().size() && !found; i++) {
			ListViewLine pair =  listView.getItems().get(i);
			if (pair.getReq() != null && pair.getReq() == req) {
				pair.setReq(req);
				found = true;
			}
		}
		*/
		if(!found) {
			Platform.runLater(() -> {
				listView.getItems().add(new ListViewLine(req, null));
				listView.refresh();
			});
		}
	}

	@Override
	public String toString() {
		return ((req != null) ? req.toString() : "no request") + "  -->  " + ((repl != null) ? repl.toString() : "waiting for loan reply...");
	}
}
