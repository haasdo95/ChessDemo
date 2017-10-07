import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class implements the Visitor pattern.
 * A major design difficulty here, is to let allowMove be polymorphic, yet provide support for various
 * pieces without explicitly using instanceof.
 */
public class Board implements Visitor {

    Piece[][] board;
    King thisKing;
    King thatKing;

    private Stack<MoveCommand> history = new Stack<>();

    /**
     * default constructor to construct the board as a standard starting board
     */
    Board () {
        this.board = new Piece[8][8];
        initStandard();
    }

    Board (BoardConfig boardConfig) {
        this.board = new Piece[8][8];
        switch (boardConfig) {
            case STANDARD:
                initStandard();
                break;
            case MAGIC:
                initMagic();
                break;
            case CHECK_MATE_TEST:
                initCheckmate();
                break;
            case STALEMATE_TEST:
                initStalemate();
                break;
            default:
                break;
        }
    }

    /**
     * method provided for the view controller to channel to view to draw board
     * @return the current board state
     */
    Piece[][] getBoard() {
        return this.board;
    }

    private void initStandard() {
        this.thisKing = new King(4, 0, Player.THIS);
        this.thatKing = new King(4, 7, Player.THAT);
        // put in NullPieces
        for (int x = 0; x < 8; x++) {
            for (int y = 2; y < 6; y++) {
                this.board[x][y] = new NullPiece(x, y);
            }
        }
        // put in pieces
        putInPlayerPieces(Player.THIS);
        putInPlayerPieces(Player.THAT);
        // put in kings
        this.board[4][0] = this.thisKing;
        this.board[4][7] = this.thatKing;
    }

    private void initMagic () {
        initStandard();
        Khan khan = new Khan(7, 4);
        if (khan.owner == Player.THAT) {
            khan.location = new Pair(7, 3);
        }
        this.board[khan.location.x][khan.location.y] = khan;
        this.board[0][0] = new Cannon(0, 0, Player.THIS);
        this.board[0][7] = new Cannon(0, 7, Player.THAT);
    }

