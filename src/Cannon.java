/**
 * Defines rules for Cannon
 */
public class Cannon extends Piece {

    Cannon (Integer newX, Integer newY, Player owner) {
        super(newX, newY);
        this.owner = owner;
    }

    @Override
    public String toString() {
        if (this.owner == Player.THIS) {
            return "炮";
        } else {
            return "砲";
        }
    }

    @Override
    public Boolean accept(Visitor visitor, Integer newX, Integer newY) {
        return visitor.visit(this, newX, newY);
    }

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
