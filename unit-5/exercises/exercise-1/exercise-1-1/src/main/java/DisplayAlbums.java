import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

public class DisplayAlbums {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    private static final int TIMEOUT = 30;

    private static final String ALBUM_QUERY = "SELECT title, label, price FROM album;";

    private static Connection connection;

    /**
     * Attempt to open a connection to the database.
     *
     * @return true if the connection is valid, false otherwise
     */
    private static boolean openConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            return connection.isValid(TIMEOUT);
        } catch (ClassNotFoundException e) {
            System.out.println("Database driver not found.");
            return false;
        } catch (SQLTimeoutException e) {
            System.out.println("Connection timed out.");
            return false;
        } catch (SQLException e) {
            System.out.println("Error accessing database.");
            return false;
        }
    }

    /**
     * Closes the connection to the database.
     */
    private static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error accessing database.");
        }
    }

    /**
     * Executes the specified SQL query. A valid connection must first be opened.
     *
     * @param query the SQL query to execute
     * @return the result of the query if successful, null otherwise
     */
    private static ResultSet executeQuery(String query) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeQuery(query);
        } catch (SQLTimeoutException e) {
            System.out.println("Connection timed out.");
            return null;
        } catch (SQLException e) {
            System.out.println("Error accessing database.");
            return null;
        }
    }

    /**
     * Prints the specified results.
     *
     * @param resultSet the results to print
     */
    private static void printResultSet(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                System.out.println("No records.");
            } else {
                do {
                    System.out.println("Title: " + resultSet.getString("title") + ", Label: "
                    + resultSet.getString("label") + ", Price: " + resultSet.getString("price"));
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println("Error accessing results.");
        }
    }

    /**
     * Discards the specified results.
     *
     * @param resultSet the results to discard.
     */
    private static void discardResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error accessing database.");
        }
    }

    public static void main(String[] args) {
        // Attempt to open a connection to the database
        if (openConnection()) {
            // Execute the album query
            ResultSet resultSet = executeQuery(ALBUM_QUERY);

            // Print and discard the results of the query if it was successful
            if (resultSet == null) {
                System.out.println("Failed to execute query.");
            } else {
                printResultSet(resultSet);
                discardResultSet(resultSet);
            }

            // Close the connection to the database
            closeConnection();
        } else {
            System.out.println("Failed to open connection.");
        }
    }

}
