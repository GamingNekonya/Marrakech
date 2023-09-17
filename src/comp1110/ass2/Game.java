package comp1110.ass2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Game extends Application {

    public void start(Stage primaryStage) {
        // Use GridPane to create a 7*7 rectangular board
        GridPane pane = new GridPane();

        // use space to separate the board
        int recSize = 100;
        pane.setHgap(5.0);
        pane.setVgap(5.0);

        // add each rectangle to the board
        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                Rectangle rec = new Rectangle(recSize, recSize);
                if ((r + c) / 2 == 0) {
                    rec.setFill(Color.GOLDENROD);
                } else  {
                    rec.setFill(Color.GOLDENROD);

                }
                pane.add(rec, r, c);
            }
        }


        Scene scene = new Scene(pane, 750, 750);
        primaryStage.setScene(scene);


        primaryStage.setTitle("Board");
        primaryStage.show();
    }
}

