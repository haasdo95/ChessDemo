import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test the functionality of Pair class
 */
class PairTest {

    @Test
    void testGeneratePathDiag() {
        Pair start = new Pair(3, 3);
        Pair end = new Pair(7, 7);
        assertEquals(start.generatePath(end).size(), 4);
        end = new Pair(0, 0);
        assertEquals(start.generatePath(end).size(), 3);
    }

    @Test
    void testGeneratePathHorizontal() {
        Pair start = new Pair(4, 3);
        Pair end = new Pair(3, 3);
        assertEquals(start.generatePath(end).size(), 1);
    }

    @Test
    void testGeneratePathVertical() {
        Pair start = new Pair(3, 3);
        Pair end = new Pair(3, 5);
        assertEquals(start.generatePath(end).size(), 2);
        end = new Pair(3, 0);
        assertEquals(start.generatePath(end).size(), 3);
    }

    @Test
    void testEqualPair() {
        Pair p1 = new Pair(1, 1);
        assertTrue(p1.toString().equals("(1, 1)"));
        Pair p2 = new Pair(1, 2);
        Pair p3 = new Pair(1, 2);
        Object o = new Object();
        assertTrue(p2.equals(p3));
        assertFalse(p2.equals(p1));
        assertFalse(p2.equals(o));
    }

}