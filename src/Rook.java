/**
 * Define rules to move a rook
 */
public class Rook extends RegularPiece {

    Rook (Integer newX, Integer newY, Player owner) {
        super(newX, newY);
        this.owner = owner;
    }

    @Override
    public Boolean accept(Visitor visitor, Integer newX, Integer newY) {
        return visitor.visit(this, newX, newY);
    }

    @Override
    public String toString() {
        if (this.owner == Player.THIS) {
            return "♖";
        } else {
            return "♜";
        }
    }

    /**
     * a rook could only go horizontally or vertically
     * @param newX new X coordinate
     * @param newY new Y coordinate
     */
    @Override
    Boolean canMoveTo(Integer newX, Integer newY) {
        Boolean basic = super.canMoveTo(newX, newY);
        Integer xdiff = this.getXCoordinateDiff(newX);
        Integer ydiff = this.getYCoordinateDiff(newY);
        return basic &&
                (
                    xdiff == 0 || // horizontal
                    ydiff == 0 // vertical
                );
    }
}
