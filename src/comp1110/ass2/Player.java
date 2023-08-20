package comp1110.ass2;

public class Player {
    //players' rug's color
    private String color;
    //players' left coins
    private int dirhams;
    //players' rugs quantity which is not placed on the board yet.
    private int rugsLeft;
    //initialized the players' information
    public Player(String color){
        this.color = color;
        this.dirhams = 30;
        this.rugsLeft = 15;
    }
    //other methods should be here...
}
