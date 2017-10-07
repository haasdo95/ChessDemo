import javax.swing.*;
import java.awt.*;

/**
 * This class displays the chess UI
 */
public class ChessView extends JFrame {

    private JButton[][] chessBoardTiles;
    private JLabel turnIndicator;
    private JPanel chessBoard;
    private InfoSquare infoSquare;
    private StartButton startButton;
    private ChessViewController viewController;

    ChessView (String title, ChessViewController chessViewController) {
        super(title);
        this.viewController = chessViewController;
        bootstrap();
    }

    /**
     * method to draw the UI
     */
    private void bootstrap () {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        this.setSize(640, 640);
        this.initializeChessBoardTiles();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * draw the board according to the board passed in
     */
    void drawBoard(Piece[][] board) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.chessBoardTiles[x][y].setText(board[x][y].toString());
            }
        }
    }

    /**
     * turn on or off all the chessboard tiles
     * @param shouldBeOn pass in true to turn all on
     */
    void toggleAll (boolean shouldBeOn) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.chessBoardTiles[x][y].setEnabled(shouldBeOn);
            }
        }
    }

    /**
     * update player score info
     */
    void updateScore (Pair newScore) {
        this.infoSquare.updateScore(newScore);
    }

    /**
     * initialize the chess board as well as put in the initial pieces
     */
    private void initializeChessBoardTiles() {
        turnIndicator = new JLabel("B", SwingConstants.CENTER);
        turnIndicator.setFont(new Font("Helvetica", Font.ITALIC, 20));

        // put in pieces
        this.chessBoardTiles = new JButton[8][8];
        Insets tileMargin = new Insets(0,0,0,0);
        // paint black and white & basic settings
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton tile = new ChessTileButton(new Pair(x, y));
                tile.setMargin(tileMargin);
                tile.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
                tile.setBorderPainted(false);
                tile.setOpaque(true);
                tile.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
                tile.addActionListener(this.viewController);
                if ((x + y) % 2 == 0) { // white
                    tile.setBackground(Color.WHITE);
                } else {
                    tile.setBackground(Color.LIGHT_GRAY);
                }
                chessBoardTiles[x][y] = tile;
            }
        }

        this.infoSquare = new InfoSquare(this.turnIndicator);
        this.infoSquare.updateScore(new Pair(0, 0));
        this.drawBoard(this.viewController.getBoard()); // initial drawing

        // fill in tiles
        this.chessBoard = new JPanel(new GridLayout(9, 9));
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (x == 0) { // filling out A, B. C...
                    if (y == 0) { // top left square. indicate whose turn it is and score
                        chessBoard.add(this.infoSquare);
                    } else {
                        chessBoard.add(new JLabel(Character.toString((char)('A' + y - 1)), SwingConstants.CENTER));
                    }
                }
                else if (y == 0) { // filling out 1, 2, 3...
                    if (x == 1) {
                        ForfeitButton thisForfeitButton = new ForfeitButton(Player.THIS);
                        thisForfeitButton.addActionListener(this.viewController);
                        chessBoard.add(thisForfeitButton);
                    }
                    else if (x == 2) {
                        ExitButton eb = new ExitButton();
                        eb.addActionListener(this.viewController);
                        chessBoard.add(eb);
                    }
                    else if (x == 3) {
                        RestartButton rb = new RestartButton();
                        rb.addActionListener(this.viewController);
                        chessBoard.add(rb);
                    }
                    else if (x == 4) {
                        this.startButton = new StartButton();
                        startButton.addActionListener(this.viewController);
                        chessBoard.add(startButton);
                    }
                    else if (x == 5) {
                        UndoButton ub = new UndoButton();
                        ub.addActionListener(this.viewController);
                        chessBoard.add(ub);
                    }
                    else if (x == 6) {
                        MagicButton mb = new MagicButton();
                        mb.addActionListener(this.viewController);
                        chessBoard.add(mb);
                    }
                    else if (x == 7) {
                        TestButton tb = new TestButton(true);
                        tb.addActionListener(this.viewController);
                        chessBoard.add(tb);
                    }
                    else {
                        ForfeitButton thatForfeitButton = new ForfeitButton(Player.THAT);
                        thatForfeitButton.addActionListener(this.viewController);
                        chessBoard.add(thatForfeitButton);
                    }
                }
                else { // filling out normal tiles
                    chessBoard.add(chessBoardTiles[y - 1][x - 1]);
                }
            }
        }
        this.setContentPane(chessBoard);
    }

    /**
     * get rid of the highlight effect at (selectedX, selectedY)
     */
    void toggleTileBorder(int selectedX, int selectedY) {
        this.chessBoardTiles[selectedX][selectedY].setBorderPainted(false);
    }

    /**
     * update the turn indicator text
     */
    void changeUITurn(Player playerOfTheTurn) {
        if (playerOfTheTurn == Player.THIS) {
            this.turnIndicator.setText("W");
        } else {
            this.turnIndicator.setText("B");
        }
    }

    /**
     * highlight the king in case of check
     * @param kingInCheck 2-d tuple indicating king status
     * @param kingInCheckMate 2-d tuple indicating king status
     */
    void renderKingStatus (boolean[] kingInCheck, boolean[] kingInCheckMate) {
        boolean thatKingInCheck = kingInCheck[0];
        boolean thisKingInCheck = kingInCheck[1];
        boolean thatKingInCheckMate = kingInCheckMate[0];
        boolean thisKingInCheckMate = kingInCheckMate[1];
        if (thatKingInCheckMate) {
            inCheckEffect("♚", true);
            this.startButton.setEnabled(true);
            this.toggleAll(false);
        }
        else if (thisKingInCheckMate) {
            inCheckEffect("♔", true);
            this.startButton.setEnabled(true);
            this.toggleAll(false);
        }
        else if (thatKingInCheck) {
            inCheckEffect("♚", false);
        }
        else if (thisKingInCheck) {
            inCheckEffect("♔", false);
        }
    }

    /**
     * highlight the king in case of check(or checkmate)
     */
    private void inCheckEffect (String kingChar, boolean isInMate) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton tile = this.chessBoardTiles[x][y];
                if (tile.getText().equals(kingChar)) {
                    Color newColor = Color.YELLOW;
                    if (isInMate) {
                        newColor = Color.RED;
                    }
                    setBkgdAndChangeBack(tile, newColor);
                }
            }
        }
    }

    /**
     * use a bkgd thread to achieve the timeout effect
     */
    void setBkgdAndChangeBack(JButton tile, Color newColor) {
        Color oldBkgd = tile.getBackground();
        tile.setBackground(newColor);
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tile.setBackground(oldBkgd);
        }).start();
    }

}
