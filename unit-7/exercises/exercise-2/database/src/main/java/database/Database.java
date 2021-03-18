package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database implements AutoCloseable {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    private static final String AFFORDABLE_ALBUM_TITLE_QUERY = "SELECT title FROM album WHERE price <= ?";
    private static final String ALBUM_TITLE_ATTRIBUTE = "title";

    private Connection connection;

    /**
     * Constructs a Database object and attempts to connect it to the Music
     * database.
     *
     * @throws SQLException if a database access error occurs
     */
    public Database() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Closes the connection between this Database object and the Music database.
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * Returns a list of the titles of albums whose prices are less than or equal to
     * the specified budget.
     *
     * @param budget the maximum that can be spent on an album
     * @return a list of the titles of albums whose prices are less than or equal to
     *         the specified budget
     */
    public List<String> getAffordableAlbumTitles(double budget) {
        List<String> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(AFFORDABLE_ALBUM_TITLE_QUERY)) {
            statement.setDouble(1, budget);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                result.add(results.getString(ALBUM_TITLE_ATTRIBUTE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
