import javax.swing.*;
import java.awt.*;

public class ForfeitButton extends JButton {
    Player owner;
    ForfeitButton(Player owner) {
        this.owner = owner;
        if (owner == Player.THIS) {
            this.setForeground(Color.WHITE);
        } else {
            this.setForeground(Color.BLACK);
        }
        this.setIcon(new ImageIcon(getClass().getResource("/resources/white_flag.png")));
    }
}
