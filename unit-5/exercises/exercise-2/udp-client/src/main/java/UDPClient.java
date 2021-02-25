import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import circle.CircleUtils;
import udp.UDPUtils;

public class UDPClient {

    private static final String HOST = "localhost";
    private static final int PORT = 8000;

    private static DatagramSocket socket;
    private static SocketAddress address;

    /**
     * Binds a socket and sets an address to be used by packets.
     *
     * @return true if successful, false otherwise
     */
    private static boolean bindSocket() {
        try {
            socket = new DatagramSocket();
            address = new InetSocketAddress(HOST, PORT);
            return socket.isBound();
        } catch (Exception e) {
            System.out.println("Error creating socket.");
            return false;
        }
    }

    /**
     * Closes the socket.
     */
    private static void closeSocket() {
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
        if (UDPUtils.sendSinglePacket(UDPUtils.getBytesFromDouble(radius), socket, address)) {
            return UDPUtils.getDoubleFromBytes(UDPUtils.receiveSinglePacket(Double.BYTES, socket).getData());
        } else {
            System.out.println("Error communicating with server.");
            return 0.0;
        }
    }

    public static void main(String[] args) {
        double radius = CircleUtils.getRadiusFromUser();

        if (bindSocket()) {
            System.out.println("Area calculated by server: " + getAreaFromServer(radius));

            closeSocket();
        } else {
            System.out.println("Failed to bind socket.");
        }
    }

}
