package app.sandwich;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class SandwichFilling {

    private ReadOnlyStringProperty name;
    private ReadOnlyIntegerProperty price;

    /**
     * Construct a sandwich filling with the specified name and price in pence.
     *
     * @param name  the name of the sandwich filling
     * @param price the price of the sandwich filling in pence
     */
    public SandwichFilling(String name, int price) {
        this.name = new ReadOnlyStringWrapper(name);
        this.price = new ReadOnlyIntegerWrapper(price);
    }

    /**
     * Return the name of this sandwich filling.
     *
     * @return the name of this sandwich filling
     */
    public String getName() {
        return name.get();
    }

    /**
     * Return the price of this sandwich filling in pence.
     *
     * @return the price of this sandwich filling in pence
     */
    public int getPrice() {
        return price.get();
    }

    /**
     * The name of this sandwich filling.
     *
     * @return the name of this sandwich filling.
     */
    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    /**
     * The price of this sandwich filling in pence.
     *
     * @return the price of this sandwich filling in pence
     */
    public ReadOnlyIntegerProperty priceProperty() {
        return price;
    }

}
