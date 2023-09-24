package comp1110.ass2;

import java.util.Set;

/**
 * Represents a rug in Marrakech.
 */
public class Rug {
    private static final Set<Character> VALID_COLORS = Set.of('c', 'y', 'r', 'p');
    //one from c,t,r,p
    private char color;
    //rug's id
    private int id;
    //two position rug covers
    private int x1,y1,x2,y2;
    //initialized the rug's priorities

    /**
     * Constructs a new rug based on rug string.
     *
     * @param rugString The string representing the rug's details.
     */
    public Rug(String rugString) {
        if (rugString == null || rugString.length() != 7) {
            throw new IllegalArgumentException("Invalid rug string length!");
        }

        char inputColor = rugString.charAt(0);
        if (!VALID_COLORS.contains(inputColor)) {
            throw new IllegalArgumentException("Invalid color");
        }

        this.color = inputColor;

        try {
            this.id = Integer.parseInt(rugString.substring(1, 3));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID in rug string!");
        }

        this.x1 = Character.getNumericValue(rugString.charAt(3));
        this.y1 = Character.getNumericValue(rugString.charAt(4));
        this.x2 = Character.getNumericValue(rugString.charAt(5));
        this.y2 = Character.getNumericValue(rugString.charAt(6));

        // Add checks for valid board coordinates
        if (!isValidBoardCoordinate(x1, y1) || !isValidBoardCoordinate(x2, y2)) {
            throw new IllegalArgumentException("Invalid board coordinates!");
        }
    }


    private boolean isValidBoardCoordinate(int x, int y) {
        return x >= 0 && x < 7 && y >= 0 && y < 7;
    }

    // Getter methods :

    public char getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

}
