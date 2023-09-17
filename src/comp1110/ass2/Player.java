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
    private boolean inGame;

    public Player(String playerString) {
        this.color = playerString.charAt(1);
        this.dirhams = Integer.parseInt(playerString.substring(2, 5));
        this.rugs = Integer.parseInt(playerString.substring(5, 7));
        this.inGame = playerString.charAt(7) == 'i';
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
        return inGame;
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

    /**
     * Checks if this player is out of game.
     *
     * @return True if the player is out, false otherwise.
     */

}