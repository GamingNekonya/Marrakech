package comp1110.ass2.gui;

import comp1110.ass2.GameSet;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;


    private GameSet gameSet = new GameSet();
    // Assume you have a GameSet class that handles the game logic

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();

    }
}
