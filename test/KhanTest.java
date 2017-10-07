import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KhanTest {
    @Test
    void testSwitchSide() {
        Khan khan = new Khan(0, 0);
        khan.owner = Player.THAT;
        assertTrue(khan.toString().equals("✫"));
        khan.switchSide();
        assertTrue(khan.owner == Player.THIS);
        assertTrue(khan.toString().equals("✬"));
    }

}