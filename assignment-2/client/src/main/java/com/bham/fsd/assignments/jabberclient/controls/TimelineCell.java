package com.bham.fsd.assignments.jabberclient.controls;

import java.io.IOException;

import com.bham.fsd.assignments.jabberclient.JabberClient;
import com.bham.fsd.assignments.jabberclient.models.Jab;

import javafx.scene.control.ListCell;

/**
 * The timeline cell class is a custom control that extends from the JavaFX list
 * cell class. It is intended to provide a representation of an individual Jab
 * within a list view.
 *
 * @author Martin de Spirlet
 */
public class TimelineCell extends ListCell<Jab> {

    private final JabberClient client;

    /**
     * Creates a new timeline cell for the specified client.
     *
     * @param client the client for which to create this timeline cell
     */
    public TimelineCell(JabberClient client) {
        this.client = client;
    }

    @Override
    protected void updateItem(Jab item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || (item == null)) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                TimelineCellController controller = new TimelineCellController(client, item);
                setGraphic(controller.getView());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
