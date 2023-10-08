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


    public String toAssamString() {
        return "A" + x + y + orientation;
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
