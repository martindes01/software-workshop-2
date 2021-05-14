package com.bham.fsd.assignments.jabberserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JabberServer implements Runnable {

    private static final int PORT = 44444;
    private static final int TIMEOUT = 300;

    private final ServerSocket serverSocket;

    /**
     * Constructs a new server for the Jabber application.
     *
     * @throws IOException if an I/O error occurs when opening the socket
     */
    public JabberServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(TIMEOUT);
    }

    /**
     * Listens for incoming client connections and handles them in separate tasks.
     */
    @Override
    public void run() {
        System.out.println("Listening on port " + PORT + "...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (true) {
            try {
                executorService.execute(new ClientConnection(serverSocket.accept()));

                System.out.println("Incoming connection accepted.");
            } catch (SocketTimeoutException e) {
                // Socket timeout expired
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Thread(new JabberServer()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
