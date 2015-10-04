package nl.pluizer.math2d;

/**
 * The Circle class is used to define a circle.
 *
 * @author Richard van Roy
 */
public class Circle {

    private Vector centre;

    private double radius;

    /**
     * Returns a circle with centred around a centre width a
     * specific radius.
     * @param position      the centre position of the circle
     * @param radius        the radius of the circle
     */
    public Circle(Vector position, double radius) {
        this.centre = position;
        this.radius = radius;
    }

    /**
     * Creates a circle with a certain radius.
     * @param radius        the radius of the circle
     */
    public Circle(double radius) {
        this(new Vector(), radius);
    }

    /**
     * Returns the centre position of the circle.
     * @return              a new vector, the centre of the circle
     */
    public Vector getCentre() {
        return centre;
    }

    /**
     * Teturns the radius of this circle
     * @return              the radius of this circle.
     */
    public double getRadius() {
        return radius;
    }
}
