import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessViewControllerTest {
    @Test
    void testGetBoard() {
        ChessViewController viewController = new ChessViewController();
        assertTrue(viewController.getBoard() != null);
    }

}