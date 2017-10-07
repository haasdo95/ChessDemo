import javax.swing.*;
import java.awt.*;

public class StartButton extends JButton {
    StartButton() {
        super();
        this.setEnabled(false);
        this.setForeground(Color.GREEN);
        this.setIcon(new ImageIcon(getClass().getResource("/resources/arrow.png")));
    }
}
