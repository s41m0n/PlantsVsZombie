package pvz.utility;

/**
 * A standard generic Pair<X,Y>, with getters, hashCode, equals, and toString
 * well implemented.
 *
 * @param <X>
 *            the first element
 * @param <Y>
 *            the second element
 */
public class Pair<X, Y> {

    private final X x;
    private final Y y;

    /**
     * Public constructor, it sets the x and y.
     * 
     * @param x
     *            first element
     * @param y
     *            second element
     */
    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Method to get the x element of the pair.
     * 
     * @return the x element.
     */
    public X getX() {
        return x;
    }

    /**
     * Method to get the y element of the pair.
     * 
     * @return the y element.
     */
    public Y getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Pair other = (Pair) obj;
        if (x == null) {
            if (other.x != null) {
                return false;
            }
        } else if (!x.equals(other.x)) {
            return false;
        }
        if (y == null) {
            if (other.y != null) {
                return false;
            }
        } else if (!y.equals(other.y)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pair [x = " + x + "; y = " + y + "]";
    }

    /**
     * Method to create a new Pair of a,b.
     * 
     * @param a
     *            first element of the pair.
     * @param b
     *            second element of the pair.
     * @param <A>
     *            the type of the first element.
     * @param <B>
     *            the type of the second element.
     * @return new pair of a,b.
     */
    public static <A, B> Pair<A, B> pairOf(final A a, final B b) {
        return new Pair<A, B>(a, b);
    }

}
