/**
 * Created by dwd on 9/15/17.
 */
public interface Visitor {
    boolean visit (RegularPiece regularPiece, Integer newX, Integer newY);
    boolean visit (King king, Integer newX, Integer newY);

    boolean visit (Pawn pawn, Integer newX, Integer newY);
    boolean visit (Knight knight, Integer newX, Integer newY);

    boolean visit (NullPiece nullPiece, Integer newX, Integer newY);

    boolean visit (Cannon cannon, Integer newX, Integer newY);
    boolean visit (Khan khan, Integer newX, Integer newY);

}
