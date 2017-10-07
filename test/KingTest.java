import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test moving a king
 */
class KingTest {

    @Test
    void testCanMoveTo() {
        King king = new King(4, 4, Player.THIS);

        assertTrue(king.canMoveTo(4, 5));
        assertTrue(king.canMoveTo(5, 5));
        assertTrue(king.canMoveTo(5, 4));
        assertTrue(king.canMoveTo(4, 3));
        assertTrue(king.canMoveTo(3, 3));
        assertTrue(king.canMoveTo(3, 4));
        assertTrue(king.canMoveTo(5, 3));
        assertTrue(king.canMoveTo(3, 5));

        assertFalse(king.canMoveTo(4, 6));
        assertFalse(king.canMoveTo(4, 4));
    }

}