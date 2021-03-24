package exercise;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private static final String TITLE = "Star Trek: First Contact";
    private static final String QUOTE = "\"The acquisition of wealth is no longer the driving force in our lives.  We work to better ourselves and the rest of humanity.\"  \u2014  Capt. Jean-Luc Picard";
    private static final String BUTTON_TEXT = "Display quote";

    private static final double WIDTH = 400;
    private static final double HEIGHT = 300;
    private static final double PADDING = 10;
    private static final double SPACING = 10;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label quote = new Label(QUOTE);
        quote.setAlignment(Pos.CENTER);
        quote.setVisible(false);
        quote.setWrapText(true);

        Button button = new Button(BUTTON_TEXT);
        button.setOnAction((event) -> {
            quote.setVisible(true);
            button.setDisable(true);
        });

        VBox root = new VBox(SPACING, quote, button);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(PADDING));

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setTitle(TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
