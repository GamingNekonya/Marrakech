package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class Game extends Application {
    private Scene mainGameScene;
    public static final int VIEWER_WIDTH = 1200;
    public static final int VIEWER_HEIGHT = 700;

    private static final int SQUARE_SIZE = 50;

    private final Group root = new Group();
    private final Group controls = new Group();

    private final Group boardGroup = new Group();
    private final Group playerInfoGroup = new Group();
    private Button rollDieBtn;
    private Assam assam;
    private Board board;
    private GameSet gameSet = new GameSet();

    private int currentPlayerIndex = 0;

    private static Game instance;

    private gameStage currentStage;

    private EventHandler<KeyEvent> keyEventHandler;

    private Circle currentAssamCircle = null;
    private Line currentOrientationLine = null;

    private Label statusLabel;

    private Rectangle[][] guiSquares;



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

        if (statusLabel == null) {
            statusLabel = new Label();
            statusLabel.setLayoutX(10);
            statusLabel.setLayoutY(VIEWER_HEIGHT - 30);
            root.getChildren().add(statusLabel);
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
        rollDieBtn = new Button("Roll the Die");
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
            String currentAssamState = assam.toAssamString(assam);
            String newAssamState = Marrakech.moveAssam(currentAssamState, dieResult);
            int newX = Character.getNumericValue(newAssamState.charAt(1));
            int newY = Character.getNumericValue(newAssamState.charAt(2));
            char newOrientation = newAssamState.charAt(3);
            assam.updateAssam(newX, newY, newOrientation);
            rollDieBtn.setDisable(true);
            displayAssam();
            currentStage = gameStage.MOVE_ASSAM;
            waitForPlayerAction();
        });

        // Add the button and text to a layout node and add to the controls group
        HBox controlsBox = new HBox(10);  // 10 is the spacing between controls
        controlsBox.getChildren().addAll(rollDieBtn, dieResultTxt);
        controlsBox.setLayoutX(VIEWER_WIDTH - 200);
        controlsBox.setLayoutY(VIEWER_HEIGHT - 50);

        controls.getChildren().add(controlsBox);
    }

    /**
     * Draws the game board on the UI. This method initializes the board's cells
     * and colors them based on the current state of the game.
     * Each cell of the board is represented as a rectangle, and the color of the cell
     * is determined by the color code fetched from the game's logic.
     */
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
                guiSquares[i][j] = cell;
            }
        }
    }

    /**
     * Displays the Assam on the board. The Assam is represented as a circle with a line indicating its orientation.
     * The position of the Assam is determined by its current coordinates on the board, and its orientation is indicated by a line pointing in one of the four cardinal directions: North, East, South, or West.
     */
    private void displayAssam() {
        int centerX = assam.getX() * SQUARE_SIZE + SQUARE_SIZE / 2;
        int centerY = assam.getY() * SQUARE_SIZE + SQUARE_SIZE / 2;
        if (currentAssamCircle != null && currentOrientationLine != null) {
            boardGroup.getChildren().removeAll(currentAssamCircle, currentOrientationLine);
        }

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

        if (currentAssamCircle != null && currentOrientationLine != null) {
            boardGroup.getChildren().removeAll(currentAssamCircle, currentOrientationLine);
        }

        currentAssamCircle = assamCircle;
        currentOrientationLine = orientationLine;

        boardGroup.getChildren().addAll(currentAssamCircle, currentOrientationLine);
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
     * Handles keyboard input from the user to control the rotation and movement of Assam.
     *
     * This method determines the new orientation of Assam based on the key pressed (W, A, S, D)
     * and its current orientation (N, E, S, W). It doesn't permit moving backward (e.g., pressing W while facing South,
     * or S while facing North, etc.). In case of an illegal move, the method will provide an error message.
     *
     * If a valid key is pressed, the method calculates the new orientation, updates Assam's state, and reflects this change in the UI.
     *
     * @param code The KeyCode corresponding to the pressed key by the user, expected to be W (move North), A (move West), S (move South), D (move East).
     *             The actual effect of these keys depends on Assam's current orientation.
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
                }else if (code == KeyCode.W) {
                    rotation = 0; // Do not rotate
                }
                break;
            case 'S':
                if (code == KeyCode.W) {
                    illegalMove = true; // Illegal to move backward when facing South
                } else if (code == KeyCode.A) {
                    rotation = 90; // Rotate right
                } else if (code == KeyCode.D) {
                    rotation = 270; // Rotate left
                }else if (code == KeyCode.S) {
                    rotation = 0; // Do not rotate
                }
                break;
            case 'W':
                if (code == KeyCode.D) {
                    illegalMove = true; // Illegal to move backward when facing West
                } else if (code == KeyCode.W) {
                    rotation = 90; // Rotate left
                } else if (code == KeyCode.S) {
                    rotation = 270; // Rotate right
                }else if (code == KeyCode.A) {
                    rotation = 0; // Do not rotate
                }
                break;
            case 'E':
                if (code == KeyCode.A) {
                    illegalMove = true; // Illegal to move backward when facing East
                } else if (code == KeyCode.W) {
                    rotation = 270; // Rotate right
                } else if (code == KeyCode.S) {
                    rotation = 90; // Rotate left
                }else if (code == KeyCode.D) {
                    rotation = 0; // Do not rotate
                }
                break;
        }

        if (illegalMove) {
            // Handle illegal move, e.g., show an error message to the player
            statusLabel.setText("Illegal move! You cannot move Assam backward.");
        }
        else if (rotation != 180) {
            String currentAssamState = assam.toAssamString(assam);
            String newAssamState = Marrakech.rotateAssam(currentAssamState, rotation);

            if (!newAssamState.equals(currentAssamState)) {
                // Parse the new Assam state
                int newX = Character.getNumericValue(newAssamState.charAt(1));
                int newY = Character.getNumericValue(newAssamState.charAt(2));
                char newOrientation = newAssamState.charAt(3);

                // Update Assam's state
                assam.updateAssam(newX, newY, newOrientation);

                // Update the display
                displayAssam();
                disableKeyInput();
                currentStage = gameStage.ROLL_DICE;
                waitForPlayerAction();
            }
            disableKeyInput();
            currentStage = gameStage.ROLL_DICE;
            waitForPlayerAction();
        }
    }

    private void enableKeyInput() {
        if (keyEventHandler != null && mainGameScene != null) {
            mainGameScene.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
        }
    }

    private void disableKeyInput() {
        if (keyEventHandler != null && mainGameScene != null) {
            mainGameScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
        }
    }

    /**
     * Displays a "Draw!" message on the user interface when the game ends in a draw.
     * The message is centrally placed at the bottom of the game board.
     */
    private void displayDrawText() {
        double startX = (VIEWER_WIDTH - 150) / 2;
        double startY = 7 * SQUARE_SIZE + 10;

        Label drawLabel = new Label("Draw!");
        drawLabel.setFont(new Font(40));
        drawLabel.setTextFill(Color.BLACK);
        drawLabel.setLayoutX(startX);
        drawLabel.setLayoutY(startY);
        drawLabel.setAlignment(Pos.CENTER);

        boardGroup.getChildren().add(drawLabel);
    }

    /**
     * Displays a "Winner!" label next to the player information of the winning player.
     *
     * @param winnerColor The color identifier of the winning player. It's used to locate
     *                    the player's information on the user interface.
     */
    private void displayWinnerText(char winnerColor) {
        for (int i = 0; i < playerInfoGroup.getChildren().size(); i += 4) {
            Node colorBox = playerInfoGroup.getChildren().get(i);
            if (colorBox instanceof Rectangle && ((Rectangle)colorBox).getFill().toString().equals(colorToHex(winnerColor))) {
                double startX = colorBox.getLayoutX() + 150;
                double startY = colorBox.getLayoutY() + 60;

                Label winnerLabel = new Label("Winner!");
                winnerLabel.setFont(new Font(20));
                winnerLabel.setTextFill(Color.GOLD);
                winnerLabel.setLayoutX(startX);
                winnerLabel.setLayoutY(startY);

                playerInfoGroup.getChildren().add(winnerLabel);
                break;
            }
        }
    }

    /**
     * Converts a color character identifier to its corresponding hexadecimal color string.
     *
     * @param color A character representing the color.
     *              'c' for CYAN, 'y' for YELLOW, 'r' for RED, 'p' for PURPLE.
     * @return A string representing the hexadecimal value of the color, or an empty string if the color identifier is invalid.
     */
    private String colorToHex(char color) {
        switch (color) {
            case 'c':
                return Color.CYAN.toString();
            case 'y':
                return Color.YELLOW.toString();
            case 'r':
                return Color.RED.toString();
            case 'p':
                return Color.PURPLE.toString();
            default:
                return "";
        }
    }

    private enum gameStage {
        ROTATE_ASSAM,
        ROLL_DICE,
        MOVE_ASSAM,
        PAY_DIRHAMS,
        PLACE_CARPET,
        END_TURN
    }

    /**
     * Advances the game to the next player's turn.
     * This method should handle the transition between players, ensuring that game state variables are updated accordingly,
     * and any necessary game logic is executed (e.g., validations, state saving).
     * It should also update the UI to reflect the change, such as by calling `highlightCurrentPlayerInfo()`.
     */

    private void nextTurn() {
        String currentGameState = gameSet.getCurrentGameState();
        char winner = Marrakech.getWinner(currentGameState);
        if (winner != 'n') {
            gameOver(winner);
        } else {
            currentStage = gameStage.ROTATE_ASSAM;
            highlightCurrentPlayerInfo();
            waitForPlayerAction();
        }
    }

    private void waitForPlayerAction() {
        switch (currentStage) {
            case ROTATE_ASSAM:
                enableKeyInput();
                break;
            case ROLL_DICE:
                rollDieBtn.setDisable(false);
                break;
            case MOVE_ASSAM:
                currentStage = gameStage.PAY_DIRHAMS;
                waitForPlayerAction();
                break;
            case PAY_DIRHAMS:
                gameSet.executePayment(assam.getCurrentAssamPositionRugColor(),Marrakech.getPaymentAmount(gameSet.getCurrentGameState()));
                currentStage = gameStage.PLACE_CARPET;
                waitForPlayerAction();
                break;
            case PLACE_CARPET:
                highlightAdjacentSquares(assam.getX(), assam.getY());
                break;
            case END_TURN:
                nextTurn();
                break;
        }
    }


    private void gameOver(char winner) {
        if (winner == 't') {
            displayDrawText();
        } else {
            displayWinnerText(winner);
        }
    }

    public void startGame(){
        nextTurn();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Game");
        this.mainGameScene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        guiSquares = new Rectangle[board.getSize()][board.getSize()];
        this.keyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyInput(event.getCode());
            }
        };
        makeControls();  // Create the controls
        rollDieBtn.setDisable(true);
        root.getChildren().add(controls);
        displayState(gameSet.getCurrentGameState());
        StartScene startScene = new StartScene(primaryStage, this);
        startGame();
    }

    public void highlightAdjacentSquares(int currentAssamX, int currentAssamY) {
        int[][] adjacentCoordinates = {
                {currentAssamX - 1, currentAssamY},
                {currentAssamX + 1, currentAssamY},
                {currentAssamX, currentAssamY - 1},
                {currentAssamX, currentAssamY + 1}
        };

        for (int[] coordinates : adjacentCoordinates) {
            int x = coordinates[0];
            int y = coordinates[1];

            if (x >= 0 && x < 7 && y >= 0 && y < 7) {
                Rectangle rectangle = guiSquares[x][y];
                if (rectangle != null) {
                    rectangle.setStrokeWidth(5);
                    rectangle.setStroke(Color.BLACK);
                }
            }
        }
    }


    private void clearHighlights() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Rectangle square = guiSquares[i][j];
                square.setStroke(Color.BLACK);
                square.setStrokeWidth(1);
            }
        }
    }

    /**
     * Switches the current scene to the main game scene.
     *
     * @param primaryStage The primary stage of the application where scenes are displayed.
     *                     This is the main container of the JavaFX application.
     */
    public void switchToMainScene(Stage primaryStage) {
        primaryStage.setScene(this.mainGameScene);
        primaryStage.show();
        enableKeyInput();
    }

    public Game(){
        instance = this;
    }

    public static Game getInstance(){
        return instance;
    }

    public Group getRoot(){
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

