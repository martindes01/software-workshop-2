package com.bham.fsd.assignments.jabberclient;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The Jabber dialog class provides static methods for displaying common dialogs
 * required by the Jabber client application. Each dialog is instantiated only
 * when first required, and is cached for subsequent calls.
 *
 * @author Martin de Spirlet
 */
public class JabberDialogs {

    private static final String COMMUNICATION_ERROR_TITLE = "Communication error";
    private static final String COMMUNICATION_ERROR_HEADER_TEXT = "Error communicating with server";
    private static final String COMMUNICATION_ERROR_CONTENT_TEXT = "The current request has been cancelled.";

    private static final String CONNECTION_ERROR_TITLE = "Connection error";
    private static final String CONNECTION_ERROR_HEADER_TEXT = "Failed to connect to server";
    private static final String CONNECTION_ERROR_CONTENT_TEXT = "The server is currently not accepting connections.";

    private static final String SIGN_IN_ERROR_TITLE = "Sign-in error";
    private static final String SIGN_IN_ERROR_HEADER_TEXT = "Username not found";
    private static final String SIGN_IN_ERROR_CONTENT_TEXT = "The server was unable to find this username in the database.\nPlease try again.";

    private static Alert communicationErrorDialog;
    private static Alert connectionErrorDialog;
    private static Alert signInErrorDialog;

    /**
     * Displays a modal error dialog that informs the user that a communication
     * error occurred between the client and the server.
     */
    public static void showCommunicationErrorDialog() {
        if (communicationErrorDialog == null) {
            Alert communicationErrorDialog = new Alert(AlertType.ERROR);
            communicationErrorDialog.setTitle(COMMUNICATION_ERROR_TITLE);
            communicationErrorDialog.setHeaderText(COMMUNICATION_ERROR_HEADER_TEXT);
            communicationErrorDialog.setContentText(COMMUNICATION_ERROR_CONTENT_TEXT);
        }

        communicationErrorDialog.showAndWait();
    }

    /**
     * Displays a modal error dialog that informs the user that the client failed to
     * open a connection to the server.
     */
    public static void showConnectionErrorDialog() {
        if (connectionErrorDialog == null) {
            Alert connectionErrorDialog = new Alert(AlertType.ERROR);
            connectionErrorDialog.setTitle(CONNECTION_ERROR_TITLE);
            connectionErrorDialog.setHeaderText(CONNECTION_ERROR_HEADER_TEXT);
            connectionErrorDialog.setContentText(CONNECTION_ERROR_CONTENT_TEXT);
        }

        connectionErrorDialog.showAndWait();
    }

    /**
     * Displays a modal error dialog that informs the user that the provided
     * username was not found by the server in the database.
     */
    public static void showSignInErrorDialog() {
        if (signInErrorDialog == null) {
            signInErrorDialog = new Alert(AlertType.ERROR);
            signInErrorDialog.setTitle(SIGN_IN_ERROR_TITLE);
            signInErrorDialog.setHeaderText(SIGN_IN_ERROR_HEADER_TEXT);
            signInErrorDialog.setContentText(SIGN_IN_ERROR_CONTENT_TEXT);
        }

        signInErrorDialog.showAndWait();
    }

}
