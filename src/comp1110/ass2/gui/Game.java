package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Board;
import comp1110.ass2.GameSet;
import comp1110.ass2.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);
        displayState(gameSet.getCurrentGameState());


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

