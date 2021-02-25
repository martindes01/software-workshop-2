import java.net.DatagramPacket;
import java.net.DatagramSocket;

import circle.CircleUtils;
import udp.UDPUtils;

public class UDPServer {

    private static final int PORT = 8000;

    private static DatagramSocket socket;

    /**
     * Start the server.
     *
     * @return true if the server was started successfully, false otherwise
     */
    private static boolean startServer() {
        try {
            socket = new DatagramSocket(PORT);
            return socket.isBound();
        } catch (Exception e) {
            System.out.println("Error starting server.");
            return false;
        }
    }

    /**
     * Handle incoming requests.
     *
     * @return false when an exception causes service to stop
     */
    private static boolean serve() {
        while (true) {
            DatagramPacket request = UDPUtils.receiveSinglePacket(Double.BYTES, socket);
            System.out.println("Request received.");

            if (UDPUtils.sendSinglePacket(
                    UDPUtils.getBytesFromDouble(
                            CircleUtils.calculateArea(UDPUtils.getDoubleFromBytes(request.getData()))),
                    socket, request.getSocketAddress())) {
                System.out.println("Response sent.");
            } else {
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
            socket.close();
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
