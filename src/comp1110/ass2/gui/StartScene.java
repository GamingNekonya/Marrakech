package comp1110.ass2.gui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * The StartScene class represents the initial scene of the game,
 * displaying a start image that, when clicked, initiates the main game scene.
 */
public class StartScene {
    // The scene attribute holds the graphical elements for the start scene.
    private Scene startScene;
    /**
     * Constructor for the StartScene class.
     *
     * @param primaryStage The primary stage for this application, onto which the scene is set.
     * @param game An instance of the Game class, used to switch to the main game scene.
     */
    public StartScene(Stage primaryStage, Game game) {
        // Creating a new StackPane named startPane to hold the UI elements.
        StackPane startPane = new StackPane();

        // Creating an image object that loads an image resource for the start button.
        Image startImage = new Image("./Resources/StartIcon.png");
        // Creating an ImageView object to display the start image.
        ImageView startImageView = new ImageView(startImage);

        // Setting an event handler on the startImageView, which will trigger the transition to the main game scene when the image is clicked.
        startImageView.setOnMouseClicked(event -> {
            game.switchToMainScene(primaryStage);
        });
        // Adding the startImageView to the startPane.
        startPane.getChildren().add(startImageView);
        // Initializing the startScene with the startPane and the predefined width and height.
        startScene = new Scene(startPane, Game.VIEWER_WIDTH, Game.VIEWER_HEIGHT);
        // Setting the scene onto the primaryStage and displaying it.
        primaryStage.setScene(startScene);
        primaryStage.show();
    }
    /**
     * Getter method for the scene.
     *
     * @return the startScene instance.
     */
    public Scene getScene() {
        return this.startScene;
    }
}