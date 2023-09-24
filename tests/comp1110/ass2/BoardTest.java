package comp1110.ass2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {

    @Test
    void testEmptyBoard() {
        Board board = new Board();
        assertEquals(0, board.getColorAt(0, 0), "Expecting empty board at position (0,0)");
        assertEquals(0, board.getColorAt(6, 6), "Expecting empty board at position (6,6)");
    }

    @Test
    void testParseBoardStringEmpty() {
        Board board = new Board("B" + "n00".repeat(49));
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals(0, board.getColorAt(i, j), "Expecting empty board at position (" + i + "," + j + ")");
            }
        }
    }

    @Test
    void testParseBoardStringSingleRug() {
        Board board = new Board("B" + "n00".repeat(18) + "r01" + "n00".repeat(30));
        assertEquals(3, board.getColorAt(2, 4), "Expecting red rug at position (2,4)");
    }

    @Test
    void testAssamPosition() {
        Board board = new Board();
        Board.Position assam = board.getAssamPosition();
        assertEquals(4, assam.getX(), "Expecting Assam's X position to be 4");
        assertEquals(4, assam.getY(), "Expecting Assam's Y position to be 4");
        assertEquals('S', assam.getDirection(), "Expecting Assam to be facing South");
    }

}

