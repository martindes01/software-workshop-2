package com.bham.fsd.assignments.jabberclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.bham.fsd.assignments.jabberclient.login.LoginController;
import com.bham.fsd.assignments.jabberserver.JabberMessage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Jabber client class is the entry point and controller of the Jabber
 * client application. It is responsible for interacting with the Jabber server
 * and the Jabber client views.
 *
 * @author Martin de Spirlet
 */
public class JabberClient extends Application {

    private static final String HOST = "localhost";
    private static final int PORT = 44444;

    private static final String SIGN_IN_REQUEST_PREFIX = "signin ";
    private static final String REGISTRATION_REQUEST_PREFIX = "register ";

    private Stage stage;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private LoginController loginController;

    /**
     * Creates a socket, connects it to the server, and wraps its input and output
     * streams in object input and object output streams, respectively.
     *
     * @return true if the socket is connected and its streams are created
     *         successfully, false otherwise
     */
    private boolean connectToServer() {
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            JabberDialogs.showConnectionErrorDialog();
            return false;
        }
    }

    /**
     * Closes the socket connected to the server.
     */
    private void disconnectFromServer() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the specified request to the server.
     *
     * @param request the request to send
     */
    public void sendRequest(JabberMessage request) {
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            JabberDialogs.showCommunicationErrorDialog();
        }
    }

    /**
     * Displays the login view.
     */
    public void showLoginView() {
        try {
            stage.setScene(new Scene(loginController.getView()));
            stage.setTitle(loginController.getTitle());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a registration request with the specified username to the server.
     *
     * @param username the username of the user to be registered
     */
    public void register(String username) {
        sendRequest(new JabberMessage(REGISTRATION_REQUEST_PREFIX + username.strip()));
    }

    /**
     * Sends a sign-in request with the specified username to the server.
     *
     * @param username the username of the user to be signed in
     */
    public void signIn(String username) {
        sendRequest(new JabberMessage(SIGN_IN_REQUEST_PREFIX + username.strip()));
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setOnShown(e -> {
            stage.toFront();
            stage.requestFocus();
        });

        if (connectToServer()) {
            loginController = new LoginController(this);
            showLoginView();
        }
    }

    @Override
    public void stop() {
        disconnectFromServer();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
