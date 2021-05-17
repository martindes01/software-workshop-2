package com.bham.fsd.assignments.jabberserver;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private final Socket socket;
    private final JabberDatabase database;

    private String username;

    /**
     * Constructs a task to handle a connection on the specified client socket.
     *
     * @param socket the client socket
     */
    public ClientConnection(Socket socket) {
        this.socket = socket;
        this.database = new JabberDatabase();
    }

    /**
     * Reads incoming Jabber protocol requests and sends the appropriate Jabber
     * protocol responses.
     */
    @Override
    public void run() {
        try (socket) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (!socket.isClosed()) {
                out.writeObject(processRequest((JabberMessage) in.readObject()));
                out.flush();
            }
        } catch (EOFException e) {
            // This exception is thrown when the end of the input stream is reached
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Processes the specified Jabber protocol request and returns the appropriate
     * Jabber protocol response.
     *
     * @param request the Jabber protocol request
     * @return the Jabber protocol response
     * @throws InterruptedException if the request is undefined or a sign-out
     *                              request
     */
    private JabberMessage processRequest(JabberMessage request) throws InterruptedException {
        System.out.println(request.getMessage());

        String[] tokens = request.getMessage().split(" ", 2);

        switch (tokens[0]) {
            case "signin":
                if (database.getUserID(tokens[1]) == -1) {
                    return new JabberMessage("unknown-user");
                } else {
                    username = tokens[1];
                    return new JabberMessage("signedin");
                }

            case "register":
                username = tokens[1];
                database.addUser(username, username + "@email.com");
                return new JabberMessage("signedin");

            case "timeline":
                return new JabberMessage("timeline", database.getTimelineOfUserEx(username));

            case "users":
                return new JabberMessage("users", database.getUsersNotFollowed(database.getUserID(username)));

            case "post":
                database.addJab(username, tokens[1]);
                return new JabberMessage("posted");

            case "like":
                database.addLike(database.getUserID(username), Integer.parseInt(tokens[1]));
                return new JabberMessage("posted");

            case "follow":
                database.addFollower(database.getUserID(username), tokens[1]);
                return new JabberMessage("posted");

            default:
                throw new InterruptedException();
        }
    }

}
