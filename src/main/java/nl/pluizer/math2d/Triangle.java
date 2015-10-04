package nl.pluizer.math2d;

/**
 * The Triangle class is used to define a triangle.
 *
 * @author Richard van Roy
 */
public class Triangle implements Shape {

    private Vector cornerA, cornerB, cornerC;

    /**
     * Create a triangle from 3 vectors.
     * @param cornerA       the first vector
     * @param cornerB       the second vector
     * @param cornerC       the third vector
     */
    public Triangle(Vector cornerA, Vector cornerB, Vector cornerC) {
        this.cornerA = cornerA;
        this.cornerB = cornerB;
        this.cornerC = cornerC;
    }

    /**
     * @return              the first corner of this triangle
     */
    public Vector getCornerA() {
        return cornerA;
    }

    /**
     * @return              the second corner of this triangle
     */
    public Vector getCornerB() {
        return cornerB;
    }

    /**
     * @return              the third corner of this triangle
     */
    public Vector getCornerC() {
        return cornerC;
    }

    /**
     * Returns true if this triangle is defined in clockwise order.
     * @return              true if clockwise, false otherwise
     */
    public boolean clockwise() {
        double pA = (cornerA.getX() - cornerB.getX()) * (cornerB.getY() - cornerC.getY());
        double pB = (cornerA.getY() - cornerC.getY()) * (cornerB.getX() - cornerC.getX());
        return pA - pB <= 0;
    }

    /**
     * Returns the total width of this triangle.
     * @return              the width of this triangle
     */
    @Override
    public double getWidth() {
        return Rectangle.encapsulateTriangle(this).getWidth();
    }

    /**
     * Returns the total width of this triangle.
     * @return              the width of this triangle
     */
    @Override
    public double getHeight() {
        return Rectangle.encapsulateTriangle(this).getWidth();
    }
}
