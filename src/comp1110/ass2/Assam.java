package comp1110.ass2;

/**
 * Represents Assam, the main game piece in Marrakech.
 */
public class Assam {
    private int x;
    private int y;
    //one from N,E,S,W.
    private char orientation;
    //initialized the position of Assam

    private static final char DEFAULT_ORIENTATION = 'S';
    private static final int DEFAULT_POSITION = 3;


    /**
     * Initializes Assam at the specified position (3, 3) facing North (N) by default.
     */
    public Assam() {
        this.x = DEFAULT_POSITION;
        this.y = DEFAULT_POSITION;
        this.orientation = DEFAULT_ORIENTATION;
    }
    public Assam(int x, int y, char orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
    /**
     * Initializes Assam using a formatted string.
     *
     * @param assamStr The Assam string, in the format AxyO, where x and y are the coordinates and O is the orientation.
     */
    public Assam(String assamStr) {
        if (assamStr == null || assamStr.length() != 4 || assamStr.charAt(0) != 'A') {
            throw new IllegalArgumentException("Invalid Assam string");
        }
        this.x = Character.getNumericValue(assamStr.charAt(1));
        this.y = Character.getNumericValue(assamStr.charAt(2));
        this.orientation = assamStr.charAt(3);
        if ("NESW".indexOf(orientation) == -1) {
            throw new IllegalArgumentException("Invalid orientation character");
        }
    }
    /**
     * Updates the position of Assam.
     *
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    public void updatePosition(int x, int y) {
        // You might want to add some validation here, like checking if the new position is within the board bounds.
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the direction in which Assam is facing.
     *
     * @param newOrientation The new direction, should be one of 'N', 'E', 'S', 'W'.
     */
    public void updateOrientation(char newOrientation) {
        // Optionally add validation, like checking if the newDirection is one of the valid directions.
        this.orientation = newOrientation;
    }

    public void move(int steps) {
        switch (orientation) {
            case 'N':
                y = (y - steps + Board.getSize()) % Board.getSize();
                if(y == 6 && steps != 0) {  // Crossing the north boundary
                    x = (x + 1) % Board.getSize();
                    orientation = 'S';
                }
                break;
            case 'S':
                y = (y + steps) % Board.getSize();
                if(y == 0 && steps != 0) {  // Crossing the south boundary
                    x = (x - 1 + Board.getSize()) % Board.getSize();
                    orientation = 'N';
                }
                break;
            case 'E':
                x = (x + steps) % Board.getSize();
                if(x == 0 && steps != 0) {  // Crossing the east boundary
                    y = (y - 1 + Board.getSize()) % Board.getSize();
                    orientation = 'W';
                }
                break;
            case 'W':
                x = (x - steps + Board.getSize()) % Board.getSize();
                if(x == 6 && steps != 0) {  // Crossing the west boundary
                    y = (y + 1) % Board.getSize();
                    orientation = 'E';
                }
                break;
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getOrientation() {
        return orientation;
    }
    //We assume assam will start at the center of the board(3,3)
}
