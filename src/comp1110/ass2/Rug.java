package comp1110.ass2;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a rug in Marrakech.
 */
public class Rug {
    private static final Set<Character> VALID_COLORS = Set.of('c', 'y', 'r', 'p');
    // A map to hold the next ID for each color.
    private static final Map<Character, Integer> NEXT_IDS = new HashMap<>();
    static {
        NEXT_IDS.put('c', 1);
        NEXT_IDS.put('y', 1);
        NEXT_IDS.put('r', 1);
        NEXT_IDS.put('p', 1);
    }
    //two position rug covers
    private char color;
    //rug's id
    private int id;
    //initialized the rug's priorities
    private int x1,y1,x2,y2;


    /**
     * Constructs a new Rug object.
     *
     * @param color The color of the rug.
     */
    public Rug(char color) {
        // Validate and set the color and get and set the next available ID.
        if (!NEXT_IDS.containsKey(color)) {
            throw new IllegalArgumentException("Invalid color");
        }
        this.color = color;
        this.id = NEXT_IDS.get(color);
        // Update the next available ID for this color.
        NEXT_IDS.put(color, id + 1);
    }


    /**
     * Constructs a new Rug object with position information.
     *
     * @param color The color of the rug.
     * @param x1 The x-coordinate of the first position.
     * @param y1 The y-coordinate of the first position.
     * @param x2 The x-coordinate of the second position.
     * @param y2 The y-coordinate of the second position.
     */
    public Rug(char color, int x1, int y1, int x2, int y2) {
        // Use the previous constructor to set color and ID.
        this(color);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Constructs a Rug from a string representation.
     * The string format should be "ciixxxxpp" where:
     * c = color character,
     * ii = 2-digit ID,
     * xxxx = 4 digit positions (2 for x and 2 for y), and
     * pp = 2-digit priority.
     *
     * @param rugString the string representation of the rug.
     */
    public Rug(String rugString) {
        if (rugString == null || rugString.length() != 9) {
            throw new IllegalArgumentException("Invalid rug string");
        }

        this.color = rugString.charAt(0);
        if (!VALID_COLORS.contains(color)) {
            throw new IllegalArgumentException("Invalid color");
        }

        this.id = Integer.parseInt(rugString.substring(1, 3));
        this.x1 = Character.getNumericValue(rugString.charAt(3));
        this.y1 = Character.getNumericValue(rugString.charAt(4));
        this.x2 = Character.getNumericValue(rugString.charAt(5));
        this.y2 = Character.getNumericValue(rugString.charAt(6));

        validatePositions();
    }

    /**
     * Checks if the given color character is valid.
     *
     * @param color A character representing a color.
     * @return True if the color is valid, false otherwise.
     */
    public static boolean isValidColor(char color) {
        return VALID_COLORS.contains(color);
    }

    /**
     * Validates that the positions are within acceptable bounds.
     */
    private void validatePositions() {
        if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
            throw new IllegalArgumentException("Invalid position(s)");
        }
    }

    /**
     * Converts the Rug object to its string representation.
     *
     * @return a string representing the rug.
     */
    public String toRugString(){
        return String.format("%c%02d%d%d%d%d", color, id, x1, y1, x2, y2);
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
