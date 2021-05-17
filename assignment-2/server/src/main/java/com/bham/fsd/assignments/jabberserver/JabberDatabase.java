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
import java.util.Collections;

public class JabberDatabase {

    private static String dbcommand = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static String db = "postgres";
    private static String pw = "";

    private static Connection conn;

    /**
     * Provided default constructor that connects to the database.
     */
    public JabberDatabase() {
        connectToDatabase();

        // resetDatabase();
    }

    /**
     * Provided static connection method to connect to the default 'postgres'
     * database and store the connection object in a private static field.
     */
    public static void connectToDatabase() {
        try {
            conn = DriverManager.getConnection(dbcommand, db, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Provided static connection method to return the connection to the database.
     *
     * @return the connection to the database.
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * Returns an ArrayList of the user IDs of users who are followers of the
     * specified user. These are the users who follow the specified user.
     *
     * @param userid the user ID of the user whose followers to retrieve
     * @return an ArrayList of the user IDs of users who follow the specified user
     */
    public ArrayList<String> getFollowerUserIDs(int userid) {

        ArrayList<String> ret = new ArrayList<String>();

        try {
            PreparedStatement stmt = conn.prepareStatement("select userida from follows where useridb = ?");

            stmt.setInt(1, userid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getObject("userida").toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
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
        ArrayList<String> ret = new ArrayList<String>();

        try {
            PreparedStatement stmt = conn.prepareStatement("select useridb from follows where userida = ?");

            stmt.setInt(1, userid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getObject("useridb").toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
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
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "select f1.userida, f1.useridb from follows f1 inner join follows f2 on f1.userida = f2.useridb and f1.useridb = f2.userida");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ArrayList<String> r = new ArrayList<String>();
                r.add(rs.getObject("userida").toString());
                r.add(rs.getObject("useridb").toString());
                ret.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return removeDuplicates(ret);
    }

    /**
     * Returns an ArrayList whose elements are themselves ArrayLists that each
     * contain one username for all the users who are not followed by the specified
     * user.
     *
     * @param userid the user ID of the user whose recommendations to retrieve
     * @return an ArrayList whose elements are themselves ArrayLists that each
     *         contain one username for all the users who are not followed by the
     *         specified user
     */
    public ArrayList<ArrayList<String>> getUsersNotFollowed(int userid) {
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "select username from jabberuser where userid not in (select useridb from follows where userida = ?)");

            stmt.setInt(1, userid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ArrayList<String> r = new ArrayList<String>();
                r.add(rs.getObject("username").toString());
                ret.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
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
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "select username, jabtext from jab natural join jabberuser where jabid in (select jabid from likes where userid = ?)");

            stmt.setInt(1, userid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ArrayList<String> r = new ArrayList<String>();
                r.add(rs.getObject("username").toString());
                r.add(rs.getObject("jabtext").toString());
                ret.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Returns an ArrayList whose elements are themselves ArrayLists that each
     * contain the username and text of a different Jab that the specified user has
     * liked.
     *
     * @param userid the username of the user whose liked Jabs to retrieve
     * @return an ArrayList of ArrayLists that each contain the username and text of
     *         a different Jab that the specified user has liked
     */
    @Deprecated
    public ArrayList<ArrayList<String>> getTimelineOfUser(String username) {
        int userid = this.getUserID(username);

        if (userid >= 0) {
            return getTimelineOfUser(userid);
        }

        return null;
    }

    /**
     * Returns an ArrayList whose elements are themselves ArrayLists that each
     * contain the username, text, ID and number of likes of a different Jab that
     * the specified user has liked.
     *
     * @param userid the username of the user whose liked Jabs to retrieve
     * @return an ArrayList of ArrayLists that each contain the username and text of
     *         a different Jab that the specified user has liked
     */
    public ArrayList<ArrayList<String>> getTimelineOfUserEx(String username) {
        int userid = this.getUserID(username);

        if (userid >= 0) {
            return getTimelineOfUserEx(userid);
        }

        return null;
    }

    /**
     * Returns an ArrayList whose elements are themselves ArrayLists that each
     * contain the username and text of a different Jab that is authored by a user
     * who the specified user is following.
     *
     * @param userid the user ID of the user whose timeline to retrieve
     * @return an ArrayList of ArrayLists that each contain the username and text of
     *         a different Jab that is authored by a user who the specified user is
     *         following
     */
    @Deprecated
    public ArrayList<ArrayList<String>> getTimelineOfUser(int userid) {
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "select username, jabtext from jab natural join jabberuser where userid in (select useridb from follows where userida = ?)");

            stmt.setInt(1, userid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ArrayList<String> r = new ArrayList<String>();
                r.add(rs.getObject("username").toString());
                r.add(rs.getObject("jabtext").toString());
                ret.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Returns an ArrayList whose elements are themselves ArrayLists that each
     * contain the username, text, ID and number of likes of a different Jab that
     * the specified user has liked.
     *
     * @param userid the user ID of the user whose liked Jabs to retrieve
     * @return an ArrayList of ArrayLists that each contain the username and text of
     *         a different Jab that the specified user has liked
     */
    public ArrayList<ArrayList<String>> getTimelineOfUserEx(int userid) {
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "select username, jabtext, jabid, (select count(jabid) from likes A where A.jabid = B.jabid group by A.jabid) as jabcount from jab B natural join jabberuser where userid in (select useridb from follows where userida = ?)");

            stmt.setInt(1, userid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ArrayList<String> r = new ArrayList<String>();
                r.add(rs.getObject("username").toString());
                r.add(rs.getObject("jabtext").toString());
                r.add(rs.getObject("jabid").toString());
                if (rs.getObject("jabcount") != null) {
                    r.add(rs.getObject("jabcount").toString());
                } else
                    r.add("0");
                ret.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
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
        int userid = getUserID(username);
        int jabid = getNextJabID();

        try {
            PreparedStatement stmt = conn.prepareStatement("insert into jab (values (?,?,?));");

            stmt.setInt(1, jabid);
            stmt.setInt(2, userid);
            stmt.setString(3, jabtext);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a user to the 'jabberuser' table with the specified username and email
     * address. The user is given a unique user ID.
     *
     * @param username the username of the new user
     * @param emailadd the email address of the new user
     */
    public void addUser(String username, String emailadd) {
        int newid = getNextUserID();

        try {
            PreparedStatement stmt = conn.prepareStatement("insert into jabberuser (values(?,?,?))");

            stmt.setInt(1, newid);
            stmt.setString(2, username);
            stmt.setString(3, emailadd);

            stmt.executeUpdate();

            addFollower(newid, newid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a follows relationship to the 'follows' table to indicate that the user
     * specified by 'userida' follows the user specified by 'useridb'.
     *
     * @param userida the user ID of the user following the user specified by
     *                'useridb'
     * @param useridb the user ID of the user being followed by the user specified
     *                by 'userida'
     */
    public void addFollower(int userida, int useridb) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into follows (values(?,?))");

            stmt.setInt(1, userida);
            stmt.setInt(2, useridb);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a follows relationship to the 'follows' table to indicate that the user
     * specified by 'userida' follows the user specified by 'username'.
     *
     * @param userida  the user ID of the user following the user specified by
     *                 'username'
     * @param username the username of the user being followed by the user specified
     *                 by 'userida'
     */
    public void addFollower(int userida, String username) {
        int useridb = this.getUserID(username);

        if (useridb < 0) {
            return;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("insert into follows (values(?,?))");

            stmt.setInt(1, userida);
            stmt.setInt(2, useridb);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a likes relationship to the 'likes' table to indicate that the specified
     * user likes the specified Jab.
     *
     * @param userid the user ID of the user who likes the specified Jab
     * @param jabid  the Jab ID of the Jab liked by the specified user
     */
    public void addLike(int userid, int jabid) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into likes (values(?,?))");

            stmt.setInt(1, userid);
            stmt.setInt(2, jabid);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an ArrayList of the user IDs of all users with the current greatest
     * number of followers.
     *
     * @return an ArrayList of the user IDs of all users with the current greatest
     *         number of followers
     */
    public ArrayList<String> getUsersWithMostFollowers() {
        ArrayList<String> ret = new ArrayList<String>();

        String query = "select useridb from follows group by useridb having count (useridb) >= all (select count(useridb) from follows group by useridb order by count(useridb));";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getObject("useridb").toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Returns the next available Jab ID.
     *
     * @return the next available Jab ID.
     */
    private int getNextJabID() {
        int maxid = -1;

        try {
            PreparedStatement stmt = conn.prepareStatement("select max(jabid) from jab");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                maxid = rs.getInt(1); // only one result.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (maxid < 0) {
            return maxid;
        }

        return maxid + 1;
    }

    /**
     * Returns the user ID of the user with the specified username.
     *
     * @param username the username of the user whose ID to retrieve.
     * @return the user ID of the user with the specified username.
     */
    public int getUserID(String username) {
        int ret = -1;

        try {
            PreparedStatement stmt = conn.prepareStatement("select userid from jabberuser where username = ?");
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ret = rs.getInt(1); // only one result anyway.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;

    }

    /**
     * Returns the next available user ID.
     *
     * @return the next available user ID.
     */
    private int getNextUserID() {
        String query = "select max(userid) from jabberuser";

        int maxid = -1;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                maxid = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (maxid < 0) {
            return maxid;
        }

        return maxid + 1;
    }

    /**
     * Returns a new ArrayList of the distinct ArrayLists in the specified ArrayList
     * of ArrayLists. The result contains only one permutation of each nested list.
     *
     * @param list the ArrayList whose duplicate ArrayLists to remove
     * @return a new ArrayList of the distinct ArrayLists in the specified ArrayList
     *         of ArrayLists
     */
    private ArrayList<ArrayList<String>> removeDuplicates(ArrayList<ArrayList<String>> list) {
        for (ArrayList<String> l : list) {
            Collections.sort(l);
        }

        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

        ret.add(list.get(0));

        for (ArrayList<String> l : list) {

            if (!ret.contains(l)) {
                ret.add(l);
            }
        }

        return ret;
    }

    /**
     * Provided static utility method to print an ArrayList to standard output.
     */
    private static void print1(ArrayList<String> list) {
        for (String s : list) {
            System.out.print(s + " ");
        }
    }

    /**
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
