import javax.swing.*;

public class RestartButton extends JButton {
    RestartButton () {
        super();
        this.setIcon(new ImageIcon(getClass().getResource("/resources/restart.png")));
    }
}
