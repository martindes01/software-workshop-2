package com.bham.fsd.assignments.jabberclient;

/**
 * The server poller class implements the Java runnable interface. It regularly
 * sends update requests to the server.
 *
 * @author Martin de Spirlet
 */
public class ServerPoller implements Runnable {

    private static final long INTERVAL = 1000;

    private final JabberClient client;

    /**
     * Creates a new server poller for the specified client. The poller continues to
     * run while the client is connected to the server, or until it is interrupted.
     *
     * @param client the client for which to create this server poller
     */
    public ServerPoller(JabberClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (client.isConnected()) {
                if (client.isSignedIn()) {
                    client.requestTimeline();
                    client.requestUsers();
                }

                Thread.sleep(INTERVAL);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
