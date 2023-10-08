package comp1110.ass2;

public class Game {
    private Assam assam;
    private Board board;
    private Player playerCyan;
    private Player playerYellow;
    private Player playerRed;
    private Player playerPurple;

    private Player currentPlayer;

    public Game() {
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
     * Handles a single turn for the current player.
     * Manages the turn sequence which involves rolling the die, moving Assam,
     * possibly paying a fee, and making a rug placement.
     */
    public void playTurn() {
        System.out.println("It's " + currentPlayer.getColor() + "'s turn.");

        // Roll the die.
        int dieResult = Marrakech.rollDie();  // Replace with your actual class name.
        System.out.println(currentPlayer.getColor() + " rolled a " + dieResult + ".");

        // Move Assam.
        String newAssamPosition = Marrakech.moveAssam(assam.toString(), dieResult);
        assam = new Assam(newAssamPosition);
        System.out.println("Assam is now at " + newAssamPosition);

        // Check if payment is due.
        int paymentAmount = Marrakech.getPaymentAmount(getCurrentGameState());
        if (paymentAmount > 0) {
            // Handle payment.
            System.out.println(currentPlayer.getColor() + " owes a payment of " + paymentAmount + ".");
            // ... might want to adjust player's funds here.
        }

        // Make a rug placement.
        String newPlacement = Marrakech.makePlacement(getCurrentGameState(), getCurrentRugState());
        System.out.println(currentPlayer.getColor() + " placed a rug: " + newPlacement);
        // ... Update the board with the new rug placement.

        // Switch to the next player.
        switchPlayer();
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
        return assam.toAssamString();
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
        return playerCyan.toString() + playerYellow.toString() + playerRed.toString() + playerPurple.toString();
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

