package com.bham.fsd.assignments.jabberclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.bham.fsd.assignments.jabberserver.JabberMessage;

import javafx.application.Platform;

/**
 * The server listener class implements the Java runnable interface. It listens
 * for incoming responses from the server and handles them by calling the
 * relevant methods of the Jabber client controller.
 */
public class ServerListener implements Runnable {

    private static final String SIGN_IN_RESPONSE_FAILURE = "unknown-user";
    private static final String SIGN_IN_RESPONSE_SUCCESS = "signedin";

    public Socket socket;
    public ObjectInputStream in;

    /**
     * Creates a new server listener for the specified socket and the specified
     * object input stream that wraps the input stream of the socket.
     *
     * @param socket the socket connected to the server
     * @param in     the object input stream that wraps the input stream of the
     *               socket
     */
    public ServerListener(Socket socket, ObjectInputStream in) {
        this.socket = socket;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                JabberMessage response = (JabberMessage) in.readObject();
                System.out.println(response.getMessage());

                switch (response.getMessage()) {
                    case SIGN_IN_RESPONSE_FAILURE:
                        Platform.runLater(() -> JabberDialogs.showSignInErrorDialog());
                        break;

                    case SIGN_IN_RESPONSE_SUCCESS:
                        // TODO
                        break;
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            Platform.runLater(() -> JabberDialogs.showCommunicationErrorDialog());
        }
    }

}
