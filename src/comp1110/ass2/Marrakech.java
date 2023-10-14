package comp1110.ass2;
import javafx.util.Pair;

import java.util.*;

public class Marrakech {

    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     *  - The String is 7 characters long
     *  - The first character in the String corresponds to the colour character of a player present in the game
     *  - The next two characters represent a 2-digit ID number
     *  - The next 4 characters represent coordinates that are on the board
     *  - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     *  - c023343 (Shares the colour but not the ID)
     *  - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     * @param gameString A String representing the current state of the game as per the README
     * @param rug A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameString, String rug) {
        // (1)Check if the String is 7 Characters long
        if (rug.length() !=7) {
            return false;
        }
        // (2)Check first char: Use charAt method find the color and then compare it with players
        char rugColor= rug.charAt(0);
        if (rugColor != 'c' && rugColor != 'y' && rugColor != 'r' && rugColor != 'p' ){
            return false;
        }

        // (3)Check id: Translate the string into integer to guarantee the placed rug starts with 00.
        String rugId = rug.substring(1, 3);
        int idInt= Integer.parseInt(rugId);
        if (idInt<0){
            return false;
        }

        // (4)Check coordinates: Translate each char into integer to guarantee location are in the board(7*7)
        int x1 = Character.getNumericValue(rug.charAt(3));
        int y1 = Character.getNumericValue(rug.charAt(4));
        int x2 = Character.getNumericValue(rug.charAt(5));
        int y2 = Character.getNumericValue(rug.charAt(6));

        if (x1 > 6 || y1 > 6 || x2 > 6 || y2 > 6) {
            return false;
        }

        // (6)Check gameString: contains p+Assume+board
        // player String:
        if (!gameString.contains("P" + rugColor)) {
            return false;
        }

        String boardString = gameString.split("A")[1].substring(2);
        if (boardString.contains(rugColor + rugId)) {
            return false;
        }

        return true;

    }

    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     *  - One shows 1
     *  - Two show 2
     *  - Two show 3
     *  - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     * @return The result of the roll of the die meeting the criteria above
     */

    public static int rollDie() {
        //make a die with six side but only have value1-4
        int[] die = {1,2,2,3,3,4};
        Random random =new Random();
        int randomIndex = random.nextInt(6);
        int randomNum = die[randomIndex];
        return randomNum;
    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {

        // split currentGame String into four player string:
        String player1String = currentGame.substring(0, 8); // Assuming each player state is 8 characters.
        String player2String = currentGame.substring(8, 16); // Assuming Assam state is 4 characters.
        String player3String = currentGame.substring(16, 24);
        String player4String = currentGame.substring(24, 32);

        // get each player's rugID and status(in or out of game)
        int player1Rugs  = Integer.parseInt(player1String.substring(5, 7));
        char player1State = player1String.charAt(7);
        int player2Rugs = Integer.parseInt(player2String.substring(5, 7));
        char player2State = player2String.charAt(7);
        int player3Rugs = Integer.parseInt(player3String.substring(5, 7));
        char player3State = player3String.charAt(7);
        int player4Rugs = Integer.parseInt(player4String.substring(5, 7));
        char player4State = player4String.charAt(7);

        // if all turn player have no rugs or is out of game, the game is over.
        boolean ifGameOver = (player1Rugs ==0 || player1State=='o')
                &&(player2Rugs ==0 || player2State=='o')
                &&(player3Rugs ==0 || player3State=='o')
                &&(player4Rugs ==0 || player4State=='o');

        if(ifGameOver) {
            return true;
        }
        return false;
    }


    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     * @param currentAssam A String representing Assam's current state
     * @param rotation The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                 is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                 current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        // FIXME: Task 9
        //Get current orientation
        String position = currentAssam.substring(1, 3);
        char direction = currentAssam.charAt(3);
        rotation%=360;
        // Mapping the directions in order, so we can easily rotate Assam
        String directions = "NESW";

        // If rotation is not 90 or -90, Assam stays in the current direction
        if (rotation != 90 && rotation != 270) {return currentAssam;}

        // Find the index of the current direction in the directions string
        int currentIndex = directions.indexOf(direction);
        int newIndex = -1;

        // Rotate 90 degrees to the right
        if (rotation == 90) {newIndex = (currentIndex + 1) % 4;}
        // Rotate 90 degrees to the left
        else {newIndex = (currentIndex - 1 + 4) % 4;}

        // Construct and return the new Assam string
        return "A" + position + directions.charAt(newIndex);

    }

    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     *   1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     *   2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     *      the new rug must not cover the entirety of another rug that's already on the board.
     * @param gameState A game string representing the current state of the game
     * @param rug A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rug) {
        // FIXME: Task 10
        String assamString = gameState.substring(32, 36);
        int assamX = Character.getNumericValue(assamString.charAt(1));
        int assamY = Character.getNumericValue(assamString.charAt(2));

        // position of the expected rug
        int rugX1 = Character.getNumericValue(rug.charAt(3));
        int rugY1 = Character.getNumericValue(rug.charAt(4));
        int rugX2 = Character.getNumericValue(rug.charAt(5));
        int rugY2 = Character.getNumericValue(rug.charAt(6));

        int absX1 = Math.abs(rugX1 - assamX);
        int absY1 = Math.abs(rugY1 - assamY);
        int absX2 = Math.abs(rugX2 - assamX);
        int absY2 = Math.abs(rugY2 - assamY);

        // Check if the rug will place on Assam and whether it is next to Assam
        boolean rugNotOnAssam = (rugX1 != assamX || rugY1 != assamY) && (rugX2 != assamX || rugY2 != assamY);
        boolean nextAssam = (absX1 + absY1 == 1 || absX2 + absY2 == 1);

       // if
        if (rugNotOnAssam && nextAssam) {
            // Find the rug tiles in the board
            String boardString = gameState.substring(gameState.indexOf('B'));

            // Check if the rug will completely cover another rug
            String boardRug1 = boardString.substring((rugX1 * 21 + rugY1 * 3) + 1, (rugX1 * 21 + rugY1 * 3) + 4);
            String boardRug2 = boardString.substring((rugX2 * 21 + rugY2 * 3) + 1, (rugX2 * 21 + rugY2 * 3) + 4);

            if (!boardRug1.equals("n00") || !boardRug2.equals("n00")) {
                return !boardRug1.equals(boardRug2);
            }
            return true;
        }

        return false;
    }

    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {
        // FIXME: Task 11
        String[] parts = gameString.split("A|B");
        String playerString = parts[0];
        String assamString = "A" + parts[1];
        String boardString = parts[2];

        // Parse Assam's position and orientation
        int assamX = Character.getNumericValue(assamString.charAt(1));
        int assamY = Character.getNumericValue(assamString.charAt(2));

        // Get rugColor of Assam's position on board
        String boardAssam = boardString.substring(assamX * 21 + assamY * 3, assamX * 21 + assamY * 3 + 3);
        char rugColor = boardAssam.charAt(0);

        // Create a set to track visited positions
        Set<String> calculatedSpuare = new HashSet<>();

        // Call the recursive function to calculate payment
        int paymentAmount = payment(gameString, boardString, assamX, assamY, rugColor, calculatedSpuare);

        return paymentAmount;
    }

    static int payment(String gameString, String boardString, int assamX, int assamY, char rugColor, Set<String> calculatedSpuare) {
        String positionKey = assamX + "," + assamY;

        // Check if Assam's position contains a rug and if it has not been calculated before
        if (rugColor !='n' && !calculatedSpuare.contains(positionKey)) {
            int paymentAmount = 1;
            calculatedSpuare.add(positionKey);


            int[][] nextRug = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

            for (int[] next : nextRug) {
                int newX = assamX + next[0];
                int newY = assamY + next[1];

                if (newX >= 0 && newX < 7 && newY >= 0 && newY < 7) {
                    // Check if the adjacent position contains a rug
                    String connectedRug = boardString.substring(newX * 21 + newY * 3, newX * 21 + newY * 3 + 3);
                    char connectedRugColor = connectedRug.charAt(0);

                    if (connectedRugColor!='n' && connectedRugColor==rugColor) {
                        // If the adjacent rug color is the same as Assam's rug color, it belongs to the same player
                        paymentAmount += payment(gameString, boardString, newX, newY, rugColor, calculatedSpuare); // Recursively calculate payment
                    }
                }
            }

            return paymentAmount; // Return the total payment amount
        }

        return 0; // No rug at Assam's position, or already visited, so payment is 0
    }

        /**
         * Determine the winner of a game of Marrakech.
         * For this task, you will be provided with a game state string and have to return a char representing the colour
         * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
         * player is the winner return 'r', etc...
         * If the game is not yet over, then you should return 'n'.
         * If the game is over, but is a tie, then you should return 't'.
         * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
         * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
         * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
         * score and number of dirhams, then the game is a tie.
         * @param gameState A String representation of the current state of the game
         * @return A char representing the winner of the game as described above.
         */
    public static char getWinner(String gameState) {

        // If game not over, return 'n'
        if (!isGameOver(gameState)) {
            return 'n';
        }

        Map<Character, Integer> playerScores = new HashMap<>();

        // find each player String
        for (int p = 0; p < 4; p++) {
            String playerString = gameState.substring(p * 8, (p + 1) * 8);
            int playerDirhams = Integer.parseInt(playerString.substring(2, 5));
            char playerColor = playerString.charAt(1);

            // Calculate each player rugs on Board and total Score.
            int playerScore = playerDirhams + boardRugs(playerColor,gameState);
            playerScores.put(playerColor, playerScore);
        }

        // return winner
        return getWinner(playerScores, gameState);
    }

    public static int boardRugs(char playerColor, String gameState) {
        String boardString = gameState.substring(37);
        int boardRugs = 0;

        // find all rugs on the board
        for (int c = 0; c < 7; c++) {
            for (int r = 0; r < 7; r++) {
                int startIndex = (c * 3) + (r * 21);
                String abbreviatedRug = boardString.substring(startIndex, startIndex + 3);

                // add rugs to each player
                if (abbreviatedRug.charAt(0) == playerColor) {
                    boardRugs++;
                }
            }
        }

        return boardRugs;
    }

    public static char getWinner(Map<Character, Integer> playerScores, String gameState) {

        char winner = 't';
        int highestScore = Integer.MIN_VALUE;
        int highestDirhams = Integer.MIN_VALUE;

        for (char playerColor : playerScores.keySet()) {
            int playerScore = playerScores.get(playerColor);

            //
            if (playerScore > highestScore) {
                highestScore = playerScore;
                highestDirhams = getDirhams(playerColor, gameState);
                winner = playerColor;
                // Compare dirham if players have same score
            } else if (playerScore == highestScore) {
                int playerDirhams = getDirhams(playerColor, gameState);
                if (playerDirhams > highestDirhams) {
                    highestDirhams = playerDirhams;
                    winner = playerColor;
                } else if (playerDirhams == highestDirhams) {
                    // 如果 Dirham 数量也相同，设定为平局
                    winner = 't';
                }
            }
        }

        return winner;
    }


    private static int getDirhams(char playerColor, String gameState) {
        for (int playerIndex = 0; playerIndex < 4; playerIndex++) {
            String playerString = gameState.substring(playerIndex * 8, (playerIndex + 1) * 8);
            char color = playerString.charAt(1);
            int dirhams = Integer.parseInt(playerString.substring(2, 5));
            if (color == playerColor) {
                return dirhams;
            }
        }
        return 0;
    }

    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult) {
        int x = Character.getNumericValue(currentAssam.charAt(1));
        int y = Character.getNumericValue(currentAssam.charAt(2));
        char direction = currentAssam.charAt(3);

        // Calculate new position based on direction and dieResult
        switch (direction) {
            case 'N': y -= dieResult; break;
            case 'S': y += dieResult; break;
            case 'E': x += dieResult; break;
            case 'W': x -= dieResult; break;
        }

        int remainingSteps = 0; // Additional steps after reaching boundary

        // Check if Assam is out of bounds and adjust position and direction accordingly
        if (x < 0 || x > 6 || y < 0 || y > 6) {
            // Calculate steps needed to reach the boundary and the remaining steps
            if (x < 0) {
                remainingSteps = -x;
                x = 0;
            } else if (x > 6) {
                remainingSteps = x - 6;
                x = 6;
            } else if (y < 0) {
                remainingSteps = -y;
                y = 0;
            } else if (y > 6) {
                remainingSteps = y - 6;
                y = 6;
            }

            // Special corner cases
            if (x == 0 && y == 6) {
                direction = (direction == 'W') ? 'N' : 'E';
                remainingSteps -= 1;
            } else if (x == 6 && y == 0) {
                direction = (direction == 'N') ? 'W' : 'S';
                remainingSteps -= 1;
            } else {
                // General boundary case: reverse direction and adjust x or y based on parity
                switch (direction) {
                    case 'N': direction = 'S'; x = (x % 2 == 0) ? x + 1 : x - 1; break;
                    case 'S': direction = 'N'; x = (x % 2 == 0) ? x - 1 : x + 1; break;
                    case 'E': direction = 'W'; y = (y % 2 == 0) ? y - 1 : y + 1; break;
                    case 'W': direction = 'E'; y = (y % 2 == 0) ? y + 1 : y - 1; break;
                }
                remainingSteps -= 1;
            }

            // Move Assam the remaining steps in the new direction
            switch (direction) {
                case 'N': y -= remainingSteps; break;
                case 'S': y += remainingSteps; break;
                case 'E': x += remainingSteps; break;
                case 'W': x -= remainingSteps; break;
            }

            // Final position adjustment in case it's still out of bounds
            x = Math.max(0, Math.min(x, 6));
            y = Math.max(0, Math.min(y, 6));
        }

        return "A" + x + y + direction;
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     * @param currentGame A String representation of the current state of the game.
     * @param rug A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rug) {
        // FIXME: Task 14
        return "";
    }

}
