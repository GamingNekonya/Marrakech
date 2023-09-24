package comp1110.ass2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RugTest {

    @Test
    void testValidRugString() {
        Rug rug = new Rug("r012324");
        assertEquals('r', rug.getColor());
        assertEquals(2, rug.getX1());
        assertEquals(3, rug.getY1());
        assertEquals(2, rug.getX2());
        assertEquals(4, rug.getY2());
    }

    @Test
    void testInvalidRugStringLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rug("r0123245");
        });
    }

    @Test
    void testInvalidColor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rug("q012324");
        });
    }

    @Test
    void testInvalidId(){
        assertThrows(IllegalArgumentException.class, () ->{
            new Rug("r304445");
        });
    }
    @Test
        // y1 is a negative number
    void testInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rug("c011-234");
        });
    }
}