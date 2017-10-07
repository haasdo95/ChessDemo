/**
 * Define rules to move a knight
 */
public class Knight extends Piece {

    Knight (Integer newX, Integer newY, Player owner) {
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
            return "♘";
        } else {
            return "♞";
        }
    }

    /**
     * a knight goes L shape
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
                        (Math.abs(xdiff) == 2 &&
                        Math.abs(ydiff) == 1)
                        ||
                        (Math.abs(xdiff) == 1 &&
                        Math.abs(ydiff) == 2)
                );
    }
}
