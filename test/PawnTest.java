import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test moving a pawn
 */
class PawnTest {
    @Test
    void testCanMoveToFirstMove() {
        Pawn pawn = new Pawn(4, 4, Player.THIS);
        assertTrue(pawn.canMoveTo(4, 6));
        assertTrue(pawn.canMoveTo(4, 5));
        assertFalse(pawn.canMoveTo(4, 3));
        assertFalse(pawn.canMoveTo(3, 4));
        assertFalse(pawn.canMoveTo(5, 4));
    }

    @Test
    void testCanMoveToMultipleMove() {
        Pawn pawn = new Pawn(4, 1, Player.THIS);
        pawn.moveTo(4, 2);
        assertTrue(pawn.canMoveTo(4, 3));
        assertFalse(pawn.canMoveTo(4, 4));
    }

    @Test
    void testCanCapture() {
        Pawn pawn = new Pawn(4, 1, Player.THIS);
        assertTrue(pawn.canMoveTo(5, 2));
        assertTrue(pawn.canMoveTo(3, 2));
    }

    @Test
    void testThatPlayerMove() {
        Pawn pawn = new Pawn(4, 4, Player.THAT);
        assertTrue(pawn.canMoveTo(4, 3));
        assertTrue(pawn.canMoveTo(4, 2));
        assertFalse(pawn.canMoveTo(4, 5));
        Pawn another = new Pawn(5, 6, Player.THAT);
        assertTrue(another.canMoveTo(5, 5));
    }

}