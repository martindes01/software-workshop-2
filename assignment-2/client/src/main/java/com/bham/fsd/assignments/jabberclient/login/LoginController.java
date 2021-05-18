package com.bham.fsd.assignments.jabberclient.login;

import java.io.IOException;

import com.bham.fsd.assignments.jabberclient.JabberClient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * The login controller class is responsible for handling updates to the login
 * view and sending user input to the Jabber client controller.
 *
 * @author Martin de Spirlet
 */
public class LoginController {

    private static final String VIEW_TITLE = "Jabber Login";
    private static final String VIEW_FILENAME = "login.fxml";

    private static final String SIGN_IN_ERROR_TITLE = "Sign-in error";
    private static final String SIGN_IN_ERROR_HEADER_TEXT = "Username not found";
    private static final String SIGN_IN_ERROR_CONTENT_TEXT = "The server was unable to find this username in the database.\nPlease try again.";

    private JabberClient client;
    private Parent view;

    @FXML
    private Button registerButton;
    @FXML
    private Button signInButton;
    @FXML
    private TextField usernameTextField;

    /**
     * Creates a new login controller for the specified client.
     *
     * @param client the client for which to create this login controller
     */
    public LoginController(JabberClient client) {
        this.client = client;
    }

    @FXML
    private void initialize() {
        registerButton.setOnMouseClicked(e -> handleRegistrationRequest(e));
        signInButton.setOnMouseClicked(e -> handleSignInRequest(e));
    }

    /**
     * Instructs the client to register the current user if the specified mouse
     * event was raised using the primary mouse button.
     *
     * @param event the mouse event
     */
    private void handleRegistrationRequest(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            client.register(usernameTextField.getText());
        }
    }

    /**
     * Instructs the client to sign the current user in if the specified mouse event
     * was raised using the primary mouse button.
     *
     * @param event the mouse event
     */
    private void handleSignInRequest(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            client.signIn(usernameTextField.getText());
        }
    }

    /**
     * Displays a modal error dialog that informs the user that the provided
     * username was not found by the server in the database.
     */
    public void showSignInErrorDialog() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(SIGN_IN_ERROR_TITLE);
        alert.setHeaderText(SIGN_IN_ERROR_HEADER_TEXT);
        alert.setContentText(SIGN_IN_ERROR_CONTENT_TEXT);
        alert.showAndWait();
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
