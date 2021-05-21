package com.bham.fsd.assignments.jabberclient.login;

import java.io.IOException;

import com.bham.fsd.assignments.jabberclient.JabberClient;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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

    private static final int MAX_USERNAME_LENGTH = 255;

    private final JabberClient client;
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
        registerButton.disableProperty().bind(Bindings.isEmpty(usernameTextField.textProperty()));

        signInButton.setOnMouseClicked(e -> handleSignInRequest(e));
        signInButton.disableProperty().bind(Bindings.isEmpty(usernameTextField.textProperty()));

        usernameTextField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > MAX_USERNAME_LENGTH) {
                change.setText(change.getText().substring(0, MAX_USERNAME_LENGTH - change.getControlText().length()));
            }

            return change;
        }));
    }

    /**
     * Instructs the client to register the current user if the specified mouse
     * event was raised using the primary mouse button.
     *
     * @param event the mouse event
     */
    private void handleRegistrationRequest(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            client.requestRegistration(usernameTextField.getText());
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
            client.requestSignIn(usernameTextField.getText());
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
