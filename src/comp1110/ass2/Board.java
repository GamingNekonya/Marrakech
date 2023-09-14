package comp1110.ass2;

/**
 * Represents the game board of Marrakech.
 */
public class Board {
    private int[][] assamBoard;
    private int[][] rugBoard;
    private static final int BOARD_SIZE = 7;
    //2D array representing the board.
    /**
     * Constructor to initialize the game board.
     */
    public Board(){
        assamBoard = new int[BOARD_SIZE][BOARD_SIZE];
        rugBoard = new int[BOARD_SIZE][BOARD_SIZE];
        //Sample initialization....
    }

    private void inBoards(){
        for (int i = 0; i <BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                assamBoard[i][j] = 0;
                rugBoard[i][j] = 0;
            }
        }
    }

    /**
     * place the assam in a position and clear the old position
     * @param x
     * @param y
     */
    public void placeAssam(int x, int y){
        for (int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(assamBoard[i][j] == 1){
                    assamBoard[i][j] = 0;
                }
            }
        }
        assamBoard[x][y] = 1;
    }
    /**
     * Places a rug on the board
     *
     * @param rug The rug to place on the board.
     */
    public void placeRug(Rug rug){

    }
    //other board play methods like check the rugs place is valid or not...
    //other methods should be here...
}
