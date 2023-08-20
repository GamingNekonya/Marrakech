package comp1110.ass2;

public class Board {
    //define a 7*7 board
    private String[][] grid = new String[7][7];
    //initialized the board, all position should be empty
    public Board(){
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                grid[i][j] = null;
            }
        }
    }
    public void initializeFromBoardString(String boardString){

    }
    //other board play methods like check the rugs place is valid or not...
    //other methods should be here...
}
