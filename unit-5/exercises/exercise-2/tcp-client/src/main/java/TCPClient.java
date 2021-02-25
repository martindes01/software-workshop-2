import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import circle.CircleUtils;

public class TCPClient {

    private static final String HOST = "localhost";
    private static final int PORT = 8000;

    private static Socket socket;

    /**
     * Open a connection to the server.
     *
     * @return true if successful, false otherwise
     */
    private static boolean openConnection() {
        try {
            socket = new Socket(HOST, PORT);
            return socket.isConnected();
        } catch (Exception e) {
            System.out.println("Error creating socket.");
            return false;
        }
    }

    /**
     * Close the connection to the server.
     */
    private static void closeConnection() {
        try {
            socket.close();
        } catch (Exception e) {
            System.out.println("Error closing socket.");
        }
    }

    /**
     * Get the area of a circle with the specified radius from the server.
     *
     * @param radius the radius of the circle
     * @return the calculated area if successful, zero otherwise
     */
    private static double getAreaFromServer(double radius) {
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());) {
            out.writeDouble(radius);
            return in.readDouble();
        } catch (Exception e) {
            System.out.println("Error communicating with server.");
            return 0.0;
        }
    }

    public static void main(String[] args) {
        double radius = CircleUtils.getRadiusFromUser();

        if (openConnection()) {
            System.out.println("Area calculated by server: " + getAreaFromServer(radius));

            closeConnection();
        } else {
            System.out.println("Failed to open connection.");
        }

    }

}
