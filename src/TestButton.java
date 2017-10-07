import javax.swing.*;

public class TestButton extends JButton {
    boolean testingCheckmate;

    TestButton(boolean testingCheckMate) {
        super();
        this.testingCheckmate = testingCheckMate;
        this.setIcon(new ImageIcon(getClass().getResource("/resources/bug.png")));
    }
}
