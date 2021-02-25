import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import circle.CircleUtils;

public class TCPServer {

    private static final int PORT = 8000;

    private static ServerSocket serverSocket;

    /**
     * Start the server.
     *
     * @return true if the server was started successfully, false otherwise
     */
    private static boolean startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            return serverSocket.isBound();
        } catch (Exception e) {
            System.out.println("Error starting server.");
            return false;
        }
    }

    /**
     * Handle incoming connections.
     *
     * @return false when an exception causes service to stop
     */
    private static boolean serve() {
        while (true) {
            try (Socket socket = serverSocket.accept();
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
                System.out.println("Connection accepted.");
                out.writeDouble(CircleUtils.calculateArea(in.readDouble()));
                System.out.println("Connection handled.");
            } catch (Exception e) {
                System.out.println("Error communicating with client.");
                return false;
            }
        }
    }

    /**
     * Stop the server.
     */
    private static void stopServer() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error stopping server.");
        }
    }

    public static void main(String[] args) {
        if (startServer()) {
            System.out.println("Listening on port " + PORT + "...");

            if (!serve()) {
                System.out.println("Stopping server...");
                stopServer();
                System.out.println("Server stopped.");
            }
        } else {
            System.out.println("Failed to start server.");
        }
    }

}
