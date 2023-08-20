package comp1110.ass2;

public class MarrakechGame {
    //4 players
    private Player[] players;
    private Assam assam;
    private Board board;
    public MarrakechGame(){
        this.players = new Player[4];
        this.assam = new Assam("");
        this.board = new Board();
        //......
    }

}
