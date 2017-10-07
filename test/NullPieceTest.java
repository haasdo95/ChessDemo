import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NullPieceTest {
    @Test
    void canMoveTo() {
        NullPiece np = new NullPiece(0, 0);
        assertTrue(np.toString().equals(""));
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                assertFalse(np.canMoveTo(x, y));
            }
        }
    }

}