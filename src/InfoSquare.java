import javax.swing.*;
import java.awt.*;

public class InfoSquare extends JPanel {
    private JLabel thatLabel;
    private JLabel thisLabel;

    InfoSquare(JLabel turnIndicator) {
        this.setLayout(new GridLayout(2, 1));
        this.add(turnIndicator);
        JPanel scorePanel = new JPanel(new GridLayout(1, 2));
        int thatScore = 0;
        int thisScore = 0;
        // set up "THAT"
        JPanel thatPanel = new JPanel();
        this.thatLabel = new JLabel("" + thatScore, SwingConstants.CENTER);
        thatLabel.setForeground(Color.WHITE);
        thatPanel.add(thatLabel, CENTER_ALIGNMENT);
        thatPanel.setBackground(Color.BLACK);
        // set up "THIS"
        JPanel thisPanel = new JPanel();
        this.thisLabel = new JLabel("" + thisScore, SwingConstants.CENTER);
        thisLabel.setForeground(Color.BLACK);
        thisPanel.add(thisLabel, CENTER_ALIGNMENT);
        thisPanel.setBackground(Color.WHITE);

        scorePanel.add(thatPanel);
        scorePanel.add(thisPanel);
        this.add(scorePanel);
    }

    void updateScore (Pair score) {
        this.thatLabel.setText("" + score.x);
        this.thisLabel.setText("" + score.y);
    }
}
