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
        x = DEFAULT_POSITION;
        y = DEFAULT_POSITION;
        orientation = DEFAULT_ORIENTATION;
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
        x = Character.getNumericValue(assamStr.charAt(1));
        y = Character.getNumericValue(assamStr.charAt(2));
        orientation = assamStr.charAt(3);
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
    public void updateAssam(int x, int y, char orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public static String toAssamString(Assam assam) {
        return "A" + assam.x + assam.y + assam.orientation;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
