package com.bham.fsd.assignments.jabberserver;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class JabberServerTest {

    private static JabberServer jabber;

    /**
     * Asserts that the expected and actual ArrayLists have the same size and
     * contents. If they do not, this assertion will fail with the specified failure
     * message. Neither ArrayList may be false.
     *
     * @param expected the expected ArrayList
     * @param actual   the actual ArrayList
     * @param message  the failure message
     */
    private void assertArrayListEquals(ArrayList<?> expected, ArrayList<?> actual, String message) {
        assertTrue((actual.size() == expected.size()) && actual.containsAll(expected) && expected.containsAll(actual),
                message + " ==> expected: <" + expected + "> but was: <" + actual + ">");
    }

    @BeforeAll
    public void init() {
        jabber = new JabberServer();

        JabberServer.connectToDatabase();

        jabber.resetDatabase();
    }

    @Test
    @Order(1)
    public void testGetFollowerUserIDs() {
        ArrayList<String> expected;
        ArrayList<String> actual;

        expected = new ArrayList<>(Arrays.asList("1", "2", "6", "7", "11", "12"));
        actual = jabber.getFollowerUserIDs(0);
        assertArrayListEquals(expected, actual, "getFollowerUserIDs(0)");

        expected = new ArrayList<>(Arrays.asList("1", "11"));
        actual = jabber.getFollowerUserIDs(2);
        assertArrayListEquals(expected, actual, "getFollowerUserIDs(2)");
    }

    @Test
    @Order(2)
    public void testGetFollowingUserIDs() {
        ArrayList<String> expected;
        ArrayList<String> actual;

        expected = new ArrayList<>(Arrays.asList("2", "3", "0", "12"));
        actual = jabber.getFollowingUserIDs(1);
        assertArrayListEquals(expected, actual, "getFollowingUserIDs(1)");

        expected = new ArrayList<>(Arrays.asList("2", "0", "12"));
        actual = jabber.getFollowingUserIDs(11);
        assertArrayListEquals(expected, actual, "getFollowingUserIDs(11)");
    }

    @Test
    @Order(3)
    public void testGetLikesOfUser() {
        ArrayList<ArrayList<String>> expected;
        ArrayList<ArrayList<String>> actual;

        expected = new ArrayList<>();
        expected.add(new ArrayList<>(Arrays.asList("kim", "uokhun")));
        expected.add(new ArrayList<>(Arrays.asList("HackerTDog", "WOOF!")));
        expected.add(new ArrayList<>(Arrays.asList("HackerTDog", "WOOF!!!")));

        actual = jabber.getLikesOfUser(0);

        assertArrayListEquals(expected, actual, "getLikesOfUser(0)");

        expected = new ArrayList<>();
        expected.add(new ArrayList<>(Arrays.asList("donaldJtrump", "MAGA!")));
        expected.add(new ArrayList<>(Arrays.asList("solesurvivor", "Mmm, roasted mirelurk for lunch.")));

        actual = jabber.getLikesOfUser(10);

        assertArrayListEquals(expected, actual, "getLikesOfUser(10)");
    }

    @Test
    @Order(4)
    public void testGetTimelineOfUser() {
        ArrayList<ArrayList<String>> expected;
        ArrayList<ArrayList<String>> actual;

        expected = new ArrayList<>();
        expected.add(new ArrayList<>(Arrays.asList("kim", "uokhun")));
        expected.add(new ArrayList<>(Arrays.asList("cyborg", "anybody watching that Gumball show?")));
        expected.add(new ArrayList<>(Arrays.asList("icebear", "ice bear says We Bare Bears is the best show")));

        actual = jabber.getTimelineOfUser(3);

        assertArrayListEquals(expected, actual, "getTimelineOfUser(3)");

        expected = new ArrayList<>();
        expected.add(
                new ArrayList<>(Arrays.asList("DavidBowie", "My version of The Man Who Sold The World is way better")));
        expected.add(new ArrayList<>(Arrays.asList("donaldJtrump", "MAGA!")));
        expected.add(new ArrayList<>(Arrays.asList("donaldJtrump", "SAD!")));
        expected.add(new ArrayList<>(Arrays.asList("donaldJtrump", "covfefe")));
        expected.add(new ArrayList<>(Arrays.asList("edballs", "Ed Balls")));

        actual = jabber.getTimelineOfUser(11);

        assertArrayListEquals(expected, actual, "getTimelineOfUser(11)");
    }

    @Test
    @Order(5)
    public void testGetMutualFollowUserIDs() {
        ArrayList<ArrayList<String>> expected;
        ArrayList<ArrayList<String>> actual;

        expected = new ArrayList<>();
        expected.add(new ArrayList<>(Arrays.asList("0", "12")));
        expected.add(new ArrayList<>(Arrays.asList("0", "11")));
        expected.add(new ArrayList<>(Arrays.asList("0", "6")));
        expected.add(new ArrayList<>(Arrays.asList("0", "7")));
        expected.add(new ArrayList<>(Arrays.asList("1", "3")));
        expected.add(new ArrayList<>(Arrays.asList("3", "7")));
        expected.add(new ArrayList<>(Arrays.asList("11", "12")));

        actual = jabber.getMutualFollowUserIDs();

        assertArrayListEquals(expected, actual, "getMutualFollowUserIDs()");
    }

    @Test
    @Order(6)
    public void testAddUser() {
        jabber.addUser("Ten", "johnsmith@tardis.org");
        jabber.addUser("MartydaPirate", "therealmarty@dmail.com");

        ArrayList<String> expected;
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try (PreparedStatement ps = JabberServer.getConnection().prepareStatement("SELECT * FROM jabberuser");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new ArrayList<>(Arrays.asList(rs.getObject("userid").toString(),
                        rs.getObject("username").toString(), rs.getObject("emailadd").toString())));
            }

            expected = new ArrayList<>(Arrays.asList("13", "Ten", "johnsmith@tardis.org"));
            assertTrue(result.contains(expected), "addUser(\"Ten\", \"johnsmith@tardis.org\")");

            expected = new ArrayList<>(Arrays.asList("14", "MartydaPirate", "therealmarty@dmail.com"));
            assertTrue(result.contains(expected), "addUser(\"MartydaPirate\", \"therealmarty@dmail.com\")");
        } catch (Exception e) {
            fail("addUser(username, emailadd)", e);
        }
    }

    @Test
    @Order(7)
    public void testAddJab() {
        jabber.addJab("Ten", "I don't want to go");
        jabber.addJab("edballs", "oops");

        ArrayList<String> expected;
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try (PreparedStatement ps = JabberServer.getConnection().prepareStatement("SELECT * FROM jab");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new ArrayList<>(Arrays.asList(rs.getObject("jabid").toString(),
                        rs.getObject("userid").toString(), rs.getObject("jabtext").toString())));
            }

            expected = new ArrayList<>(Arrays.asList("12", "13", "I don't want to go"));
            assertTrue(result.contains(expected), "addJab(\"Ten\", \"I don't want to go\")");

            expected = new ArrayList<>(Arrays.asList("13", "12", "oops"));
            assertTrue(result.contains(expected), "addJab(\"edballs\", \"oops\")");
        } catch (Exception e) {
            fail("addJab(username, jabtext)", e);
        }
    }

    @Test
    @Order(8)
    public void testAddFollower() {
        jabber.addFollower(0, 1);
        jabber.addFollower(12, 13);
        jabber.addFollower(14, 3);

        ArrayList<String> expected;
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try (PreparedStatement ps = JabberServer.getConnection().prepareStatement("SELECT * FROM follows");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new ArrayList<>(
                        Arrays.asList(rs.getObject("useridA").toString(), rs.getObject("useridB").toString())));
            }

            expected = new ArrayList<>(Arrays.asList("0", "1"));
            assertTrue(result.contains(expected), "addFollower(0, 1)");

            expected = new ArrayList<>(Arrays.asList("12", "13"));
            assertTrue(result.contains(expected), "addFollower(12, 13)");

            expected = new ArrayList<>(Arrays.asList("14", "3"));
            assertTrue(result.contains(expected), "addFollower(14, 3)");
        } catch (Exception e) {
            fail("addFollower(userida, useridb)", e);
        }
    }

    @Test
    @Order(9)
    public void testAddLike() {
        jabber.addLike(13, 12);
        jabber.addLike(8, 9);

        ArrayList<String> expected;
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try (PreparedStatement ps = JabberServer.getConnection().prepareStatement("SELECT * FROM likes");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new ArrayList<>(
                        Arrays.asList(rs.getObject("userid").toString(), rs.getObject("jabid").toString())));
            }

            expected = new ArrayList<>(Arrays.asList("13", "12"));
            assertTrue(result.contains(expected), "addLike(13, 12)");

            expected = new ArrayList<>(Arrays.asList("8", "9"));
            assertTrue(result.contains(expected), "addLike(8, 9)");
        } catch (Exception e) {
            fail("addFollower(userid, jabid)", e);
        }
    }

    @Test
    @Order(10)
    public void testGetUsersWithMostFollowers() {
        ArrayList<String> expected;
        ArrayList<String> actual;

        expected = new ArrayList<>(Arrays.asList("0"));
        actual = jabber.getUsersWithMostFollowers();
        assertArrayListEquals(expected, actual, "getUsersWithMostFollowers() with single expected result");

        jabber.addFollower(0, 13);
        jabber.addFollower(3, 13);
        jabber.addFollower(4, 13);
        jabber.addFollower(7, 13);
        jabber.addFollower(9, 13);

        expected = new ArrayList<>(Arrays.asList("0", "13"));
        actual = jabber.getUsersWithMostFollowers();
        assertArrayListEquals(expected, actual, "getUsersWithMostFollowers() with multiple expected results");
    }

}
