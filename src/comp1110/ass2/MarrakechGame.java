package comp1110.ass2;

/**
 * Represents the Marrakech game, containing all game elements and state.
 */
public class MarrakechGame {
    //The array of players in the game.
    private Player[] players;
    //The assam game piece.
    private Assam assam;
    //The game board.
    private Board board;

    /**
     * Constructor to initialize the Marrakech game.
     */
    public MarrakechGame(){
        this.players = new Player[4];
        this.assam = new Assam("");
        this.board = new Board();
        //Sample initialization......
    }

}
