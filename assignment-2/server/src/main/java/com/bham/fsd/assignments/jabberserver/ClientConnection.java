package com.bham.fsd.assignments.jabberserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.bham.fsd.assignments.jabberutils.JabberMessage;

public class ClientConnection implements Runnable {

    private final Socket socket;
    private final JabberDatabase database;

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

            out.writeObject(processRequest((JabberMessage) in.readObject()));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Processes the specified Jabber protocol request and returns the appropriate
     * Jabber protocol response.
     *
     * @param request the Jabber protocol request
     * @return the Jabber protocol response
     */
    private JabberMessage processRequest(JabberMessage request) {
        return new JabberMessage(null);
    }

}
