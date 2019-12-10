package messaging;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class CustomListView<TypeReceived, TypeSent> {

    ListView<CustomListViewLine<TypeReceived, TypeSent>> listView;

    public CustomListView(ListView<CustomListViewLine<TypeReceived, TypeSent>> listView) {
        this.listView = listView;
    }

    public void add(TypeSent sent, TypeReceived received){
        boolean found = false;

		for (int i = 0; i < listView.getItems().size() && !found; i++) {
			CustomListViewLine<TypeReceived, TypeSent> pair =  listView.getItems().get(i);
			if (pair.getReceived().equals(received) || pair.getSent().equals(sent)) {
				pair.setReceived(received);
				pair.setSent(sent);
				found = true;
			}
		}

        if(!found) {
            Platform.runLater(() -> {
                listView.getItems().add(new CustomListViewLine<TypeReceived, TypeSent>(sent, received));
                listView.refresh();
            });
        }else{
            Platform.runLater(() -> {
                listView.refresh();
            });
        }
    }

    public void addReceived(TypeReceived received) { this.add(null, received); }
    public void addSent(TypeSent sent) { this.add(sent, null); }
}
