import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ChessViewController implements ActionListener {
    /**
     * Game state
     */
    // selected pair by a player in the UI
    private Pair selected = null;
    private Player playerOfTheTurn = Player.THAT;
    private Pair score = new Pair(0, 0);
    private boolean[] kingInCheck = {false, false};
    private boolean[] kingInCheckMate = {false, false};

    /**
     * MVC architecture
     */
    private ChessView view;
    private Board boardModel;

    ChessViewController() {
        this.boardModel = new Board();
        view = new ChessView("Chess", this);
    }

    // provided for the view to repaint board accordingly
    Piece[][] getBoard() {
        return this.boardModel.getBoard();
    }

    /**
     * action listener method. listening on button pressing
     * @param event event listened
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object eventEmitter = event.getSource();
        if (eventEmitter instanceof ChessTileButton) {
            handleChessBoardEvent((ChessTileButton) eventEmitter);
        }
        else if (eventEmitter instanceof ForfeitButton) {
            ForfeitButton fb = (ForfeitButton)eventEmitter;
            handleVictory(this.getOpponent(fb.owner));
            resetBoard();
        }
        else if (eventEmitter instanceof StartButton) {
            StartButton sb = (StartButton)eventEmitter;
            view.toggleAll(true);
            sb.setEnabled(false);
            resetBoard();
        }
        else if (eventEmitter instanceof RestartButton) {
            int agreeRestart = JOptionPane.showConfirmDialog(view, "Would you accept it?", "Your opponent wants to restart", JOptionPane.YES_NO_OPTION);
            if (agreeRestart == JOptionPane.YES_OPTION) {
                this.score = new Pair(0, 0);
                resetBoard();
            }
        }
        else if (eventEmitter instanceof UndoButton) {
            boolean isUndoSuccessful = this.boardModel.undo();
            if (isUndoSuccessful)
                this.nextTurn();
        }
        else if (eventEmitter instanceof MagicButton) {
            this.boardModel = new Board(BoardConfig.MAGIC);
        }
        else if (eventEmitter instanceof TestButton) {
            boolean isTestingCheckMate = ((TestButton)eventEmitter).testingCheckmate;
            if (isTestingCheckMate) {
                this.boardModel = new Board(BoardConfig.CHECK_MATE_TEST);
            } else {
                this.boardModel = new Board(BoardConfig.STALEMATE_TEST);
            }
            ((TestButton)eventEmitter).testingCheckmate = !isTestingCheckMate;
        }
        else if (eventEmitter instanceof ExitButton) {
            view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
        }
        // UI rendering
        this.view.drawBoard(this.getBoard());
        this.view.updateScore(this.score);
        this.view.changeUITurn(this.playerOfTheTurn);
        this.view.renderKingStatus(this.kingInCheck, this.kingInCheckMate);
    }

    /**
     * handler method to handle a move or a select on the board
     * @param eventEmitter the tile to move to or the tile selected
     */
    private void handleChessBoardEvent(ChessTileButton eventEmitter) {
        ChessTileButton source = eventEmitter;
        if (this.selected == null) { // making the selection
            handleSelect(source);
        } else {
            // move & check logic come here
            if (!source.location.equals(selected))
                handleMove(source);
            else { // tapping the same location twice to cancel selection
                selected = null;
                source.setBorderPainted(false);
            }
        }
    }

    /**
     * reset all the game state
     */
    private void resetBoard() {
        this.playerOfTheTurn = Player.THAT; // reset starting player
        this.kingInCheck[0] = false;
        this.kingInCheck[1] = false;
        this.kingInCheckMate[0] = false;
        this.kingInCheckMate[1] = false;
        this.boardModel = new Board();
    }

    /**
     * add one point to the winner
     */
    private void handleVictory(Player winner) {
        if (winner == Player.THAT) {
            this.score.x = this.score.x + 1;
        } else {
            this.score.y = this.score.y + 1;
        }
    }

    /**
     * handle a select on the board
     * @param source the tile selected
     */
    private void handleSelect(ChessTileButton source) {
        this.selected = source.location;
        source.setBorderPainted(true); // show a border
    }

    /**
     * handler method to handle a move on the board
     * @param source the tile to move to
     */
    private void handleMove(ChessTileButton source) {
        boolean isValidMove = this.boardModel.allowMove(this.playerOfTheTurn, this.selected, source.location);
        if (isValidMove) {
            boolean isGameEnded = false;
            this.boardModel.moveFromTo(this.selected, source.location);
            this.kingInCheck[0] = this.boardModel.isInCheck(Player.THAT);
            this.kingInCheck[1] = this.boardModel.isInCheck(Player.THIS);
            // handle endgame logic
            Player opponent = this.getOpponent(this.playerOfTheTurn);
            if (this.boardModel.isInCheckmate(opponent)) { // checkmate
                this.handleVictory(this.playerOfTheTurn);
                isGameEnded = true;
                if (opponent == Player.THAT) {
                    this.kingInCheckMate[0] = true;
                } else {
                    this.kingInCheckMate[1] = true;
                }
            }
            if (this.boardModel.isInStalemate(this.playerOfTheTurn)
                    || this.boardModel.isInStalemate(this.getOpponent(this.playerOfTheTurn))) // stalemate
            {
                this.resetBoard(); // call it a draw. reset board without incrementing score
                isGameEnded = true;
            }
            if (!isGameEnded) {
                this.nextTurn();
            } else { // reset the player of the turn to THAT if starting a new game
                this.playerOfTheTurn = Player.THAT;
            }
        } else { // indicate an invalid move
            this.view.setBkgdAndChangeBack(source, Color.RED);
        }
        // update UI
        this.view.toggleTileBorder(this.selected.x, this.selected.y); // toggle the border
        this.selected = null;
    }

    /**
     * change game state to next turn
     */
    private void nextTurn () {
        if (this.playerOfTheTurn == Player.THIS) {
            this.playerOfTheTurn = Player.THAT;
        } else {
            this.playerOfTheTurn = Player.THIS;
        }
    }

    private Player getOpponent (Player player) {
        if (player == Player.THIS) {
            return Player.THAT;
        } else {
            return Player.THIS;
        }
    }

    public static void main (String[] args) {
        new ChessViewController();
    }

}
