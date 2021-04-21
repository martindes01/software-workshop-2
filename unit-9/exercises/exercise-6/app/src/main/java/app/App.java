package app;

import app.menu.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final String TITLE = "Sandwich Menu";

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MenuController.getViewUrl());

        primaryStage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));
        primaryStage.setTitle(TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