    private void initStalemate () {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.board[x][y] = new NullPiece(x, y);
            }
        }
        this.thatKing = new King(4, 0, Player.THAT);
        this.board[4][0] = this.thatKing;
        this.thisKing = new King(7, 7, Player.THIS);
        this.board[7][7] = this.thisKing;
        this.board[5][5] = new Queen(5, 5, Player.THAT);
    }

    private void initCheckmate () {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.board[x][y] = new NullPiece(x, y);
            }
        }
        this.thisKing = new King(7, 0, Player.THIS);
        this.board[7][0] = thisKing;
        this.thatKing = new King(7, 7, Player.THAT);
        this.board[7][7] = thatKing;
        board[0][1] = new Rook(0, 1, Player.THAT);
        board[1][2] = new Rook(1, 2, Player.THAT);
    }

    /**
     * a temp method to do pretty-print
     */
    @Override
    public String toString() {
        String retValue = "";
        for (int y = 0; y < 8; y++) {
            retValue += "|";
            for (int x = 0; x < 8; x++) {
                retValue += this.getPiece(x, y).toString();
                retValue += "\t";
                retValue += "|";
            }
            retValue += "\n";
        }
        return retValue;
    }

    /**
     * helper function used by constructor to fill in the side of board as specified by "player"
     * @param player: the player whose side will be filled out.
     */
    private void putInPlayerPieces(Player player) {
        int thisY;
        int pawnsY;
        if (player == Player.THIS) {
            thisY = 0;
            pawnsY = 1;
        } else {
            thisY = 7;
            pawnsY = 6;
        }
        // rooks
        this.board[0][thisY] = new Rook(0, thisY, player);
        this.board[7][thisY] = new Rook(0, thisY, player);
        // knights
        this.board[1][thisY] = new Knight(1, thisY, player);
        this.board[6][thisY] = new Knight(6, thisY, player);
        // bishops
        this.board[2][thisY] = new Bishop(2, thisY, player);
        this.board[5][thisY] = new Bishop(5, thisY, player);
        // queen
        this.board[3][thisY] = new Queen(3, thisY, player);
        // pawns
        for (int x = 0; x < 8; x++) {
            this.board[x][pawnsY] = new Pawn(x, pawnsY, player);
        }
    }

    /**
     *
     * @param player
     * @return a list of all pieces of player
     */
    private ArrayList<Piece> getPiecesOf(Player player) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (this.getPiece(x, y).owner == player) {
                    pieces.add(this.getPiece(x, y));
                }
            }
        }
        return pieces;
    }

    /**
     *
     * @param player
     * @return true if the player has any legal move
     */
    private boolean hasLegalMove(Player player) {
        ArrayList<Piece> allies = this.getPiecesOf(player);
        // perform all possible moves for our allies (and rollback)
        for (int allyRunner = 0; allyRunner < allies.size(); allyRunner++) {
            Piece ally = allies.get(allyRunner);
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (this.allowMove(ally, x, y)) {
//                        System.out.println("(" + x + ", " + y + ") works!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * if "the king is currently not in check and there is no legal move"
     * try all legal moves of all pieces, in order to find one that doesn't endanger the king.
     * @param player the player of the turn
     * @return true if the player is in stalemate
     */
    boolean isInStalemate (Player player) {
        if (this.isInCheck(player)) return false; // someone is attacking
        return !this.hasLegalMove(player);
    }

    /**
     * the king is in check and there is no legal move that could remedy this.
     * @param player player of the turn
     * @return true if the player is in checkmate
     */
    boolean isInCheckmate (Player player) {
        if (!this.isInCheck(player)) return false; // nobody is attacking
        return !this.hasLegalMove(player);
    }

    /**
     * will be called only on king location
     * @param player player of the turn
     * @param location the location we are studying
     * @return a list of pieces that could potentially touch "location"
     */
    private ArrayList<Piece> getAttackers(Player player, Pair location) {
        Player opponent = Player.THAT;
        if (player == Player.THAT) {
            opponent = Player.THIS;
        }
        ArrayList<Piece> attackers = new ArrayList<>();
        ArrayList<Piece> opponents = this.getPiecesOf(opponent); // get all opponent pieces
        for (int oppoRunner = 0; oppoRunner < opponents.size(); oppoRunner++) {
            Piece opponentPiece = opponents.get(oppoRunner);
            if (this.allowMoveNoWorryKing(opponentPiece, location.x, location.y)) {
                attackers.add(opponentPiece);
            }
        }
        return attackers;
    }

    /**
     * @param player the owner of the king
     * @return true if the player is in check.
     */
    boolean isInCheck(Player player) {
        King king;
        if (player == Player.THIS) {
            king = this.thisKing;
        } else {
            king = this.thatKing;
        }
        return this.getAttackers(player, king.location).size() != 0;
    }

    Piece getPiece(Pair location) {
        return this.getPiece(location.x, location.y);
    }
    private Piece getPiece(Integer x, Integer y) {
        return this.board[x][y];
    }

    /**
     * if a player could move a piece from the current location to a new location
     * @param player the player to make to move
     * @param currLocation the current location of the piece
     * @param newLocation the destination
     * @return true if he can potentially move it
     */
    boolean allowMove(Player player, Pair currLocation, Pair newLocation) {
        if (player != this.getPiece(currLocation).owner) { // not your piece!
            return false;
        }
        return this.allowMove(currLocation, newLocation);
    }

    /**
     * determine if it is possible to move from currLocation to newLocation
     * without being blocked or breaking rules
     * owner of the piece does not matter here.
     * @param currLocation the current location of the piece
     * @param newLocation the destination
     * @return true if we technically can
     */
    private boolean allowMove(Pair currLocation, Pair newLocation) {
        return this.allowMove(this.getPiece(currLocation), newLocation.x, newLocation.y);
    }

    /**
     * determine if it is possible to move a piece to newLocation
     * without being blocked or breaking rules
     * @param piece the piece to move
     * @param newX destination X
     * @param newY destination Y
     * @return true if we can
     */
    private boolean allowMove(Piece piece, Integer newX, Integer newY) {
        return this.allowMoveNoWorryKing(piece, newX, newY) && this.kingIsSafeAfterMove(piece, newX, newY);
    }

    /**
     * test movability without checking king danger
     * used only in getAttackers
     */
    private boolean allowMoveNoWorryKing (Piece piece, Integer newX, Integer newY) {
        if (!piece.canMoveTo(newX, newY)) {
            return false; // not even a legal move
        }
        return piece.accept(this, newX, newY);
    }

    /**
     * method to undo the most recent move
     * @return true if the stack is not empty
     */
    boolean undo() {
        try {
            MoveCommand undoneMove = this.history.pop();
            undoneMove.unexecute();
            return true;
        } catch (EmptyStackException e) { // report failure if stack is empty
            return false;
        }
    }

    /**
     * method used to move a piece and push it onto history stack
     * @return captured piece
     */
    Piece moveFromTo (Pair currLocation, Pair newLocation) {
        MoveCommand moveCommand = new MoveCommand(this.board, currLocation, newLocation);
        this.history.push(moveCommand);
        return moveCommand.execute();
    }

    /**
     * @deprecated
     * actually move a piece from the current location to the new location
     * @return the piece that gets captured (can be a NullPiece)
     */
    @Deprecated
    protected Piece moveFromTo (int currX, int currY, int newX, int newY) {
        return this.moveFromTo(new Pair(currX, currY), new Pair(newX, newY));
    }

    /**
     * takes a "regular piece", walks along the path, sees if there is anything blocking
     * @return true if there is nothing blocking
     */
    private boolean gropeBoard (RegularPiece regularPiece, Integer newX, Integer newY) {
        ArrayList<Pair> path = regularPiece.location.generatePath(new Pair(newX, newY));
        for (int runner = 1; runner < path.size(); runner++) { // iterate in between
            Pair coord = path.get(runner);
            Piece curr = this.board[coord.x][coord.y];
            if (! (curr instanceof NullPiece)) { // something is in our way!!!
                return false;
            }
        }
        return true;
    }

    /**
     * @param piece the piece to capture others
     * @return true if the piece can capture the piece at the destination
     */
    private boolean canCapture (Piece piece, Integer newX, Integer newY) {
        return piece.owner != this.board[newX][newY].owner;
    }

    /**
     * a method to determine if a move made by any piece would endanger its king
     * @param piece the piece to move
     * @return true if the king remains safe after the move
     */
    private boolean kingIsSafeAfterMove(Piece piece, Integer newX, Integer newY) {
        King king;
        if (piece.owner == Player.THIS) {
            king = this.thisKing;
        } else {
            king = this.thatKing;
        }
        Player pieceOwner = piece.owner;
        Pair currLocation = piece.location;
        Pair newLocation = new Pair(newX, newY);
        MoveCommand moveCommand = new MoveCommand(this.board, currLocation, newLocation);
        moveCommand.execute();
        ArrayList<Piece> attackers = this.getAttackers(pieceOwner, king.location);
        boolean retValue = attackers.size() == 0;
        moveCommand.unexecute();
        return retValue;
    }

    /**
     * board support for "regular pieces".
     * @return true if there is nothing to prevent a "regular piece" from reaching destination
     */
    @Override
    public boolean visit(RegularPiece regularPiece, Integer newX, Integer newY) {
        boolean noBlock = this.gropeBoard(regularPiece, newX, newY);
        return noBlock && this.canCapture(regularPiece, newX, newY);
    }

    /**
     * board support for pawn
     * @return true if there is nothing to prevent a pawn from reaching destination
     */
    @Override
    public boolean visit(Pawn pawn, Integer newX, Integer newY) {
        boolean retValue;
        int playerMult = 1; // used to tell THIS from THAT
        if (pawn.owner == Player.THAT) {
            playerMult = -1;
        }
        if (newY.equals(pawn.location.y + 2 * playerMult)) { // going two steps
            retValue = (this.board[newX][pawn.location.y + playerMult] instanceof NullPiece)
                    && (this.board[newX][pawn.location.y + 2 * playerMult] instanceof NullPiece);
        }
        else if (newX.equals(pawn.location.x) && newY.equals(pawn.location.y + playerMult)) { // going one step
            retValue = (this.board[newX][pawn.location.y + playerMult] instanceof NullPiece);
        }
        else { // capturing!
            retValue = (!(this.board[newX][newY] instanceof NullPiece)) && this.canCapture(pawn, newX, newY);
        }
        return retValue;
    }

    @Override
    public boolean visit(Knight knight, Integer newX, Integer newY) {
        return this.canCapture(knight, newX, newY);
    }

    @Override
    public boolean visit(King king, Integer newX, Integer newY) {
        return this.canCapture(king, newX, newY);
    }

    /**
     * a cannon moves just like a rook, however, it cannot capture like a rook
     * a cannon needs exactly one supporter to fire
     * @return true if the board allows the cannon to move to the new location
     */
    @Override
    public boolean visit(Cannon cannon, Integer newX, Integer newY) {
        boolean capturing = !(this.getPiece(newX, newY) instanceof NullPiece) && this.canCapture(cannon, newX, newY);
        ArrayList<Pair> path = cannon.location.generatePath(new Pair(newX, newY));
        int numberOfBlockingPieces = 0;
        for (int i = 1; i < path.size(); i++) {
            Piece piece = this.getPiece(path.get(i));
            if (! (piece instanceof NullPiece)) {
                numberOfBlockingPieces++;
            }
        }
        return (numberOfBlockingPieces == 1 && capturing)  // a cannon needs one supporter to fire
                || (numberOfBlockingPieces == 0 && !capturing); // moving like rooks
    }

    @Override
    public boolean visit(Khan khan, Integer newX, Integer newY) {
        if (khan.location.x.equals(newX) || khan.location.y.equals(newY)
                || (Math.abs(khan.location.x - newX) == Math.abs(khan.location.y - newY))) { // move like queen
            boolean noBlock = this.gropeBoard(khan, newX, newY);
            return noBlock && this.canCapture(khan, newX, newY);
        } else { // move like knight
            return this.canCapture(khan, newX, newY);
        }
    }

    /**
     *  a null piece can't go anywhere
     */
    @Override
    public boolean visit(NullPiece nullPiece, Integer newX, Integer newY) {
        return false;
    }
}
