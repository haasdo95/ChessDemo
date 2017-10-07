import javax.swing.*;

public class ExitButton extends JButton {
    ExitButton() {
        super();
        this.setIcon(new ImageIcon(getClass().getResource("/resources/exit.png")));
    }
}
