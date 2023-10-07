package comp1110.ass2;

public class Game {
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
    }
}
