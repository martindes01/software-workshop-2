package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import database.Database;

public class ServerTask implements Runnable {

    private final Socket socket;

    /**
     * Constructs a command to handle a connection from the specified socket.
     *
     * @param socket the socket whose connection to handle
     */
    public ServerTask(Socket socket) {
        this.socket = socket;
    }

    /**
     * Receives budgets from the client and responds with lists of the titles of
     * albums whose prices are less than or equal to each budget.
     */
    @Override
    public void run() {
        try (socket; Database database = new Database()) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String budget;
            while ((budget = in.readLine()) != null) {
                List<String> albumTitles = database.getAffordableAlbumTitles(Double.parseDouble(budget));

                out.writeObject(albumTitles);
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to database.");
        } catch (Exception e) {
            System.out.println("Error communicating with client.");
        }
    }

}
