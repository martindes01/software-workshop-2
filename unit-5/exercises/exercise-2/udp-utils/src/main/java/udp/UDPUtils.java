package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class UDPUtils {

    /**
     * Sends a single packet with the specified data.
     *
     * @param data    the data to send
     * @param socket  the socket through which to send the data
     * @param address the address to which to send to the data
     * @return true if successful, false otherwise
     */
    public static boolean sendSinglePacket(byte[] data, DatagramSocket socket, SocketAddress address) {
        try {
            socket.send(new DatagramPacket(data, data.length, address));
            return true;
        } catch (Exception e) {
            System.out.println("Error sending packet.");
            return false;
        }
    }

    /**
     * Receives a single packet containing data of the specified length.
     *
     * @param length the length in bytes of data to receive
     * @param socket the socket through which to receive the packet
     * @return the packet received if successful, an empty packet otherwise
     */
    public static DatagramPacket receiveSinglePacket(int length, DatagramSocket socket) {
        DatagramPacket response = new DatagramPacket(new byte[length], length);

        try {
            socket.receive(response);
        } catch (Exception e) {
            System.out.println("Error receiving packet.");
        }

        return response;
    }

    /**
     * Returns a byte array that represents the specified double value.
     *
     * @param value the double value to convert
     * @return a byte array that represents the specified double value if
     *         successful, an empty byte array otherwise
     */
    public static byte[] getBytesFromDouble(double value) {
        try {
            byte[] result = new byte[Double.BYTES];
            ByteBuffer.wrap(result).putDouble(value);
            return result;
        } catch (Exception e) {
            System.out.println("Error writing value.");
            return new byte[Double.BYTES];
        }
    }

    /**
     * Returns the double value that is represented by the specified byte array.
     *
     * @param data the byte array to convert
     * @return the double value that is represented by the specified byte array if
     *         successful, zero otherwise
     */
    public static double getDoubleFromBytes(byte[] data) {
        try {
            return ByteBuffer.wrap(data).getDouble();
        } catch (Exception e) {
            System.out.println("Error reading value.");
            return 0.0;
        }
    }

}
