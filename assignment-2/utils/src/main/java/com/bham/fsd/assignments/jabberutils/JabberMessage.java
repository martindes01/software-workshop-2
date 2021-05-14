package com.bham.fsd.assignments.jabberutils;

import java.io.Serializable;
import java.util.ArrayList;

public class JabberMessage implements Serializable {

    private String message;
    private ArrayList<ArrayList<String>> data;

    /**
     * Constructs a new Jabber protocol message with the specified message and data.
     *
     * @param message the message
     * @param data    the data
     */
    public JabberMessage(String message, ArrayList<ArrayList<String>> data) {
        this.message = message;
        this.data = data;
    }

    /**
     * Constructs a new Jabber protocol message with the specified message.
     *
     * @param message the message
     */
    public JabberMessage(String message) {
        this(message, null);
    }

    /**
     * Returns the data of this Jabber protocol message.
     *
     * @return the data of this Jabber protocol message
     */
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    /**
     * Returns the message of this Jabber protocol message.
     *
     * @return the message of this Jabber protocol message
     */
    public String getMessage() {
        return message;
    }

}
