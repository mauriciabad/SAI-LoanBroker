package messaging;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class CustomListView<TypeReceived, TypeSent> {

    ListView<CustomListViewLine<TypeReceived, TypeSent>> listView;

    public CustomListView(ListView<CustomListViewLine<TypeReceived, TypeSent>> listView) {
        this.listView = listView;
    }

    public void add(TypeReceived received, TypeSent sent){
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
                listView.getItems().add(new CustomListViewLine<TypeReceived, TypeSent>(null, received));
                listView.refresh();
            });
        }else{
            Platform.runLater(() -> {
                listView.refresh();
            });
        }
    }

    public void addReceived(TypeReceived received) {
        boolean found = false;

        /*
		for (int i = 0; i < listView.getItems().size() && !found; i++) {
			CustomListViewLine<TypeReceived, TypeSent> pair =  listView.getItems().get(i);
			if (pair.getReceived() == null && pair.getSent().getId().equals(received.getId())) {
				pair.setReceived(received);
				found = true;
			}
		}
		*/

        if(!found) {
            Platform.runLater(() -> {
                listView.getItems().add(new CustomListViewLine<TypeReceived, TypeSent>(null, received));
                listView.refresh();
            });
        }
    }

    public void addSent(TypeSent sent) {
        boolean found = false;

        /*
		for (int i = 0; i < listView.getItems().size() && !found; i++) {
			CustomListViewLine<TypeReceived, TypeSent> pair =  listView.getItems().get(i);
			if (pair.getSent() == null && pair.getReceived().getId().equals(sent.getId())) {
				pair.setSent(sent);
				found = true;
			}
		}
		*/

        if(!found) {
            Platform.runLater(() -> {
                listView.getItems().add(new CustomListViewLine<TypeReceived, TypeSent>(sent, null));
                listView.refresh();
            });
        }
    }
}
