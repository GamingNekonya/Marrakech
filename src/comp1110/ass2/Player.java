package comp1110.ass2;

/**
 * Represents a player in the Marrakech game, tracking their color, dirhams,
 * rugs, and game status
 */
public class Player {
    //player's color.
    private char color;
    //The number of dirhams the player has
    private int dirhams;
    //The number of rugs the player has.
    private int rugsRemaining;
    //The player's status ( 'i' for in, 'o' for out).
    private char status;

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
