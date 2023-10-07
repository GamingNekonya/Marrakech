package comp1110.ass2;

/**
 * Represents a player in the Marrakech game, tracking their color, dirhams,
 * rugs, and game status
 */
public class Player {
    /**
     * Player colors enumeration.
     */
    private char color;
    private int dirhams;
    private int rugs;
    private boolean isInGame;

    public Player(char color, int dirhams, int rugs, boolean isInGame) {
        this.color = color;
        this.dirhams = dirhams;
        this.rugs = rugs;
        this.isInGame = isInGame;
    }
    public Player() {
        this.dirhams = 30;
        this.rugs = 15;
        this.isInGame = true;
        // default color, can be changed later if needed
        this.color = 'c';
    }

    // Constructor to create a Player object from a string
    public Player(String playerString) {
        if (playerString == null || playerString.length() != 8) {
            throw new IllegalArgumentException("Invalid player string");
        }
        color = playerString.charAt(1);
        if ("cyrp".indexOf(color) == -1) {
            throw new IllegalArgumentException("Invalid color character");
        }
        dirhams = Integer.parseInt(playerString.substring(2, 5));
        rugs = Integer.parseInt(playerString.substring(5, 7));
        isInGame = playerString.charAt(7) == 'i';
    }

    public String toPlayerString(){
        String dirhamString = String.format("%03d", dirhams);
        String rugString = String.format("%02d", rugs);
        char inGameChar = isInGame ? 'i' : 'o';

        return "P" + color + dirhamString + rugString +inGameChar;
    }
    public void setColor(char color) {
        if ("cyrp".indexOf(color) == -1) {
            throw new IllegalArgumentException("Invalid color character");
        }
        this.color = color;
    }

    public void setDirhams(int dirhams) {
        this.dirhams = dirhams;
    }

    public void setRugs(int rugs) {
        this.rugs = rugs;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    /**
     * Player's color.
     */
    public char getColor() {
        return color;
    }

    /**
     * The number of dirhanms the player currently possesses.
     */
    public int getDirhams() {
        return dirhams;
    }

    /**
     * The number of rugs the player has left to place.
     */
    public int getRugs() {
        return rugs;
    }
    //The player's status ( 'i' for in, 'o' for out).

    /**
     * Constructors, getters, setters and other methods...
     */
    public boolean isInGame() {
        return isInGame;
    }

    /**
     * Constructs a new player based on a player string.
     *
     * @param playerString The string representing the player's details.
     */

    /**
     * Transfers dirhams from this player to another.
     *
     * @param receiver The player receiving the dirhams.
     * @param amount   The amount of dirhams to transfer.
     */

    public void pay(Player receiver, int amount) {
        if (this.dirhams >= amount) {
            this.dirhams -= amount;
            receiver.dirhams += amount;
        } else {
            System.out.println("Not enough dirhams!");
        }
    }

}