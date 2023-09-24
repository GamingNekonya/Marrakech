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

    /**
     * Constructs Assam based on an Assam string.
     *
     * @param assamString The string representing Assam's position and orientation
     */
    public Assam(String assamString) {
        if (assamString == null || assamString.length() < 4) {
            System.out.println("Invalid Assam string!");
            return;
        }
        char possibleOrientation = assamString.charAt(3);
        if ("NESW".indexOf(possibleOrientation) == -1) {
            System.out.println("Invalid orientation!");
            return;
        }

        this.x = Character.getNumericValue(assamString.charAt(1));
        this.y = Character.getNumericValue(assamString.charAt(2));
        this.orientation = assamString.charAt(3);
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
