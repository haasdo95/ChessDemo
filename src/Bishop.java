/**
 * Define rules to move a bishop
 */
public class Bishop extends RegularPiece {
    
    Bishop (Integer newX, Integer newY, Player owner) {
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
            return "♗";
        } else {
            return "♝";
        }
    }

    /**
     * a bishop could only go diagonally
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
                    Math.abs(xdiff) == Math.abs(ydiff)
                );
    }
}
