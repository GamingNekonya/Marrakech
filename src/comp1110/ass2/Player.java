package comp1110.ass2;

/**
 * Represents a player in the Marrakech game, tracking their color, dirhams,
 * rugs, and game status
 */
public class Player {
    /**
     * Player colors enumeration.
     */
    public enum Color{
        CYAN, YELLOW, RED, PURPLE
    }

    /**
     * Player's color.
     */
    private Color color;
    /**
     * The number of dirhanms the player currently possesses.
     */
    private int dirhams;
    /**
     * The number of rugs the player has left to place.
     */
    private int rugsRemaining;
    //The player's status ( 'i' for in, 'o' for out).
    private char status;
    /**
     * Constructors, getters, setters and other methods...
     */
    private boolean isActive;

    /**
     * Constructs a new player based on a player string.
     *
     * @param playerString The string representing the player's details.
     */
    public Player (String playerString){
        //Sample initialization...
    }

    /**
     * Transfers dirhams from this player to another.
     *
     * @param receiver The player receiving the dirhams.
     * @param amount The amount of dirhams to transfer.
     */

    public void pay(Player receiver, int amount){
        //Sample implementation...
    }

    /**
     * Checks if this player is out of game.
     *
     * @return True if the player is out, false otherwise.
     */
    public boolean isOut(){
        return this.status == 'o';
    }
}
