import javax.swing.*;

public class MagicButton extends JButton {
    MagicButton() {
        super();
        this.setIcon(new ImageIcon(getClass().getResource("/resources/magic.png")));
    }
}
