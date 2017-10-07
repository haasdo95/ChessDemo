/**
 * Created by dwd on 9/15/17.
 */
public interface Visitable {
    Boolean accept(Visitor visitor, Integer newX, Integer newY);
}
