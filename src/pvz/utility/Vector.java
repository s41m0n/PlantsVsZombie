package pvz.utility;

/**
 * Basic 2D vector with floating point components.
 */
public final class Vector {

    private Pair<Double, Double> components;

    private Vector(final double x, final double y) {
        this.components = new Pair<Double, Double>(x, y);
    }

    /**
     * Returns the vector's x component.
     * 
     * @return x component
     */
    public double getX() {
        return this.components.getX();
    }

    /**
     * Returns the vector's y component.
     * 
     * @return y component
     */
    public double getY() {
        return this.components.getY();
    }

    /**
     * Factory method that returns a new Vector object, given the two
     * coordinates.
     * 
     * @param x
     *            horizontal position
     * @param y
     *            vertical position
     * @return a new vector
     */
    public static Vector of(final double x, final double y) {
        return new Vector(x, y);
    }

}
