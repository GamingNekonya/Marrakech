package comp1110.ass2;


/**
 * Represents the game board of Marrakech.
 */
public class Board {
    private Position assamPosition;
    private int[][] rugBoard;
    private static final int BOARD_SIZE = 7;
    //2D array representing the board.
    /**
     * Constructor to initialize the game board.
     */
    public Board(){
        assamPosition = new Position(0, 0, 'N');
        rugBoard = new int[BOARD_SIZE][BOARD_SIZE];
        initRugBoard();
        //Sample initialization....
    }

    private void initRugBoard(){
        for (int i = 0; i <BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                rugBoard[i][j] = 0;
            }
        }
    }

    /**
     * place the assam in a position and clear the old position
     * @param x
     * @param y
     */
    public void placeAssam(int x, int y, char direction){
        if (x >= 0 && x < BOARD_SIZE && y >=0 && y < BOARD_SIZE){
            assamPosition = new Position(x, y, direction);
        }
        else {
            System.out.println("Invalid Assam position!");
        }
    }

    /**
     * Places a rug on the board
     *
     * @param rug The rug to place on the board.
     */
    public void placeRugBoard(String rug){
        char color = rug.charAt(0);
        int colorCode = getColorCode(color);
        int x1 = Character.getNumericValue(rug.charAt(4));
        int y1 = Character.getNumericValue(rug.charAt(5));
        int x2 = Character.getNumericValue(rug.charAt(6));
        int y2 = Character.getNumericValue(rug.charAt(7));
        if (isValidPosition(x1,y1)&& isValidPosition(x2,y2)){
            rugBoard[x1][y1] = colorCode;
            rugBoard[x2][y2] = colorCode;
        }
        else{
            System.out.println("Invalid rug position!");
        }
    }
    //other board play methods like check the rugs place is valid or not...
    //other methods should be here...

    private int getColorCode(char color){
        switch (color){
            case 'c' : return 1;
            case 'y' : return 2;
            case 'r' : return 3;
            case 'p' : return 4;
            default  : return 0;
        }
    }
    private boolean isValidPosition(int x, int y){
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    public Position getAssamPosition(){
        return assamPosition;
    }
    public static class Position{
        int x;
        int y;
        char direction;

        public Position (int x, int y, char direction){
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
        public char getDirection(){
            return direction;
        }
    }

}
