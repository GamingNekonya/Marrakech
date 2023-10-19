package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

import static comp1110.ass2.Marrakech.moveAssam;

public class Game extends Application {
    private Scene mainGameScene;

    public static final int VIEWER_WIDTH = 1200;
    public static final int VIEWER_HEIGHT = 700;

    private static final int SQUARE_SIZE = 50;

    private final Group root = new Group();
    private final Group controls = new Group();

    private final Group boardGroup = new Group();
    private final Group playerInfoGroup = new Group();
    private Assam assam;
    private Board board;
    private ImageView assamImageView;
    private GameSet gameSet = new GameSet();

    private int currentPlayerIndex = 0;

    private static Game instance;


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

        // Add the Face_Symbol image to help choose directions.
        Image compassImage = new Image("./Resources/Facing_Symbol.png");
        ImageView compassImageView = new ImageView(compassImage);
        // Button to choose directions
        Button northBtn = new Button("North");
        northBtn.setOnAction(e -> setAssamDirection('N'));

        Button southBtn = new Button("South");
        southBtn.setOnAction(e -> setAssamDirection('S'));

        Button eastBtn = new Button("East");
        eastBtn.setOnAction(e -> setAssamDirection('E'));

        Button westBtn = new Button("West");
        westBtn.setOnAction(e -> setAssamDirection('W'));

        Pane directionalPane = new Pane();
        directionalPane.getChildren().addAll(compassImageView, northBtn, southBtn, eastBtn, westBtn);

        // Adjust button locations
        northBtn.setLayoutX(25);
        northBtn.setLayoutY(-30);

        southBtn.setLayoutX(25);
        southBtn.setLayoutY(110);

        eastBtn.setLayoutX(100);
        eastBtn.setLayoutY(40);

        westBtn.setLayoutX(-50);
        westBtn.setLayoutY(40);

        /**
         * Set an action on the button. When the button is clicked,
         * the rollDie method is called, and the result is displayed
         * in the dieResultTxt Text node.
         */
        rollDieBtn.setOnAction(e -> {
            int dieResult = Marrakech.rollDie();
            // Update the text to display the result of the die roll
            dieResultTxt.setText("You rolled: " + dieResult);

            // update the assam position and then move base on dieResult
            String currentAssamState = "A" + assam.getX() + assam.getY() + assam.getOrientation();
            String newAssamState = moveAssam(currentAssamState, dieResult);
            updateAssamPosition(newAssamState);
        });

            // Add the buttons to a layout node
            HBox controlsBox = new HBox(10);
            controlsBox.getChildren().addAll(rollDieBtn, dieResultTxt, directionalPane);
            controlsBox.setLayoutX(VIEWER_WIDTH - 400);
            controlsBox.setLayoutY(VIEWER_HEIGHT - 200);
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

    /**
     * Use image to show the location of Assam.
     * Then rotate the Assam based on the direction button
     */
    private void displayAssam() {
        if (assamImageView == null) {
            assamImageView = new ImageView();
            assamImageView.setFitWidth(SQUARE_SIZE);
            assamImageView.setFitHeight(SQUARE_SIZE);
            assamImageView.setPreserveRatio(true);
            boardGroup.getChildren().add(assamImageView);
        }

        assamImageView.setLayoutX(assam.getX() * SQUARE_SIZE);
        assamImageView.setLayoutY(assam.getY() * SQUARE_SIZE);
        rotateAssamImage(assam.getOrientation());
    }

    private void rotateAssamImage(char orientation) {
        Image newImage;
        switch (orientation) {
            case 'N':
                newImage = new Image("./Resources/AssamN.png");
                break;
            case 'E':
                newImage = new Image("./Resources/AssamE.png");
                break;
            case 'S':
                newImage = new Image("./Resources/AssamS.png");
                break;
            case 'W':
                newImage = new Image("./Resources/AssamW.png");
                break;
            default:
                newImage = new Image("./Resources/Assam_S.png");
                break;
        }

        assamImageView.setImage(newImage);
    }

    /**
     * Call Marrakech.rotateAssam to set the rotate direction of Assam
     */
    private void setAssamDirection(char newDirection) {
        char currentDirection = assam.getOrientation();
        int rotation = 0;

        // rotate degree:
        switch (currentDirection) {
            case 'N':
                if (newDirection == 'E') rotation = 90;
                else if (newDirection == 'W') rotation = 270;
                break;
            case 'E':
                if (newDirection == 'S') rotation = 90;
                else if (newDirection == 'N') rotation = 270;
                break;
            case 'S':
                if (newDirection == 'W') rotation = 90;
                else if (newDirection == 'E') rotation = 270;
                break;
            case 'W':
                if (newDirection == 'N') rotation = 90;
                else if (newDirection == 'S') rotation = 270;
                break;
        }

        // call rotateAssam to obtain the currentAssamState
        String currentAssamState = "A" + assam.getX() + assam.getY() + currentDirection;
        String newAssamState = Marrakech.rotateAssam(currentAssamState, rotation);

        // If rotate pass the rotateAssame test, then rotate the Assam Image.
        if (!newAssamState.equals(currentAssamState)) {
            assam.setOrientation(newDirection);
            displayAssam();
        }
    }

    private void updateAssamPosition(String assamState) {
        int x = Character.getNumericValue(assamState.charAt(1));
        int y = Character.getNumericValue(assamState.charAt(2));
        char direction = assamState.charAt(3);

        assam.setX(x);
        assam.setY(y);
        assam.setOrientation(direction);
        displayAssam();
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
                    rotation = 90; // Rotate left
                } else if (code == KeyCode.S) {
                    rotation = 270; // Rotate right
                }
                break;
            case 'E':
                if (code == KeyCode.A) {
                    illegalMove = true; // Illegal to move backward when facing East
                } else if (code == KeyCode.W) {
                    rotation = 270; // Rotate right
                } else if (code == KeyCode.S) {
                    rotation = 90; // Rotate left
                }
                break;
        }

        if (illegalMove) {
            // Handle illegal move, e.g., show an error message to the player
            System.out.println("Illegal move! You cannot move Assam backward.");
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
            }
        }
    }

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

    public void startGame(){
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                ae -> {
                    if (!Marrakech.isGameOver(gameSet.getCurrentGameState())){
                        nextTurn();
                    }
                    else {
                        //Game end logic here
                        ((Timeline)ae.getSource()).stop();
                        String gameState = gameSet.getCurrentGameState();

                        char winner = Marrakech.getWinner(gameState);
                        if (winner != 'n') {
                            if (winner == 't') {
                                //
                            } else {
                                displayWinnerText(winner);
                            }
                        }
                    }
                }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Game");
        this.mainGameScene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        this.mainGameScene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));

        makeControls();  // Create the controls
        root.getChildren().add(controls);
        displayState(gameSet.getCurrentGameState());
        StartScene startScene = new StartScene(primaryStage, this);
        startGame();
    }

    public void switchToMainScene(Stage primaryStage) {
        primaryStage.setScene(this.mainGameScene);
        primaryStage.show();
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

