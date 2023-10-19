package comp1110.ass2;

public class GameSet {
    private Assam assam;
    private Board board;
    private Player playerCyan;
    private Player playerYellow;
    private Player playerRed;
    private Player playerPurple;

    private Player currentPlayer;

    public GameSet() {
        this.playerCyan = new Player();
        this.playerCyan.setColor('c');

        this.playerYellow = new Player();
        this.playerYellow.setColor('y');

        this.playerRed = new Player();
        this.playerRed.setColor('r');

        this.playerPurple = new Player();
        this.playerPurple.setColor('p');

        this.board = new Board();

        this.assam = new Assam();

        this.currentPlayer = playerCyan;  // Set the initial player to playerCyan.
    }



    /**
     * Switches to the next player in the sequence.
     * The sequence of players is Cyan -> Yellow -> Red -> Purple.
     */
    private void switchPlayer() {
        if (currentPlayer == playerCyan) {
            currentPlayer = playerYellow;
        } else if (currentPlayer == playerYellow) {
            currentPlayer = playerRed;
        } else if (currentPlayer == playerRed) {
            currentPlayer = playerPurple;
        } else if (currentPlayer == playerPurple) {
            currentPlayer = playerCyan;
        }
    }

    public void placeRug(int rugId, int row, int col, char color) {
        String rugString = String.format("%c%02d", color, rugId);
        // Potential checks and logic about rug placement here
        board.placeRug(rugString, row, col);
    }
    public String getCurrentBoardState() {
        return board.toBoardString();
    }

    public String getCurrentAssamState() {
        return Assam.toAssamString(assam);
    }
    public int payDirhams(Player player, int amount) {
        if (player == null) {
            return 0;
        }

        int actualAmountToPay = Math.max(amount, 0);

        if (player.getDirhams() >= actualAmountToPay) {
            player.setDirhams(player.getDirhams() - actualAmountToPay);
            return actualAmountToPay;
        } else {
            int allDirhams = player.getDirhams();
            player.setDirhams(0);
            player.setIsInGame(false);
            return allDirhams;
        }
    }

    public void executePayment(char rugColor, int paymentAmount) {
        Player payer = currentPlayer;
        Player receiver = getPlayerByColor(rugColor);

        if (payer != null && receiver != null && payer != receiver) {
            int actualPaidAmount = payDirhams(payer,paymentAmount);
            receiver.receiveDirhams(actualPaidAmount);
        }

    }

    public Player getPlayerByColor(char color) {
        switch (color) {
            case 'c':
                return playerCyan;
            case 'y':
                return playerYellow;
            case 'r':
                return playerRed;
            case 'p':
                return playerPurple;
            default:
                return null;
        }
    }
    public String getCurrentPlayerString() {
        return currentPlayer.toPlayerString();
    }

    public Board getBoard(){
        return this.board;
    }

    public String getCurrentRugState() { return getCurrentRugState();}

    /**
     * Concatenates the string representations of each player's state
     * in the order Cyan, Yellow, Red, Purple (cyrp).
     *
     * @return A string representing the state of all players in the game.
     */
    public String getCurrentPlayerState() {
        // Concatenate the string representations of each player.
        return playerCyan.toPlayerString() + playerYellow.toPlayerString() + playerRed.toPlayerString() + playerPurple.toPlayerString();
    }

    /**
     * Combines the player, Assam, and board states into a single game state string.
     *
     * @return A string representing the complete state of the game.
     */
    public String getCurrentGameState() {
        return getCurrentPlayerState() + getCurrentAssamState() + getCurrentBoardState();
    }
}

