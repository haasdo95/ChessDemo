import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the functionality of board to detect illegal moves, stalemate, and checkmate
 */
class BoardTest extends Board {

    @Test
    void testCreateDifferentBoards () {
        Board b1 = new Board(BoardConfig.STANDARD);
        Board b2 = new Board(BoardConfig.MAGIC);
        Board b3 = new Board(BoardConfig.CHECK_MATE_TEST);
        Board b4 = new Board(BoardConfig.STALEMATE_TEST);
    }

    @Test
    void testStalemateNaive() {
        System.out.println("TESTING NAIVE STALEMATE");
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.board[x][y] = new NullPiece(x, y);
            }
        }
        this.thisKing = new King(4, 0, Player.THIS);
        this.board[4][0] = this.thisKing;
        this.thatKing = new King(7, 7, Player.THAT);
        this.board[7][7] = this.thatKing;
        this.board[6][5] = new Queen(6, 5, Player.THIS);
        System.out.println(this);
        assertTrue(this.isInStalemate(Player.THAT));
    }

    @Test
    void testStalemateHard() {
        System.out.println("TESTING HARD STALEMATE");
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.board[x][y] = new NullPiece(x, y);
            }
        }
        this.thatKing = new King(7, 1, Player.THAT);
        this.thisKing = new King(7, 7, Player.THIS);
        this.board[4][1] = new Rook(4, 1, Player.THIS);
        this.board[3][0] = new Queen(3, 0, Player.THIS);
        this.board[6][1] = new Pawn(6, 1, Player.THAT);
        this.board[7][2] = new Pawn(7, 2, Player.THAT);
        this.board[7][3] = new Pawn(7, 3, Player.THIS);
        this.board[5][4] = new Knight(5, 4, Player.THIS);
        this.board[7][1] = thatKing;
        this.board[7][7] = thisKing;
        System.out.println(this);
        assertTrue(this.isInStalemate(Player.THAT));
    }

    @Test
    void testIsInCheckmateNaive() {
        System.out.println("TESTING NAIVE CHECKMATE");
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.board[x][y] = new NullPiece(x, y);
            }
        }
        this.thatKing = new King(7, 0, Player.THAT);
        this.thisKing = new King(7, 7, Player.THIS);
        this.board[7][0] = thatKing;
        this.board[7][7] = thisKing;
        this.board[0][0] = new Rook(0, 0, Player.THIS);
        this.board[3][1] = new Rook(3, 1, Player.THIS);
        System.out.println(this);
        assertTrue(this.isInCheckmate(Player.THAT));

        this.moveFromTo(7, 0, 7, 1);
        System.out.println("KING CAN ESCAPE");
        System.out.println(this);
        assertTrue(this.allowMove(Player.THAT, new Pair(7, 1), new Pair(7, 2)));
        assertFalse(this.isInCheckmate(Player.THAT));

        // move back
        this.moveFromTo(7, 1, 7, 0);
        // adding reinforcement
        this.board[5][4] = new Rook(5, 4, Player.THAT);
        System.out.println("ROOK CAN SHIELD");
        System.out.println(this);
        assertTrue(this.allowMove(Player.THAT, new Pair(5, 4), new Pair(5, 0)));
        assertFalse(this.isInCheckmate(Player.THAT));

        // remove reinforcement
        this.board[5][4] = new NullPiece(5, 4);
        // can take out attacker
        this.board[0][3] = new Rook(0, 3, Player.THAT);
        System.out.println("ROOK CAN TAKE OUT ATTACKER");
        System.out.println(this);
        assertFalse(this.isInCheckmate(Player.THAT));

        // remove reinforcement
        this.board[0][3] = new NullPiece(0, 3);
//        // add reinforcement
//        this.board[0][3] = new Khan(0, 3);
//        this.board[0][3].owner = Player.THAT; // remove randomness
//        this.board[1][2] = new Pawn(1, 2, Player.THIS); // to block khan
//        System.out.println("KHAN CANNOT HELP");
//        System.out.println(this);
//        assertFalse(this.allowMove(Player.THAT, new Pair(0, 3), new Pair(0, 0)));
//        assertTrue(this.isInCheckmate(Player.THAT));
//        this.board[1][2] = new NullPiece(1, 2);


        // remove reinforcement
        this.board[0][3] = new NullPiece(0, 3);
        // a cannon alone won't work
        this.board[0][3] = new Cannon(0, 3, Player.THAT);
        System.out.println("CANNON ALONE CANNOT TAKE OUT ATTACKER");
        System.out.println(this);
        assertTrue(this.isInCheckmate(Player.THAT));

        // adding cannon support
        this.board[0][1] = new Queen(0, 1, Player.THIS);
        System.out.println("CANNON WITH SUPPORTER CAN TAKE OUT ATTACKER");
        System.out.println(this);
        assertTrue(this.allowMove(Player.THAT, new Pair(0, 3), new Pair(0, 0)));
        assertFalse(this.isInCheckmate(Player.THAT));
    }

    @Test
    void testAllowMoveNaive() {
        System.out.println("TESTING NAIVE ALLOW-MOVE");
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.board[x][y] = new NullPiece(x, y);
            }
        }
        this.thatKing = new King(7, 0, Player.THAT);
        this.thisKing = new King(7, 7, Player.THIS);
        this.board[7][0] = thatKing;
        this.board[7][7] = thisKing;
        this.board[0][0] = new Rook(0, 0, Player.THIS);
        this.board[0][1] = new Rook(0, 1, Player.THIS);
        this.board[6][0] = new Bishop(6, 0, Player.THAT);
        System.out.println("KING CANNOT MOVE DOWNWARD");
        System.out.println(this);
        assertFalse(this.allowMove(Player.THAT, this.thatKing.location, new Pair(7, 1)));
        System.out.println("BISHOP CANNOT LEAVE THE KING TO (4, 2)");
        System.out.println(this);
        assertFalse(this.allowMove(Player.THAT, new Pair(6, 0), new Pair(4, 2)));
    }

    @Test
    void testAllowMoveKhan () {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.board[x][y] = new NullPiece(x, y);
            }
        }
        this.thatKing = new King(7, 0, Player.THAT);
        this.thisKing = new King(7, 7, Player.THIS);
        this.board[7][0] = thatKing;
        this.board[7][7] = thisKing;
        this.board[0][0] = new Rook(0, 0, Player.THIS);
        this.board[3][1] = new Rook(3, 1, Player.THIS);
        // add reinforcement
        this.board[0][3] = new Khan(0, 3);
        this.board[0][3].owner = Player.THAT; // remove randomness
        this.board[1][2] = new Pawn(1, 2, Player.THIS); // to block khan
        System.out.println("KHAN CANNOT HELP");
        System.out.println(this);
        assertTrue(this.board[0][3].owner == Player.THAT);
        assertFalse(this.allowMove(Player.THAT, new Pair(0, 3), new Pair(0, 0)));
        assertTrue(this.board[0][3].owner == Player.THAT);

        this.board[1][2] = new NullPiece(1, 2);
    }

}