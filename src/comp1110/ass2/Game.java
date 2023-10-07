package comp1110.ass2;

public class Game {
    private Board board;
    private Player playerCyan;
    private Player playerYellow;
    private Player playerRed;
    private Player playerPurple;

    public Game() {
        this.playerCyan = new Player();
        this.playerCyan.setColor('c');

        this.playerYellow = new Player();
        this.playerYellow.setColor('y');

        this.playerRed = new Player();
        this.playerRed.setColor('r');

        this.playerPurple = new Player();
        this.playerPurple.setColor('p');


        board = new Board();
    }
    public void placeRug(int rugId, int row, int col, char color) {
        String rugString = String.format("%c%02d", color, rugId);
        // Potential checks and logic about rug placement here
        board.placeRug(rugString, row, col);
    }
    public String getCurrentBoardState() {
        return board.toBoardString();
    }
}
