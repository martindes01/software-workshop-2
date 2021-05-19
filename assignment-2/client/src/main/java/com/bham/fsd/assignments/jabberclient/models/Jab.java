package com.bham.fsd.assignments.jabberclient.models;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The Jab class encapsulates the data required to represent an individual Jab
 * in the Jabber client application.
 *
 * @author Martin de Spirlet
 */
public class Jab implements Comparable<Jab> {

    private static final int JAB_ID_INDEX = 0;
    private static final int USERNAME_INDEX = 1;
    private static final int JAB_TEXT_INDEX = 2;
    private static final int LIKES_INDEX = 3;

    private final int jabID;
    private final String username;
    private final String jabText;
    private final int likes;

    /**
     * Creates a new Jab with the specified Jab ID, author username, Jab text and
     * number of likes.
     *
     * @param jabID    the ID of this Jab
     * @param username the username of the author of this Jab
     * @param jabText  the text of this Jab
     * @param likes    the number of likes this Jab has received
     */
    public Jab(int jabID, String username, String jabText, int likes) {
        this.jabID = jabID;
        this.username = username;
        this.jabText = jabText;
        this.likes = likes;
    }

    /**
     * Creates a new Jab with the data in the specified list. It is assumed that the
     * list contains, in this order, only the ID, author username, text and number
     * of likes of this Jab.
     *
     * @param data the list containing, in this order, only the ID, author username,
     *             text and number of likes of this Jab
     */
    public Jab(ArrayList<String> data) {
        this(Integer.parseInt(data.get(JAB_ID_INDEX)), data.get(USERNAME_INDEX), data.get(JAB_TEXT_INDEX),
                Integer.parseInt(data.get(LIKES_INDEX)));
    }

    /**
     * Returns the ID of this Jab.
     *
     * @return the ID of this Jab
     */
    public int getJabID() {
        return jabID;
    }

    /**
     * Returns the username of the author of this Jab.
     *
     * @return the username of the author of this Jab
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the text of this Jab.
     *
     * @return the text of this Jab
     */
    public String getJabText() {
        return jabText;
    }

    /**
     * Returns the number of likes this Jab has received.
     *
     * @return the number of likes this Jab has received
     */
    public int getLikes() {
        return likes;
    }

    @Override
    public int compareTo(Jab o) {
        return Integer.compare(jabID, o.jabID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Jab)) {
            return false;
        } else {
            return jabID == ((Jab) o).jabID;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(jabID);
    }

    @Override
    public String toString() {
        return username + " > " + jabText;
    }

}
