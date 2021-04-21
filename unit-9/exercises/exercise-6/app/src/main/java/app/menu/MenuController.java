package app.menu;

import java.net.URL;

import app.sandwich.SandwichBread;
import app.sandwich.SandwichFilling;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MenuController {

    private static final String MENU_VIEW_FILENAME = "menu.fxml";

    private static final ObservableList<SandwichBread> SANDWICH_BREADS = FXCollections.observableArrayList(
            new SandwichBread("White", 25), new SandwichBread("Whole grain", 25), new SandwichBread("Sourdough", 50));

    private static final ObservableList<SandwichFilling> SANDWICH_FILLINGS = FXCollections.observableArrayList(
            new SandwichFilling("Ham and cheese", 150), new SandwichFilling("Egg mayonnaise", 175),
            new SandwichFilling("Bacon lettuce tomato", 200));

    private static final String BREAD_NAME_PROPERTY = "name";
    private static final String FILLING_NAME_PROPERTY = "name";

    private static final String PRICE_FORMAT = "\u00a3%d.%02d";
    private static final int PENCE_PER_POUND = 100;

    private static final String TOTAL_PRICE_PREFIX = "Total: ";

    @FXML
    private TableView<SandwichFilling> sandwichFillingTableView;
    @FXML
    private TableColumn<SandwichFilling, String> sandwichFillingNameTableColumn;
    @FXML
    private TableColumn<SandwichFilling, String> sandwichFillingPriceTableColumn;

    @FXML
    private TableView<SandwichBread> sandwichBreadTableView;
    @FXML
    private TableColumn<SandwichBread, String> sandwichBreadNameTableColumn;
    @FXML
    private TableColumn<SandwichBread, String> sandwichBreadPriceTableColumn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private void initialize() {
        InvalidationListener sandwichInvalidationListener = observable -> {
            totalPriceLabel.textProperty()
                    .set(TOTAL_PRICE_PREFIX
                            + formatPrice(sandwichBreadTableView.getSelectionModel().getSelectedItem().getPrice()
                                    + sandwichFillingTableView.getSelectionModel().getSelectedItem().getPrice()));
        };

        sandwichBreadTableView.setItems(SANDWICH_BREADS);
        sandwichBreadNameTableColumn.setCellValueFactory(new PropertyValueFactory<>(BREAD_NAME_PROPERTY));
        sandwichBreadPriceTableColumn.setCellValueFactory(value -> {
            return new SimpleStringProperty(formatPrice(value.getValue().getPrice()));
        });

        sandwichFillingTableView.setItems(SANDWICH_FILLINGS);
        sandwichFillingNameTableColumn.setCellValueFactory(new PropertyValueFactory<>(FILLING_NAME_PROPERTY));
        sandwichFillingPriceTableColumn.setCellValueFactory(value -> {
            return new SimpleStringProperty(formatPrice(value.getValue().getPrice()));
        });

        sandwichFillingTableView.getSelectionModel().selectFirst();

        sandwichBreadTableView.getSelectionModel().selectedItemProperty().addListener(sandwichInvalidationListener);
        sandwichFillingTableView.getSelectionModel().selectedItemProperty().addListener(sandwichInvalidationListener);

        sandwichBreadTableView.getSelectionModel().selectFirst();
    }

    /**
     * Return a string representation of the specified number of pence in pounds and
     * pence.
     *
     * @param pence the number of pence
     * @return a string representation of the specified number of pence in pounds
     *         and pence
     */
    private static String formatPrice(int pence) {
        return String.format(PRICE_FORMAT, pence / PENCE_PER_POUND, pence % PENCE_PER_POUND);
    }

    /**
     * Return a URL that refers to the view associated to this controller.
     *
     * @return a URL that refers to the view associated to this controller
     */
    public static URL getViewUrl() {
        return new MenuController().getClass().getResource(MENU_VIEW_FILENAME);
    }

}
