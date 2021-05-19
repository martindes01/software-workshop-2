package com.bham.fsd.assignments.jabberclient.controls;

import java.io.IOException;

import com.bham.fsd.assignments.jabberclient.JabberClient;
import com.bham.fsd.assignments.jabberclient.models.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * The user cell controller class is responsible for handling updates to user
 * cells and sending user input to the Jabber client controller.
 *
 * @author Martin de Spirlet
 */
public class UserCellController {

    private static final String VIEW_FILENAME = "user-cell.fxml";

    private final JabberClient client;
    private final User user;
    private Parent view;

    @FXML
    private Button followButton;
    @FXML
    private Label usernameLabel;

    /**
     * Creates a new user cell controller for the specified client.
     *
     * @param client the client for which to create this user cell controller
     * @param user   the user for which this user cell controller is responsible
     */
    public UserCellController(JabberClient client, User user) {
        this.client = client;
        this.user = user;
    }

    @FXML
    private void initialize() {
        followButton.setOnMouseClicked(e -> handleFollowRequest(e));
        usernameLabel.setText(user.getUsername());
    }

    /**
     * Instructs the client to like the follow the selected user if the specified
     * mouse event was raised using the primary mouse button.
     *
     * @param event the mouse event
     */
    private void handleFollowRequest(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            client.requestFollow(user.getUsername());
        }
    }

    /**
     * Returns the view associated with this controller.
     *
     * @return the view associated with this controller
     * @throws IOException if an error occurs when loading the view
     */
    public Parent getView() throws IOException {
        if (view == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_FILENAME));
            loader.setController(this);
            view = loader.load();
        }

        return view;
    }

}
