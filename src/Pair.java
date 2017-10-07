import java.util.ArrayList;

/**
 * a utility class used to represent a location on the board
 */
public class Pair {
    Integer x;
    Integer y;
    Pair(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Pair)) {
            return false;
        }
        Pair o = (Pair) other;
        return this.x.equals(o.x) && this.y.equals(o.y);
    }

    /**
     * give you an array of points between the start and dest points
     * works only with horizontal, vertical and diagonal cases
     * other cases give UNDEFINED behaviour
     * @param other the other point; dest
     * @return the points in between
     */
    ArrayList<Pair> generatePath(Pair other) {
        ArrayList<Pair> retValue = new ArrayList<>();
        int xInc = other.x - this.x;
        int yInc = other.y - this.y;
        if (xInc > 0) xInc = 1;
        else if (xInc < 0) xInc = -1;
        if (yInc > 0) yInc = 1;
        else if (yInc < 0) yInc = -1;
        for (int xRunner = this.x, yRunner = this.y; xRunner != other.x || yRunner != other.y; xRunner += xInc, yRunner += yInc) {
            Pair newPair = new Pair(xRunner, yRunner);
            retValue.add(newPair);
        }
        return retValue;
    }

}
