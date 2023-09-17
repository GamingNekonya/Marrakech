package comp1110.ass2;


/**
 * Represents the game board of Marrakech.
 */
public class Board {
    private Position assamPosition;
    private int[][] rugBoard;
    private static final int BOARD_SIZE = 7;
    //2D array representing the board.

    /**
     * Constructor to initialize the game board.
     */
    public Board() {
        this(""); // call the other constructor with an empty boardString
    }

    public Board(String boardString) {
        assamPosition = new Position(4, 4, 'S');
        rugBoard = new int[BOARD_SIZE][BOARD_SIZE];
        if (boardString.isEmpty()) {
            initRugBoard();
        } else {
            parseBoardString(boardString);
        }
    }

    private void initRugBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                rugBoard[i][j] = 0;
            }
        }
    }

    private void parseBoardString(String boardString) {
        // Check if boardString is null or has an unexpected length
        int expectedLength = BOARD_SIZE * BOARD_SIZE * 3 + 1;
        // Check if boardString is null or has an unexpected length
        if (boardString == null || boardString.length() != expectedLength) {
            throw new IllegalArgumentException("Invalid board string format. Expected length: " + expectedLength + ", but got: " + (boardString == null ? "null" : boardString.length()));
        }

        // Check if boardString starts with 'B'
        if (boardString.charAt(0) != 'B') {
            throw new IllegalArgumentException("Invalid board string format: does not start with 'B'");
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                int index = 1 + (col * BOARD_SIZE + row) * 3;
                String rugSubString = boardString.substring(index, index + 3);

                // Check if the rugSubString represents an empty space
                if (rugSubString.equals("n00")) {
                    rugBoard[row][col] = 0;
                } else {
                    rugBoard[row][col] = getColorCode(rugSubString.charAt(0));
                }
            }
        }
    }


    //other board play methods like check the rugs place is valid or not...
    //other methods should be here...

    private int getColorCode(char color) {
        switch (color) {
            case 'c':
                return 1;
            case 'y':
                return 2;
            case 'r':
                return 3;
            case 'p':
                return 4;
            default:
                return 0;
        }
    }

    public int getColorAt(int row, int col) {
        return rugBoard[row][col];
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    public Position getAssamPosition() {
        return assamPosition;
    }

    public static class Position {
        int x;
        int y;
        char direction;


        public Position(int x, int y, char direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public char getDirection() {
            return direction;
        }
    }
}

