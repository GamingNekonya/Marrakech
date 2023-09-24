package comp1110.ass2;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board of Marrakech.
 */
public class Board {
    private Position assamPosition;
    private int[][] rugBoard;
    private static final int BOARD_SIZE = 7;
    //2D array representing the board.
    private List<Rug> rugsOnBoard = new ArrayList<>();

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

    public void placeRug(Rug rug) {
        rugsOnBoard.add(rug);

        // Update the rugBoard using the positions from the Rug instance.
        // This assumes you want to use the Rug's ID as an identifier in the rugBoard.
        rugBoard[rug.getX1()][rug.getY1()] = rug.getId();
        rugBoard[rug.getX2()][rug.getY2()] = rug.getId();
    }


    private void parseBoardString(String boardString) {
        int expectedLength = BOARD_SIZE * BOARD_SIZE * 3 + 1;

        if (boardString == null || boardString.length() != expectedLength) {
            throw new IllegalArgumentException("Invalid board string format. Expected length: " + expectedLength + ", but got: " + (boardString == null ? "null" : boardString.length()));
        }

        if (boardString.charAt(0) != 'B') {
            throw new IllegalArgumentException("Invalid board string format: does not start with 'B'");
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int index = 1 + (row * BOARD_SIZE + col) * 3;
                String rugSubString = boardString.substring(index, index + 3);

                if (rugSubString.equals("n00")) {
                    rugBoard[row][col] = 0;
                } else {
                    int colorCode = getColorCode(rugSubString.charAt(0));
                    int id = Integer.parseInt(rugSubString.substring(1, 3));
                    rugBoard[row][col] = colorCode * 100 + id;  // Using a composite number to represent both color and ID
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
        return rugBoard[row][col] / 100;  // To get only the color code
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

