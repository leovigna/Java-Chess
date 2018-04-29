import static org.junit.Assert.*;
import org.junit.Test;

public class PositionTest {
    /* ********** NotationToPosition Tests ********** */
    @Test
    public void testNotationToPositionNull() {
        try {
            Board.notationToPosition(null);
            fail("Expected IllegalArgumentException - null input !");
        } catch (Board.SquareOutOfBoundsException e) {
            fail("Expected IllegalArgumentException - null input !");
        } catch (IllegalArgumentException e) {
            //Do nothing - it's supposed to throw this
        }
    }
    
    @Test
    public void testNotationToPositionInBounds() {
        try {
            Position position = Board.notationToPosition("a1");
            Position expected = new Position(0, 0);
            assertEquals(expected, position);
        } catch (Board.SquareOutOfBoundsException e) {
            fail("Test should not throw SquareOutOfBoundsException !");
        }
    }
    
    @Test
    public void testNotationToPositionOutOfBounds() {
        try {
            Board.notationToPosition("zz");
            fail("Expected SquareOutOfBoundsException - invalid input !");
        } catch (Board.SquareOutOfBoundsException e) {
          //Do nothing - it's supposed to throw this
        }
    }
    
    /* ********** IntToNotation Tests ********** */
    @Test
    public void testPositionToNotationInBounds() {
        try {
            Position position = new Position(0, 0);
            String notation = Board.positionToNotation(position);
            assertEquals("a1", notation);
        } catch (Board.SquareOutOfBoundsException e) {
            fail("Test should not throw SquareOutOfBoundsException !");
        }
    }
    
    @Test
    public void testIntToNotationOutOfBounds() {
        try {
            Position position = new Position(10, 10);
            Board.positionToNotation(position);
            fail("Test should not throw SquareOutOfBoundsException !");
        } catch (Board.SquareOutOfBoundsException e) {
          //Do nothing - it's supposed to throw this
        }
    }

}
