package com.bham.fsd.assignments.jabberclient.controls;

import java.io.IOException;

import com.bham.fsd.assignments.jabberclient.JabberClient;
import com.bham.fsd.assignments.jabberclient.models.User;

import javafx.scene.control.ListCell;

/**
 * The user cell class is a custom control that extends from the JavaFX list
 * cell class. It is intended to provide a representation of an individual user
 * to be followed within a list view.
 *
 * @author Martin de Spirlet
 */
public class UserCell extends ListCell<User> {

    private final JabberClient client;

    /**
     * Creates a new user cell for the specified client.
     *
     * @param client the client for which to create this user cell
     */
    public UserCell(JabberClient client) {
        this.client = client;
    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || (item == null)) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                UserCellController controller = new UserCellController(client, item);
                setGraphic(controller.getView());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
