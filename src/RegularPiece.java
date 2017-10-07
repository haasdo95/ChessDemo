/**
 * This class has no actual content
 * It only serves to mark pieces that behaves "regularly"
 * queen, bishop, king, and rook are in this category
 */
public abstract class RegularPiece extends Piece {
    RegularPiece (Integer newX, Integer newY) {
        super(newX, newY);
    }
}
