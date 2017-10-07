/**
 * Define rules to move a pawn
 */
public class Pawn extends Piece {

    boolean isFirstMove;

    @Override
    public Boolean accept(Visitor visitor, Integer newX, Integer newY) {
        return visitor.visit(this, newX, newY);
    }

    Pawn (Integer newX, Integer newY, Player owner) {
        super(newX, newY);
        this.isFirstMove = true;
        this.owner = owner;
    }

    @Override
    public String toString() {
        if (this.owner == Player.THIS) {
            return "♙";
        } else {
            return "♟";
        }
    }

    /**
     * a rook cannot go back.
     * one headache is that "go back" has different meaning for THIS player and THAT player
     * So I used followPawnRule to define basic move rules, together with canMoveTo to integrate player info
     * @param newX new X coordinate
     * @param newY new Y coordinate
     */
    @Override
    Boolean canMoveTo(Integer newX, Integer newY) {
        Boolean basic = super.canMoveTo(newX, newY);
        Integer xdiff = this.getXCoordinateDiff(newX);
        Integer ydiff = this.getYCoordinateDiff(newY);
        return basic &&
                (this.owner == Player.THIS && followPawnRule(xdiff, ydiff))
                || (this.owner == Player.THAT && followPawnRule(xdiff, -ydiff));
    }

    private boolean followPawnRule(Integer xdiff, Integer ydiff) {
        return (Math.abs(xdiff) == 1 &&
                ydiff == 1) // capturing
                ||
                (this.isFirstMove && ydiff == 2 && xdiff == 0)
                ||
                (ydiff == 1 && xdiff == 0);
    }

    /**
     * kinda special because pawns have a special first move
     * @param newX new X coordinate
     * @param newY new Y coordinate
     */
    void moveTo(Integer newX, Integer newY) {
        this.isFirstMove = false;
        super.moveTo(newX, newY);
    }
}
