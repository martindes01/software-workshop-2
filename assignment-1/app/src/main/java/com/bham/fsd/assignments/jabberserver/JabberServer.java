package com.bham.fsd.assignments.jabberserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
// import java.util.Collections;
// import java.util.HashSet;

// Martin de Spirlet 1785605

/**
 * JabberServer
 *
 * @author Martin de Spirlet
 */
public class JabberServer {

    private static String dbcommand = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static String db = "postgres";
    private static String pw = "password";

    private static Connection conn;

    /**
     * Provided static connection method to connect to the default 'postgres'
     * database with the password "password" and store the connection object in a
     * private static field.
     */
    public static void connectToDatabase() {
        try {
            conn = DriverManager.getConnection(dbcommand, db, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Provided static connection method to return the connection object set by a
     * call to 'JabberServer.connectToDatabase()'.
     *
     * @return the connection object if set by 'JabberServer.connectToDatabase()',
     *         null otherwise.
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * Provided default constructor to construct a new JabberServer.
     */
    public JabberServer() {

    }

    /**
     * Returns an ArrayList of the user IDs of users who are followers of the
     * specified user. These are the users who follow the specified user.
     *
     * @param userid the user ID of the user whose followers to retrieve
     * @return an ArrayList of the user IDs of users who follow the specified user
     */
    public ArrayList<String> getFollowerUserIDs(int userid) {
        final String QUERY = "SELECT useridA FROM follows WHERE useridB = ?";
        final String ATTRIBUTE_USER_ID = "useridA";

        ArrayList<String> result = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(QUERY)) {
            statement.setInt(1, userid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(resultSet.getObject(ATTRIBUTE_USER_ID).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Returns an ArrayList of the user IDs of users who the specified user is
     * following. These are the users who are followed by the specified user.
     *
     * @param userid the user ID of the user whose influencers to retrieve
     * @return an ArrayList of the user IDs of users who the specified user is
     *         following
     */
    public ArrayList<String> getFollowingUserIDs(int userid) {
        final String QUERY = "SELECT useridB FROM follows WHERE useridA = ?";
        final String ATTRIBUTE_USER_ID = "useridB";

        ArrayList<String> result = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(QUERY)) {
            statement.setInt(1, userid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(resultSet.getObject(ATTRIBUTE_USER_ID).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Returns an ArrayList whose elements are themselves ArrayLists that each
     * contain the username and text of a different Jab that the specified user has
     * liked.
     *
     * @param userid the user ID of the user whose liked Jabs to retrieve
     * @return an ArrayList of ArrayLists that each contain the username and text of
     *         a different Jab that the specified user has liked
     */
    public ArrayList<ArrayList<String>> getLikesOfUser(int userid) {
        final String QUERY = "SELECT username, jabtext FROM likes INNER JOIN jab USING (jabid) INNER JOIN jabberuser ON (jab.userid = jabberuser.userid) WHERE likes.userid = ?";
        final String ATTRIBUTE_USERNAME = "username";
        final String ATTRIBUTE_JAB_TEXT = "jabtext";

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(QUERY)) {
            statement.setInt(1, userid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ArrayList<String> jab = new ArrayList<>();

                    jab.add(resultSet.getObject(ATTRIBUTE_USERNAME).toString());
                    jab.add(resultSet.getObject(ATTRIBUTE_JAB_TEXT).toString());

                    result.add(jab);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<ArrayList<String>> getTimelineOfUser(int userid) {
        return null;
    }

    public ArrayList<ArrayList<String>> getMutualFollowUserIDs() {
        return null;
    }

    public void addUser(String username, String emailadd) {

    }

    public void addJab(String username, String jabtext) {

    }

    public void addFollower(int userida, int useridb) {

    }

    public void addLike(int userid, int jabid) {

    }

    public ArrayList<String> getUsersWithMostFollowers() {
        return null;
    }

    public static void main(String[] args) {
        JabberServer jabber = new JabberServer();

        JabberServer.connectToDatabase();

        // jabber.resetDatabase();

        System.out.println("Get user IDs of users who follow user 0:");
        System.out.println(jabber.getFollowerUserIDs(0));

        System.out.println("Get user IDs of users who user 0 is following:");
        System.out.println(jabber.getFollowingUserIDs(0));

        System.out.println("Get username and text of Jabs that user 0 has liked:");
        System.out.println(jabber.getLikesOfUser(0));
    }

    /*
     * Provided static utility method to print an ArrayList to standard output.
     */
    private static void print1(ArrayList<String> list) {
        for (String s : list) {
            System.out.print(s + " ");
        }
    }

    /*
     * Provided static utility method to print an ArrayList of ArrayLists to
     * standard output.
     */
    private static void print2(ArrayList<ArrayList<String>> list) {
        for (ArrayList<String> s : list) {
            print1(s);
            System.out.println();
        }
    }

    /**
     * Provided reset method to drop all Jabber tables from the database.
     */
    private void dropTables() {
        String[] commands = { "DROP TABLE jabberuser CASCADE;", "DROP TABLE jab CASCADE;",
                "DROP TABLE follows CASCADE;", "DROP TABLE likes CASCADE;" };

        for (String query : commands) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Provided reset method to execute an ArrayList of SQL update statements.
     */
    private void executeSQLUpdates(ArrayList<String> commands) {
        for (String query : commands) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Provided reset method to return an ArrayList of commands from an SQL file.
     */
    private ArrayList<String> loadSQL(String sqlfile) {
        ArrayList<String> commands = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(sqlfile + ".sql"));

            String command = "";
            String line = "";

            while ((line = reader.readLine()) != null) {
                if (line.contains(";")) {
                    command += line;
                    command = command.trim();
                    commands.add(command);
                    command = "";
                } else {
                    line = line.trim();
                    command += line + " ";
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commands;
    }

    /**
     * Provided reset method to reset the Jabber database. Requires 'jabberdef.sql'
     * and 'jabberdata.sql' to be present in the working directory.
     */
    public void resetDatabase() {
        dropTables();

        ArrayList<String> defs = loadSQL("jabberdef");
        ArrayList<String> data = loadSQL("jabberdata");

        executeSQLUpdates(defs);
        executeSQLUpdates(data);
    }

}
