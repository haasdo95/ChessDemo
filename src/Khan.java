/**
 * Defines rules for an Khan
 * At init the khan will join a random side.
 * Will switch side after killing a piece
 */
public class Khan extends RegularPiece {

    Khan(Integer newX, Integer newY) {
        super(newX, newY);
        if (Math.random() < 0.5) {
            this.owner = Player.THIS;
        } else {
            this.owner = Player.THAT;
        }
    }

    @Override
    public String toString() {
        if (owner == Player.THAT) {
            return "✫";
        } else {
            return "✬";
        }
    }

    void switchSide () {
        if (this.owner == Player.THIS) {
            this.owner = Player.THAT;
        } else {
            this.owner = Player.THIS;
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
                        (xdiff == 0 || // horizontal
                        ydiff == 0 || // vertical
                        Math.abs(xdiff) == Math.abs(ydiff)) // diagonal
                        ||
                        ((Math.abs(xdiff) == 2 &&
                            Math.abs(ydiff) == 1)
                            ||
                         (Math.abs(xdiff) == 1 &&
                            Math.abs(ydiff) == 2)) // moving like a horseman
                );
    }
}
