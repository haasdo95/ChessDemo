/**
 * Abstract piece class.
 * Define some most basic rules for a piece(stay in bound, cannot move zero step, etc)
 */
public abstract class Piece implements Visitable {

    Pair location;
    Player owner;

    Piece(Integer newX, Integer newY) {
        this.location = new Pair(newX, newY);
    }

    @Override
    public abstract Boolean accept(Visitor visitor, Integer newX, Integer newY);

    Integer getXCoordinateDiff(Integer newX) {
        return newX - this.location.x;
    }

    Integer getYCoordinateDiff(Integer newY) {
        return newY - this.location.y;
    }

    /**
     * return true if a chess piece, given its rule of movement,
     * can move to a certain location.
     *
     * @param newX new X coordinate
     * @param newY new Y coordinate
     * @return a boolean indicating if a chess can move this way.
     */
    Boolean canMoveTo(Integer newX, Integer newY) {
        return newX >= 0 && newX < 8
                && newY >= 0 && newY < 8 // must be in bound
                && (!newX.equals(this.location.x)
                    || !newY.equals(this.location.y)); // must move a little bit
    }

    /**
     * actually move to a certain location
     * update the inner-state of a piece
     * @param newX new X coordinate
     * @param newY new Y coordinate
     */
    void moveTo(Integer newX, Integer newY) {
        this.location = new Pair(newX, newY);
    }
}
