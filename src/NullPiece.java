/**
 * A design choice to avoid using Null.
 */
public class NullPiece extends Piece {

    NullPiece (Integer newX, Integer newY) {
        super(newX, newY);
        this.owner = Player.NONE;
    }

    @Override
    public Boolean accept(Visitor visitor, Integer newX, Integer newY) {
        return visitor.visit(this, newX, newY);
    }

    @Override
    public String toString() {
        return "";
    }

    /**
     * a null piece cannot go anywhere
     * @param newX new X coordinate
     * @param newY new Y coordinate
     * @return always false
     */
    Boolean canMoveTo(Integer newX, Integer newY) {
        return false;
    }
}