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
        final String QUERY = "SELECT useridA" + " FROM follows" + " WHERE useridB = ?";
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
        final String QUERY = "SELECT useridB" + " FROM follows" + " WHERE useridA = ?";
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
        final String QUERY = "SELECT username, jabtext" + " FROM likes" + " INNER JOIN jab USING (jabid)"
                + " INNER JOIN jabberuser ON (jab.userid = jabberuser.userid)" + " WHERE likes.userid = ?";
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

    /**
     * Returns an ArrayList whose elements are themselves ArrayLists that each
     * contain the username and text of a different Jab that is authored by a user
     * who the specified user is following.
     *
     * @param userid the user ID whose timeline to retrieve
     * @return an ArrayList of ArrayLists that each contain the username and text of
     *         a different Jab that is authored by a user who the specified user is
     *         following
     */
    public ArrayList<ArrayList<String>> getTimelineOfUser(int userid) {
        final String QUERY = "SELECT username, jabtext" + " FROM follows"
                + " INNER JOIN jabberuser ON (follows.useridB = jabberuser.userid)" + " INNER JOIN jab USING (userid)"
                + " WHERE useridA = ?";
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

    /**
     * Returns an ArrayList whose elements are themselves ArrayLists that each
     * contain a distinct pair of user IDs belonging to users who follow each other
     * mutually. In the case of a self-following user, a pair comprises two
     * identical user IDs. The result contains only one permutation of each pair.
     *
     * @return an ArrayList whose elements are themselves ArrayLists that each
     *         contain a distinct pair of user IDs belonging to users who follow
     *         each other mutually
     */
    public ArrayList<ArrayList<String>> getMutualFollowUserIDs() {
        final String QUERY = "SELECT DISTINCT f1.useridA, f1.useridB" + " FROM follows AS f1"
                + " INNER JOIN follows AS f2 ON (f1.useridA = f2.useridB AND f1.useridB = f2.useridA)"
                + " WHERE f1.useridA <= f1.useridB";
        final String ATTRIBUTE_USER_ID_A = "useridA";
        final String ATTRIBUTE_USER_ID_B = "useridB";

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try (ResultSet resultSet = conn.createStatement().executeQuery(QUERY)) {
            while (resultSet.next()) {
                ArrayList<String> pair = new ArrayList<>();

                pair.add(resultSet.getObject(ATTRIBUTE_USER_ID_A).toString());
                pair.add(resultSet.getObject(ATTRIBUTE_USER_ID_B).toString());

                result.add(pair);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Adds a user to the 'jabberuser' table with the specified username and email
     * address. The user is given a unique user ID.
     *
     * @param username the username of the new user
     * @param emailadd the email address of the new user
     */
    public void addUser(String username, String emailadd) {
        final String UPDATE = "INSERT INTO jabberuser" + " VALUES"
                + " ((SELECT MAX(userid) FROM jabberuser) + 1, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setString(1, username);
            statement.setString(2, emailadd);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a Jab to the 'jab' table with the specified text and a user ID
     * corresponding to the specified username. The user ID is that of the first
     * user found with the specified username. The Jab is given a unique Jab ID.
     *
     * @param username the username of the Jab author
     * @param jabtext  the text of the new Jab
     */
    public void addJab(String username, String jabtext) {
        final String UPDATE = "INSERT INTO jab" + " VALUES"
                + " ((SELECT MAX(jabid) FROM jab) + 1, (SELECT userid FROM jabberuser WHERE username = ? LIMIT 1), ?)";

        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setString(1, username);
            statement.setString(2, jabtext);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a follows relationship to the 'follows' table to indicates that the user
     * specified by 'userida' follows the user specified by 'useridb'.
     *
     * @param userida the user ID of the user following the user specified by
     *                'useridb'
     * @param useridb the user ID of the user being followed by the user specified
     *                by 'userida'
     */
    public void addFollower(int userida, int useridb) {
        final String UPDATE = "INSERT INTO follows" + " VALUES" + " (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setInt(1, userida);
            statement.setInt(2, useridb);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        System.out.println();

        System.out.println("Get user IDs of users who user 0 is following:");
        System.out.println(jabber.getFollowingUserIDs(0));
        System.out.println();

        System.out.println("Get username and text of Jabs that user 0 has liked:");
        System.out.println(jabber.getLikesOfUser(0));
        System.out.println();

        System.out.println("Get username and text of Jabs authored by users who user 0 is following:");
        System.out.println(jabber.getTimelineOfUser(0));
        System.out.println();

        System.out.println("Get distinct pairs of user IDs belonging to users who follow each other mutually:");
        System.out.println(jabber.getMutualFollowUserIDs());
        System.out.println();

        System.out.println("Adding user with username 'TheRealMarty' and email address 'marty@real.com'...");
        jabber.addUser("TheRealMarty", "marty@real.com");
        System.out.println();

        System.out.println("Adding Jab by user 'TheRealMarty' with text 'I'm a real boy!'...");
        jabber.addJab("TheRealMarty", "I'm a real boy!");
        System.out.println();

        System.out.println("Adding follow relationship user 0 follows user 13...");
        jabber.addFollower(0, 13);
        System.out.println();
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
