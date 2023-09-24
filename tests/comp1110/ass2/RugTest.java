package comp1110.ass2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RugTest {

    @Test
    void testValidRugString() {
        Rug rug = new Rug("c014445");
        assertEquals('c', rug.getColor());
        assertEquals(1, rug.getId());
        assertEquals(4, rug.getX1());
        assertEquals(4, rug.getY1());
        assertEquals(4, rug.getX2());
        assertEquals(5, rug.getY2());
    }

    @Test
    void testInvalidRugStringLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rug("c01444");
        });
    }

    @Test
    void testInvalidColor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rug("z014445");
        });
    }
    @Test
    void testInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rug("c01-1234");  // x1 is a negative number
        });
    }
}