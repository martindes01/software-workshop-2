package com.bham.fsd.assignments.jabberclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bham.fsd.assignments.jabberclient.dashboard.DashboardController;
import com.bham.fsd.assignments.jabberclient.login.LoginController;
import com.bham.fsd.assignments.jabberserver.JabberMessage;

import javafx.application.Application;
import javafx.application.Platform;
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

    private static final String REGISTRATION_REQUEST_PREFIX = "register ";
    private static final String SIGN_IN_REQUEST_PREFIX = "signin ";
    private static final String SIGN_OUT_REQUEST_MESSAGE = "signout";

    private static final String TIMELINE_REQUEST_MESSAGE = "timeline";
    private static final String USERS_REQUEST_MESSAGE = "users";

    private static final String LIKE_REQUEST_PREFIX = "like ";
    private static final String FOLLOW_REQUEST_PREFIX = "follow ";
    private static final String POST_REQUEST_PREFIX = "post ";

    private Stage stage;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private DashboardController dashboardController;
    private LoginController loginController;

    private List<Entry<String, Boolean>> usernameEntries = new ArrayList<>();
    private String signedInUsername = "";

    private boolean signedIn = false;

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
    private void sendRequest(JabberMessage request) {
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            JabberDialogs.showCommunicationErrorDialog();
        }
    }

    /**
     * Sets the signed-in state of this client, sets the signed-in username saved by
     * this client to that of the username entry at the specified index in the list
     * of all username entries for which this client has requested registration or
     * sign-in, displays the registration success dialog if this is a username for
     * which registration was requested, and displays the dashboard view.
     *
     * @param index the index of the signed-in username entry in the list of all
     *              username entries for which this client has requested
     *              registration or sign-in
     */
    public void handleSignInSuccess(int index) {
        signedIn = true;

        Map.Entry<String, Boolean> usernameEntry = usernameEntries.get(index);
        signedInUsername = usernameEntry.getKey();
        if (usernameEntry.getValue()) {
            JabberDialogs.showRegistrationSuccessDialog();
        }

        showDashboardView();
    }

    /**
     * Instructs the dashboard controller to update the timeline view using the
     * specified server response data.
     *
     * @param data the timeline data
     */
    public void handleTimelineResponse(ArrayList<ArrayList<String>> data) {
        if (dashboardController != null) {
            dashboardController.showTimeline(data);
        }
    }

    /**
     * Instructs the dashboard controller to update the users view using the
     * specified server response data.
     *
     * @param data the users data
     */
    public void handleUsersResponse(ArrayList<ArrayList<String>> data) {
        if (dashboardController != null) {
            dashboardController.showUsers(data);
        }
    }

    /**
     * Displays the dashboard view.
     */
    public void showDashboardView() {
        dashboardController = new DashboardController(this);

        try {
            stage.setScene(new Scene(dashboardController.getView()));
            stage.setTitle(dashboardController.getTitle());
            stage.setResizable(true);
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the login view.
     */
    public void showLoginView() {
        loginController = new LoginController(this);

        try {
            stage.setScene(new Scene(loginController.getView()));
            stage.setTitle(loginController.getTitle());
            stage.setResizable(false);
            stage.sizeToScene();
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
    public void requestRegistration(String username) {
        String strippedUsername = username.strip();
        usernameEntries.add(new AbstractMap.SimpleEntry<>(strippedUsername, true));
        sendRequest(new JabberMessage(REGISTRATION_REQUEST_PREFIX + username.strip()));
    }

    /**
     * Sends a sign-in request with the specified username to the server.
     *
     * @param username the username of the user to be signed in
     */
    public void requestSignIn(String username) {
        String strippedUsername = username.strip();
        usernameEntries.add(new AbstractMap.SimpleEntry<>(strippedUsername, false));
        sendRequest(new JabberMessage(SIGN_IN_REQUEST_PREFIX + strippedUsername));
    }

    /**
     * Sends a sign-out request to the server and exits the application.
     */
    public void requestSignOut() {
        sendRequest(new JabberMessage(SIGN_OUT_REQUEST_MESSAGE));
        signedIn = false;
        Platform.exit();
    }

    /**
     * Sends a timeline request to the server.
     */
    public void requestTimeline() {
        sendRequest(new JabberMessage(TIMELINE_REQUEST_MESSAGE));
    }

    /**
     * Sends a user request to the server.
     */
    public void requestUsers() {
        sendRequest(new JabberMessage(USERS_REQUEST_MESSAGE));
    }

    /**
     * Sends a like request with the specified Jab ID to the server.
     *
     * @param jabID the ID of the Jab to be liked
     */
    public void requestLike(int jabID) {
        sendRequest(new JabberMessage(LIKE_REQUEST_PREFIX + jabID));
    }

    /**
     * Sends a follow request with the specified username to the server.
     *
     * @param username the username of the user to be followed
     */
    public void requestFollow(String username) {
        sendRequest(new JabberMessage(FOLLOW_REQUEST_PREFIX + username.strip()));
    }

    /**
     * Sends a post request with the specified Jab text to the server.
     *
     * @param jabText the text of the Jab to be posted
     */
    public void requestPost(String jabText) {
        sendRequest(new JabberMessage(POST_REQUEST_PREFIX + jabText.strip()));
    }

    /**
     * Returns the username of the user most recently signed in by this client.
     *
     * @return the username of the user most recently signed in by this client
     */
    public String getSignedInUsername() {
        return signedInUsername;
    }

    /**
     * Returns the connection state of this client to the server, which is true if
     * the socket between the client and server is open, false otherwise.
     *
     * @return true if the socket between the client and server is open, false
     *         otherwise
     */
    public boolean isConnected() {
        if (socket == null) {
            return false;
        } else {
            return !socket.isClosed();
        }
    }

    /**
     * Returns the signed-in state of this client, which is true if a user is
     * currently signed into this client, false otherwise.
     *
     * @return true if a user is currently signed into this client, false otherwise
     */
    public boolean isSignedIn() {
        return signedIn;
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setOnShown(e -> {
            stage.toFront();
            stage.requestFocus();
        });

        if (connectToServer()) {
            showLoginView();

            new Thread(new ServerListener(this, in)).start();
            new Thread(new ServerPoller(this)).start();
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
