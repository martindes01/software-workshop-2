package com.bham.fsd.assignments.jabberserver;

import java.io.Serializable;
import java.util.ArrayList;

public class JabberMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private ArrayList<ArrayList<String>> response;

    /**
     * Constructs a new Jabber protocol message with the specified message and data.
     *
     * @param message the message
     * @param data    the data
     */
    public JabberMessage(String message, ArrayList<ArrayList<String>> data) {
        this.message = message;
        response = data;
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
        return response;
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
