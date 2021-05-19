package com.bham.fsd.assignments.jabberclient.models;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The User class encapsulates the data required to represent an individual user
 * in the Jabber client application.
 *
 * @author Martin de Spirlet
 */
public class User implements Comparable<User> {

    private static final int USERNAME_INDEX = 0;

    private final String username;

    /**
     * Creates a new user with the specified username.
     *
     * @param username the username of this user
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Creates a new user with the data in the specified list. It is assumed that
     * the list contains only the username of this user.
     *
     * @param data the list containing only the username of this user
     */
    public User(ArrayList<String> data) {
        this(data.get(USERNAME_INDEX));
    }

    /**
     * Returns the username of this user.
     *
     * @return the username of this user
     */
    public String getUsername() {
        return username;
    }

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof User)) {
            return false;
        } else {
            return username == ((User) o).username;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return username;
    }

}
