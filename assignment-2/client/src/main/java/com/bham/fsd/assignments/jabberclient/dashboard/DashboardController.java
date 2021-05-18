package com.bham.fsd.assignments.jabberclient.dashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.bham.fsd.assignments.jabberclient.JabberClient;
import com.bham.fsd.assignments.jabberclient.controls.TimelineCell;
import com.bham.fsd.assignments.jabberclient.controls.UserCell;
import com.bham.fsd.assignments.jabberclient.models.Jab;
import com.bham.fsd.assignments.jabberclient.models.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * The dashboard controller class is responsible for handling updates to the
 * dashboard view and sending user input to the Jabber client controller.
 *
 * @author Martin de Spirlet
 */
public class DashboardController {

    private static final String VIEW_TITLE = "Jabber Dashboard";
    private static final String VIEW_FILENAME = "dashboard.fxml";

    private final JabberClient client;
    private Parent view;

    @FXML
    private Button postButton;
    @FXML
    private Button signOutButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ListView<User> userListView;
    @FXML
    private ListView<Jab> timelineListView;
    @FXML
    private TextArea jabTextArea;

    private boolean timelineUpdateEnabled = true;
    private boolean usersUpdateEnabled = true;

    /**
     * Creates a new dashboard view controller for the specified client.
     *
     * @param client the client for which to create this dashboard controller
     */
    public DashboardController(JabberClient client) {
        this.client = client;
    }

    @FXML
    private void initialize() {
        postButton.setOnMouseClicked(e -> handlePostRequest(e));
        signOutButton.setOnMouseClicked(e -> handleSignOutRequest(e));

        welcomeLabel.setText("Welcome, " + client.getSignedInUsername() + "!");

        timelineListView.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> timelineUpdateEnabled = false);
        timelineListView.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> timelineUpdateEnabled = true);
        timelineListView.setCellFactory(value -> new TimelineCell(client));

        userListView.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> usersUpdateEnabled = false);
        userListView.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> usersUpdateEnabled = true);
        userListView.setCellFactory(value -> new UserCell(client));
    }

    /**
     * Instructs the client to post the provided text as a Jab if the specified
     * mouse event was raised using the primary mouse button.
     *
     * @param event the mouse event
     */
    private void handlePostRequest(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            client.requestPost(jabTextArea.getText());
            jabTextArea.setText("");
        }
    }

    /**
     * Instructs the client to sign the current user out if the specified mouse
     * event was raised using the primary mouse button.
     *
     * @param event the mouse event
     */
    private void handleSignOutRequest(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            client.requestSignOut();
        }
    }

    /**
     * Displays the specified server response data in the timeline view. It is
     * assumed that the response data are in the correct format.
     *
     * @param data the timeline data
     */
    public void showTimeline(ArrayList<ArrayList<String>> data) {
        if (timelineUpdateEnabled && (data != null)) {
            timelineListView.getItems().setAll(data.stream().map(e -> new Jab(e)).sorted(Comparator.reverseOrder())
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    /**
     * Displays the specified server response data in the users view. It is assumed
     * that the data are in the correct format.
     *
     * @param data the users data
     */
    public void showUsers(ArrayList<ArrayList<String>> data) {
        if (usersUpdateEnabled && (data != null)) {
            userListView.getItems().setAll(
                    data.stream().map(e -> new User(e)).sorted().collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    /**
     * Returns the title of the view associated with this controller.
     *
     * @return the title of the view associated with this controller
     */
    public String getTitle() {
        return VIEW_TITLE;
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
