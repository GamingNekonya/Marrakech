package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {


    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private static final int SQUARE_SIZE = 50;

    private final Group root = new Group();
    private final Group controls = new Group();

    private final Group boardGroup = new Group();
    private final Group playerInfoGroup = new Group();
    private Assam assam;
    private Board board;
    private GameSet gameSet = new GameSet();

    private int currentPlayerIndex = 0;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        boardGroup.getChildren().clear();
        playerInfoGroup.getChildren().clear();
        String boardStr = state.substring(37);
        String assamStr = state.substring(32, 36);
        if (state == null || state.length() < 36) {
            // handle the error: state string is not valid
            return;
        }
        for (int i = 0; i < 4; i++) {
            Player player = new Player(state.substring(i * 8, i * 8 + 8));
            displayPlayerInfo(player, i);
        }
        // Ensure boardStr starts with 'B'
        if (!boardStr.startsWith("B")) {
            boardStr = "B" + boardStr;
        }



        // Initialize objects from strings
        board = new Board(boardStr);
        assam = new Assam(assamStr);
        // similarly, initialize Player and Assam objects
        root.getChildren().remove(boardGroup);
        root.getChildren().remove(playerInfoGroup);
        root.getChildren().addAll(boardGroup, playerInfoGroup);
        drawBoard();
        displayAssam();

    }

    /**
     * Create controls for the game UI, adding functionality to interact with the game.
     */
    void makeControls() {
        // Button to roll the die
        Button rollDieBtn = new Button("Roll the Die");
        // Text to display the result of the die roll
        Text dieResultTxt = new Text("Die result will appear here");

        /**
         * Set an action on the button. When the button is clicked,
         * the rollDie method is called, and the result is displayed
         * in the dieResultTxt Text node.
         */
        rollDieBtn.setOnAction(e -> {
            int dieResult = Marrakech.rollDie();
            // Update the text to display the result of the die roll
            dieResultTxt.setText("You rolled: " + dieResult);
            // Add additional code here to update the game state and re-display
            // using displayState(gameSet.getCurrentGameState());
        });

        // Add the button and text to a layout node and add to the controls group
        HBox controlsBox = new HBox(10);  // 10 is the spacing between controls
        controlsBox.getChildren().addAll(rollDieBtn, dieResultTxt);
        controlsBox.setLayoutX(VIEWER_WIDTH - 200);
        controlsBox.setLayoutY(VIEWER_HEIGHT - 50);

        controls.getChildren().add(controlsBox);
    }

    private void drawBoard() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Rectangle cell = new Rectangle(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                int colorCode = board.getColorAt(j, i);

                Color cellColor;
                switch(colorCode) {
                    case 1: //  'c'
                        cellColor = Color.CYAN;
                        break;
                    case 2: //  'y'
                        cellColor = Color.YELLOW;
                        break;
                    case 3: //  'r'
                        cellColor = Color.RED;
                        break;
                    case 4: //  'p'
                        cellColor = Color.PURPLE;
                        break;
                    default: // no rug
                        cellColor = Color.GRAY;
                        break;
                }

                cell.setFill(cellColor);
                cell.setStroke(Color.WHITE);
                boardGroup.getChildren().add(cell);
            }
        }
    }
    private void displayAssam() {
        int centerX = assam.getX() * SQUARE_SIZE + SQUARE_SIZE / 2;
        int centerY = assam.getY() * SQUARE_SIZE + SQUARE_SIZE / 2;

        Circle assamCircle = new Circle(centerX, centerY, SQUARE_SIZE / 4, Color.BROWN);

        Line orientationLine = new Line();
        orientationLine.setStartX(centerX);
        orientationLine.setStartY(centerY);

        switch (assam.getOrientation()) {
            case 'N':
                orientationLine.setEndX(centerX);
                orientationLine.setEndY(centerY - SQUARE_SIZE / 4);
                break;
            case 'E':
                orientationLine.setEndX(centerX + SQUARE_SIZE / 4);
                orientationLine.setEndY(centerY);
                break;
            case 'S':
                orientationLine.setEndX(centerX);
                orientationLine.setEndY(centerY + SQUARE_SIZE / 4);
                break;
            case 'W':
                orientationLine.setEndX(centerX - SQUARE_SIZE / 4);
                orientationLine.setEndY(centerY);
                break;
        }

        boardGroup.getChildren().addAll(assamCircle, orientationLine);
    }

    private void displayPlayerInfo(Player player, int index) {
        double startX = 400;
        double startY = 50 + index * 150;

        Rectangle colorBox = new Rectangle(startX, startY, 40, 40);
        switch (player.getColor()) {
            case 'c': colorBox.setFill(Color.CYAN); break;
            case 'y': colorBox.setFill(Color.YELLOW); break;
            case 'r': colorBox.setFill(Color.RED); break;
            case 'p': colorBox.setFill(Color.PURPLE); break;
        }
        Label dirhamsLabel = new Label("Dirhams: " + player.getDirhams());
        dirhamsLabel.setLayoutX(startX + 50);
        dirhamsLabel.setLayoutY(startY);

        Label rugsLabel = new Label("Rugs: " + player.getRugs());
        rugsLabel.setLayoutX(startX + 50);
        rugsLabel.setLayoutY(startY + 25);

        Label inGameLabel = new Label(player.isInGame() ? "In Game" : "Out of Game");
        inGameLabel.setLayoutX(startX + 50);
        inGameLabel.setLayoutY(startY + 50);

        playerInfoGroup.getChildren().addAll(colorBox, dirhamsLabel, rugsLabel, inGameLabel);
    }

    /**
     * Highlights the information of the currently active player.
     * This method should visually distinguish the active player's info panel,
     * typically by changing the style or adding visual cues to indicate that it's their turn.
     */
    private void highlightCurrentPlayerInfo() {
        // Remove existing highlights
        for (Node node : playerInfoGroup.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle rect = (Rectangle) node;
                rect.setStroke(Color.BLACK);  // Default color
            }
        }

        // Highlight the current player's info
        Node currentPlayerInfo = playerInfoGroup.getChildren().get(currentPlayerIndex * 4);  // 4 nodes per player
        if (currentPlayerInfo instanceof Rectangle) {
            Rectangle rect = (Rectangle) currentPlayerInfo;
            rect.setStroke(Color.GOLD);  // Highlight color
            rect.setStrokeWidth(5);  // Make the border thicker
        }
    }

    /**
     * Handles keyboard input for controlling the direction of Assam.
     * W - Move forward (if Assam is not facing south, otherwise illegal)
     * A - Rotate left (if Assam is not facing east, otherwise illegal)
     * D - Rotate right (if Assam is not facing west, otherwise illegal)
     * S - Illegal move (moving backward)
     *
     * @param code the KeyCode of the pressed key
     */
    private void handleKeyInput(KeyCode code) {
        char currentDirection = assam.getOrientation();
        int rotation = 0; // Rotation angle: 0 for no rotation, 90 for right, 270 for left
        boolean illegalMove = false; // Flag for illegal move
        // Determine the rotation based on the key pressed and the current direction
        switch (currentDirection) {
            case 'N':
                if (code == KeyCode.S) {
                    illegalMove = true; // Illegal to move backward when facing North
                } else if (code == KeyCode.A) {
                    rotation = 270; // Rotate left
                } else if (code == KeyCode.D) {
                    rotation = 90; // Rotate right
                }
                break;
            case 'S':
                if (code == KeyCode.W) {
                    illegalMove = true; // Illegal to move backward when facing South
                } else if (code == KeyCode.A) {
                    rotation = 90; // Rotate right
                } else if (code == KeyCode.D) {
                    rotation = 270; // Rotate left
                }
                break;
            case 'W':
                if (code == KeyCode.D) {
                    illegalMove = true; // Illegal to move backward when facing West
                } else if (code == KeyCode.W) {
                    rotation = 270; // Rotate left
                } else if (code == KeyCode.S) {
                    rotation = 90; // Rotate right
                }
                break;
            case 'E':
                if (code == KeyCode.A) {
                    illegalMove = true; // Illegal to move backward when facing East
                } else if (code == KeyCode.W) {
                    rotation = 90; // Rotate right
                } else if (code == KeyCode.S) {
                    rotation = 270; // Rotate left
                }
                break;
        }

        if (illegalMove) {
            // Handle illegal move, e.g., show an error message to the player
            System.out.println("Illegal move! You cannot move Assam backward.");
        } else if (rotation != 180) {
            //next step

        }

    }

    /**
     * Advances the game to the next player's turn.
     * This method should handle the transition between players, ensuring that game state variables are updated accordingly,
     * and any necessary game logic is executed (e.g., validations, state saving).
     * It should also update the UI to reflect the change, such as by calling `highlightCurrentPlayerInfo()`.
     */

    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;  // Move to the next player
        highlightCurrentPlayerInfo();  // Update the UI to reflect the change
        // ... any other turn-based logic
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        scene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));

        makeControls();  // Create the controls
        root.getChildren().add(controls);
        displayState(gameSet.getCurrentGameState());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

