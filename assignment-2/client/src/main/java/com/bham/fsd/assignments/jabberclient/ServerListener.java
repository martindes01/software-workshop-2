package com.bham.fsd.assignments.jabberclient;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.bham.fsd.assignments.jabberserver.JabberMessage;

import javafx.application.Platform;

/**
 * The server listener class implements the Java runnable interface. It listens
 * for incoming responses from the server and handles them by calling the
 * relevant methods of the Jabber client controller.
 *
 * @author Martin de Spirlet
 */
public class ServerListener implements Runnable {

    private static final String SIGN_IN_RESPONSE_FAILURE = "unknown-user";
    private static final String SIGN_IN_RESPONSE_SUCCESS = "signedin";

    private static final String TIMELINE_RESPONSE = "timeline";
    private static final String USERS_RESPONSE = "users";

    private final JabberClient client;
    private final ObjectInputStream in;

    private int usernameIndex = 0;

    /**
     * Creates a new server listener for the specified client and object input
     * stream from the client to the server. The listener continues to run while the
     * client is connected to the server, or until an I/O exception occurs.
     *
     * @param client the client for which to create this server listener
     * @param in     the object input stream from the client to the server
     */
    public ServerListener(JabberClient client, ObjectInputStream in) {
        this.client = client;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (client.isConnected()) {
                JabberMessage response = (JabberMessage) in.readObject();

                switch (response.getMessage()) {
                    case SIGN_IN_RESPONSE_FAILURE:
                        usernameIndex++;
                        if (!client.isSignedIn()) {
                            Platform.runLater(() -> JabberDialogs.showSignInErrorDialog());
                        }
                        break;

                    case SIGN_IN_RESPONSE_SUCCESS:
                        if (!client.isSignedIn()) {
                            client.setSignedInUsername(usernameIndex);
                            Platform.runLater(() -> client.showDashboardView());
                        }
                        break;

                    case TIMELINE_RESPONSE:
                        if (client.isSignedIn()) {
                            Platform.runLater(() -> client.handleTimelineResponse(response.getData()));
                        }
                        break;

                    case USERS_RESPONSE:
                        if (client.isSignedIn()) {
                            Platform.runLater(() -> client.handleUsersResponse(response.getData()));
                        }
                        break;
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            Platform.runLater(() -> JabberDialogs.showCommunicationErrorDialog());
        }
    }

}
