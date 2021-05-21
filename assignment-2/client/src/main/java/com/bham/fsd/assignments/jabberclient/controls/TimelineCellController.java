package com.bham.fsd.assignments.jabberclient.controls;

import java.io.IOException;

import com.bham.fsd.assignments.jabberclient.JabberClient;
import com.bham.fsd.assignments.jabberclient.models.Jab;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * The timeline cell controller class is responsible for handling updates to
 * timeline cells and sending user input to the Jabber client controller.
 *
 * @author Martin de Spirlet
 */
public class TimelineCellController {

    private static final String VIEW_FILENAME = "timeline-cell.fxml";
    private static final String STYLESHEET_FILENAME = "timeline-cell.css";

    private static final String LIKE_ICON_CLASS_NAME = "like-icon";

    private final JabberClient client;
    private final Jab jab;
    private Parent view;

    @FXML
    private Button likeButton;
    @FXML
    private HBox root;
    @FXML
    private Label likesLabel;
    @FXML
    private Label textLabel;

    /**
     * Creates a new timeline cell controller for the specified client and Jab.
     *
     * @param client the client for which to create this timeline cell controller
     * @param client the Jab for which this timeline cell controller is responsible
     */
    public TimelineCellController(JabberClient client, Jab jab) {
        this.client = client;
        this.jab = jab;
    }

    @FXML
    private void initialize() {
        Node likeIcon = new Region();
        likeIcon.getStyleClass().add(LIKE_ICON_CLASS_NAME);
        likeIcon.setPickOnBounds(false);

        likeButton.setOnMouseClicked(e -> handleLikeRequest(e));
        likeButton.setAlignment(Pos.CENTER);
        likeButton.setBackground(null);
        likeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        likeButton.setGraphic(likeIcon);
        likeButton.setPickOnBounds(false);

        likesLabel.setText(String.valueOf(jab.getLikes()));
        textLabel.setText(jab.toString());
    }

    /**
     * Instructs the client to like the selected Jab if the specified mouse event
     * was raised using the primary mouse button.
     *
     * @param event the mouse event
     */
    private void handleLikeRequest(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            client.requestLike(jab.getJabID());
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
            view.getStylesheets().add(getClass().getResource(STYLESHEET_FILENAME).toExternalForm());
        }

        return view;
    }

}
