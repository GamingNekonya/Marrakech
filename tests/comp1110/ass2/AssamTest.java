package comp1110.ass2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssamTest {

    @Test
    public void testValidAssamString() {
        // Arrange
        String validAssamString = "A04N";

        // Act
        Assam assam = new Assam(validAssamString);

        // Assert
        assertEquals(0, assam.getX(), "Expected x-coordinate to be 0");
        assertEquals(4, assam.getY(), "Expected y-coordinate to be 4");
        assertEquals('N', assam.getOrientation(), "Expected orientation to be N");
    }

    @Test
    public void testInvalidAssamStringShort() {
        // Arrange
        String invalidAssamString = "A0N";

        // Act
        Assam assam = new Assam(invalidAssamString);

        // Assert
        // Here we can assert that the x, y and orientation are not set (they could be default values or invalid ones).
        // As an example, we're assuming they'll remain at default values, but this depends on your implementation.
        assertEquals(0, assam.getX(), "Expected x-coordinate to remain default");
        assertEquals(0, assam.getY(), "Expected y-coordinate to remain default");
        assertNotEquals('N', assam.getOrientation(), "Expected orientation to not be set");
    }

    @Test
    public void testInvalidAssamStringOrientation() {
        // Arrange
        String invalidAssamString = "A04Z"; // Z is not a valid orientation

        // Act
        Assam assam = new Assam(invalidAssamString);

        // Assert
        assertNotEquals('Z', assam.getOrientation(), "Expected orientation to not be Z");
    }
    @Test
    public void testAssamFacingSouth() {
        // Arrange
        String assamString = "A15S";

        // Act
        Assam assam = new Assam(assamString);

        // Assert
        assertEquals(1, assam.getX(), "Expected x-coordinate to be 1");
        assertEquals(5, assam.getY(), "Expected y-coordinate to be 5");
        assertEquals('S', assam.getOrientation(), "Expected orientation to be S");
    }

    @Test
    public void testAssamFacingWest() {
        // Arrange
        String assamString = "A61W";

        // Act
        Assam assam = new Assam(assamString);

        // Assert
        assertEquals(6, assam.getX(), "Expected x-coordinate to be 6");
        assertEquals(1, assam.getY(), "Expected y-coordinate to be 1");
        assertEquals('W', assam.getOrientation(), "Expected orientation to be W");
    }

    @Test
    public void testAssamStringTooLong() {
        // Arrange
        String longAssamString = "A12345N";

        // Act
        Assam assam = new Assam(longAssamString);

        // Assert
        assertNotEquals(1, assam.getX(), "Expected x-coordinate not to be 1");
        assertNotEquals(2, assam.getY(), "Expected y-coordinate not to be 2");
        assertNotEquals('N', assam.getOrientation(), "Expected orientation not to be N");
    }

    @Test
    public void testAssamInvalidCoordinate() {
        // Arrange
        String invalidCoordinateString = "A8A4N";

        // Act
        Assam assam = new Assam(invalidCoordinateString);

        // Assert
        assertNotEquals(8, assam.getX(), "Expected x-coordinate not to be 8");
        assertNotEquals(4, assam.getY(), "Expected y-coordinate not to be 4");
        assertNotEquals('N', assam.getOrientation(), "Expected orientation not to be N");
    }
}
