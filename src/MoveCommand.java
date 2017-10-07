public class MoveCommand implements Command {
    private Piece[][] board;
    private Pair currLocation;
    private Pair newLocation;

    private Piece pieceMoved = null;
    private boolean pawnMoved = false;
    private boolean pawnFirstMove = false;
    private boolean khanSwitched = false;

    private Piece pieceCaptured = null;

    MoveCommand(Piece[][] board, Pair currLocation, Pair newLocation) {
        this.board = board;
        this.currLocation = currLocation;
        this.newLocation = newLocation;
    }

    @Override
    public Piece execute() {
        this.pieceMoved = board[currLocation.x][currLocation.y];
        // special cases
        this.pawnMoved = pieceMoved instanceof Pawn;
        if (pawnMoved) {
            this.pawnFirstMove = ((Pawn)pieceMoved).isFirstMove;
        }
        boolean isKhan = pieceMoved instanceof Khan;
        boolean captureNull = board[newLocation.x][newLocation.y] instanceof NullPiece;
        if (isKhan && !captureNull) {
            ((Khan)pieceMoved).switchSide();
            this.khanSwitched = true;
        }
        this.pieceCaptured = board[newLocation.x][newLocation.y];
        board[newLocation.x][newLocation.y] = pieceMoved;
        board[currLocation.x][currLocation.y] = new NullPiece(currLocation.x, currLocation.y);
        pieceMoved.moveTo(newLocation.x, newLocation.y);
        return pieceCaptured;
    }

    @Override
    public void unexecute() {
        this.pieceMoved.moveTo(currLocation.x, currLocation.y);
        this.board[currLocation.x][currLocation.y] = this.pieceMoved;
        if (pawnMoved) {
            ((Pawn)pieceMoved).isFirstMove = this.pawnFirstMove;
        }
        if (khanSwitched) {
            ((Khan)pieceMoved).switchSide(); // switch back
        }
        this.board[newLocation.x][newLocation.y] = this.pieceCaptured;
    }
}
