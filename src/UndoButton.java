import javax.swing.*;

public class UndoButton extends JButton {
    UndoButton() {
        super();
        this.setIcon(new ImageIcon(getClass().getResource("/resources/undo.png")));
    }
}
