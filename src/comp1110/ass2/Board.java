package comp1110.ass2;


import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board of Marrakech.
 */
public class Board {
    private static String[][] grid;  // Holds abbreviated rug strings
    /**
     * Creates a new 7x7 game board.
     */
    private static final int SIZE = 7;
    //2D array representing the board.


    /**
     * Constructor to initialize the game board.
     */

    public Board() {
        grid = new String[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = "n00";  // Empty spot
            }
        }
    }

    /**
     * Initializes the board using a formatted string.
     *
     * @param boardStr The board string, which should start with 'B' and followed by 147 characters
     *                 representing the board state.
     */
    public Board(String boardStr) {
        if (boardStr == null || boardStr.length() != 148 || boardStr.charAt(0) != 'B') {
            throw new IllegalArgumentException("Invalid board string");
        }
        grid = new String[SIZE][SIZE];
        int strIndex = 1;
        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++) {
                grid[row][col] = boardStr.substring(strIndex, strIndex + 3);
                strIndex += 3;
            }
        }
    }

    private boolean isWithinBoard(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public List<int[]> getAdjacentSquares(int currentAssamX, int currentAssamY) {
        int[][] adjacentCoordinates = {
                {currentAssamX - 1, currentAssamY},
                {currentAssamX + 1, currentAssamY},
                {currentAssamX, currentAssamY - 1},
                {currentAssamX, currentAssamY + 1}
        };

        List<int[]> validCoordinates = new ArrayList<>();
        for (int[] coordinates : adjacentCoordinates) {
            int x = coordinates[0];
            int y = coordinates[1];
            if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) { // SIZE is the board's width and height
                validCoordinates.add(coordinates);
            }
        }
        return validCoordinates;
    }
    /**
     * Convert the board state into a board string following the rules given.
     *
     * @return the board string representing the current state of the board
     */
    public static String toBoardString() {
        StringBuilder boardString = new StringBuilder("B");
        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++) {
                boardString.append(grid[row][col]);
            }
        }
        return boardString.toString();
    }

    /**
     * Places a rug string at the specified position on the board.
     *
     * @param rugString the abbreviated rug string to be placed
     * @param row       the row index of the position
     * @param col       the column index of the position
     */
    public void placeRug(String rugString, int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            grid[row][col] = rugString;
        } else {
            throw new IllegalArgumentException("Invalid position");
        }
    }

    /**
     * Retrieve the color code at a specified position on the board.
     * <p>
     * The method retrieves the first character of the rug string at the
     * specified coordinates and returns a corresponding color code.
     * <ul>
     *     <li>1: Cyan ('c')</li>
     *     <li>2: Yellow ('y')</li>
     *     <li>3: Red ('r')</li>
     *     <li>4: Purple ('p')</li>
     *     <li>0: No Rug ('n')</li>
     * </ul>
     * It's expected that a calling method will map these codes to actual
     * color values for the purpose of rendering the board.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return An integer representing the color code at the specified coordinates.
     * @throws IllegalArgumentException if x or y are out of the board bounds.
     * @throws IllegalStateException    if the color character is unrecognized.
     */
    public int getColorAt(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
        char colorChar = grid[x][y].charAt(0);
        switch (colorChar) {
            case 'c':
                return 1;
            case 'y':
                return 2;
            case 'r':
                return 3;
            case 'p':
                return 4;
            case 'n':
                return 0;
            default:
                throw new IllegalStateException("Unexpected color char: " + colorChar);
        }
    }
    public static int getSize() {
        return SIZE;
    }
}