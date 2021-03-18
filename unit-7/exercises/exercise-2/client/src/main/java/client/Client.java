package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client implements AutoCloseable {

    private static final String HOST = "localhost";
    private static final int PORT = 8000;

    private static final double EXIT_BUDGET = 0.0;

    private Socket socket;

    /**
     * Constructs a client and attempts to connect it to the server.
     *
     * @throws IOException if an I/O error occurs when connecting the socket
     */
    public Client() throws IOException {
        socket = new Socket(HOST, PORT);
    }

    /**
     * Closes the connection between this client and the server.
     *
     * @throws IOException if an I/O error occurs when closing the socket
     */
    @Override
    public void close() throws IOException {
        socket.close();
    }

    /**
     * Gets a budget from the user. This is the maximum that can be spent on an
     * album.
     *
     * @return the budget
     */
    private double getBudget() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter a budget (" + EXIT_BUDGET + " to exit): ");
                return Double.parseDouble(sc.nextLine());
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Invalid input.");
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Error reading input.");
                return EXIT_BUDGET;
            }
        }
    }

    /**
     * Repeatedly asks the user for a budget and prints a list of the titles of
     * albums whose prices are less than or equal to the budget. The method
     * completes when the user enters a string that can be parsed as a double of
     * zero value.
     */
    private void printAffordableAlbumTitles() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            double budget;
            while ((budget = getBudget()) != EXIT_BUDGET) {
                out.println(budget);

                System.out.println("Affordable albums:");
                for (Object albumTitle : (List<?>) in.readObject()) {
                    System.out.println(albumTitle);
                }
            }
        } catch (Exception e) {
            System.out.println("Error communicating with server.");
        }
    }

    public static void main(String[] args) {
        try (Client client = new Client()) {
            client.printAffordableAlbumTitles();
        } catch (Exception e) {
            System.out.println("Failed to connect to server.");
        }
    }

}
