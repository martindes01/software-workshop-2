package app.sandwich;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class SandwichBread {

    private ReadOnlyStringProperty name;
    private ReadOnlyIntegerProperty price;

    /**
     * Construct a sandwich bread with the specified name and price in pence.
     *
     * @param name  the name of the sandwich bread
     * @param price the price of the sandwich bread in pence
     */
    public SandwichBread(String name, int price) {
        this.name = new ReadOnlyStringWrapper(name);
        this.price = new ReadOnlyIntegerWrapper(price);
    }

    /**
     * Return the name of this sandwich bread.
     *
     * @return the name of this sandwich bread
     */
    public String getName() {
        return name.get();
    }

    /**
     * Return the price of this sandwich bread in pence.
     *
     * @return the price of this sandwich bread in pence
     */
    public int getPrice() {
        return price.get();
    }

    /**
     * The name of this sandwich bread.
     *
     * @return the name of this sandwich bread.
     */
    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    /**
     * The price of this sandwich bread in pence.
     *
     * @return the price of this sandwich bread in pence
     */
    public ReadOnlyIntegerProperty priceProperty() {
        return price;
    }

}
