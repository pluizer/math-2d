package nl.pluizer.math2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Rectangle class provides many immutable operations for
 * working with rectangles.
 *
 * @author Richard van Roy
 */
public class Rectangle {

    private double left, right, bottom, top;

    private Rectangle(double left, double right, double bottom, double top) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    /**
     * Creates a rectangle between a topLeft and bottomRight position.
     * @param topLeft       the vector of the topLeft corner's position
     * @param bottomRight   the vector of the bottomRight corner's position
     */
    public Rectangle(Vector topLeft, Vector bottomRight) {
        this(topLeft.getX(), bottomRight.getX(), bottomRight.getY(), bottomRight.getX());
    }

    /**
     * Creates a rectangle starting from the topLeft corner with a specific size.
     * @param topLeft       the vector of the topLeft corner's position
     * @param width         the width of the rectangle
     * @param height        the height of the rectangle
     */
    public Rectangle(Vector topLeft, double width, double height) {
        this(topLeft.getX(), topLeft.getX()+width, topLeft.getY()+height, topLeft.getY());
    }

    /**
     * Creates a rectangle of a certain size.
     * @param width         the width of the rectangle
     * @param height        the height of the rectangle
     */
    public Rectangle(double width, double height) {
        this(new Vector(), width, height);
    }

    /**
     * Creates a rectangle that encapsulates a circle.
     * @param circle        the circle to encapsulate
     */
    public Rectangle(Circle circle) {
        this(circle.getCentre().subtract(new Vector(-circle.getRadius(), -circle.getRadius())),
                circle.getRadius(), circle.getRadius());
    }

    /**
     * Returns a rectangle that bounds over (can contain) all
     * vectors in the list.
     * @param vectors       the list of vectors to contain
     * @return              a new nl.pluizer.math2d.Rectangle that contains those vectors
     */
    static Rectangle contain(List<Vector> vectors) {
        if (vectors.size() <= 2) {
            throw new RuntimeException("Need at least two vectors.");
        }

        // Do not touch the original vectors.
        List<Vector> vectorsCopy = new ArrayList<>(vectors);

        // Find the lowest and highest X value.
        Collections.sort(vectorsCopy, (v1, v2) -> {
            double xA = v1.getX(), xB = v2.getX();
            return xA < xB ? -1 : xA == xB ? 0 : 1;
        });
        double minX = vectorsCopy.get(0).getX();
        double maxX = vectorsCopy.get(vectorsCopy.size() - 1).getX();

        // Find the lowest and highest Y value.
        Collections.sort(vectorsCopy, (v1, v2) -> {
            double yA = v1.getY(), yB = v2.getY();
            return yA < yB ? -1 : yA == yB ? 0 : 1;
        });
        double minY = vectorsCopy.get(0).getY();
        double maxY = vectorsCopy.get(vectorsCopy.size() - 1).getY();

        return new Rectangle(minX, maxX, minY, maxY);
    }

    /**
     * Returns the topLeft corner of the rectangle.
     * @return              a new vector with the topLeft corner
     *                      of this rectangle
     */
    public Vector getTopLeft() {
        return new Vector(left, top);
    }

    /**
     * Returns the bottomRight corner of the rectangle.
     * @return              a new vector with the bottomRight corner
     *                      of this rectangle
     */
    public Vector getBottomRight() {
        return new Vector(right, bottom);
    }

    /**
     * Returns the total width of this rectangle.
     * @return              the width of this rectangle
     */
    public double width() {
        return Math.abs(left - right);
    }

    /**
     * Returns the total height of this rectangle.
     * @return              the height of this rectangle
     */
    public double height() {
        return Math.abs(top - bottom);
    }

    /**
     * Returns true if an other rectangle intersects this one.
     * @param other         the other rectangle
     * @return              true on intersection, false otherwise
     */
    public boolean intersects(Rectangle other) {
        double lA = left, rA = right, bA = bottom, tA = top;
        double lB = other.left, rB = other.right, bB = other.bottom, tB = other.top;
        return lA<=rB && lB<=rA && bA<=tB && bB<=tA;
    }

    /**
     * Returns true if an other rectangle lies completely inside
     * this rectangle.
     * @param other         the other rectangle
     * @return              true if other lies inside this rectangle,
     *                      false otherwise
     */
    public boolean contains(Rectangle other) {
        double lA = left, rA = right, bA = bottom, tA = top;
        double lB = other.left, rB = other.right, bB = other.bottom, tB = other.top;
        return lA<=lB && rA>=rB && bA<=bB && tA>=tB;
    }

    /**
     * Returns true if a vector lies inside this rectangle.
     * @param vector        the vector
     * @return              true of vector lies inside, false otherwise
     */
    public boolean contains(Vector vector) {
        double lA = left, rA = right, bA = bottom, tA = top;
        double vX = vector.getX(), vY = vector.getY();
        return lA<=vX && rA>=vX && bA<=vY && tA>=vY;

    }
    /**
     * Returns true if a circle lies completely inside this rectangle.
     * @param circle        the circle
     * @return              true of circle lies inside, false otherwise
     */
    public boolean contains(Circle circle) {
        return contains(new Rectangle(circle));
    }

    /**
     * Returns a rectangle contains this rectangle and the other one.
     * @param other         the other rectangle
     * @return              a new rectangle that contains both.
     */
    public Rectangle merge(Rectangle other) {
        double lA = left, rA = right, bA = bottom, tA = top;
        double lB = other.left, rB = other.right, bB = other.bottom, tB = other.top;
        return new Rectangle(
                Math.min(lA, lB), Math.max(rA, rB),
                Math.min(bA, bB), Math.max(tA, tB));
    }

    /**
     * Possibly expands a rectangle so it can hold all vectors provided.
     * @param vectors       the vectors the rectangle must contain
     * @return              the new possibly bigger rectangle
     */
    public Rectangle expand(List<Vector> vectors) {
        // Do not touch the original vectors.
        List<Vector> vectorsCopy = new ArrayList<>(vectors);
        vectors.add(getTopLeft());
        vectors.add(getBottomRight());
        return contain(vectorsCopy);
    }

    /**
     * Returns the centre position of this rectangle
     * @return              a vector of the centre position
     */
    public Vector centre() {
        return new Vector(left, bottom).lerp(new Vector(right, top), 0.5d);
    }

    /**
     * Returns the total ara of this rectangle
     * @return              the area of this rectangle
     */
    public double area() {
        return (left-right) * (top-bottom);
    }

    /**
     * If the line between vectors v1 and v2 hit the bounding box
     * this methods returns the fraction along the segment query
     * where the rectangle is hit. If the line does not hit the
     * rectangle this function return INFINITY.
     * @param v1            start of the line
     * @param v2            end of the line
     * @return              fraction along the segment query
     *                      or Double.POSITIVE_INFINITY
     */
    public double segmentQuery(Vector v1, Vector v2) {
        double xA = v1.getX(), xB = v2.getX();
        double idX = 1.0d / (xB - xA);
        double tx1 = xA == left
                ? Double.NEGATIVE_INFINITY
                : (left - xA) * idX;
        double tx2 = xA == right
                ? Double.POSITIVE_INFINITY
                : (right - xA) * idX;
        double txMin = Math.min(tx1, tx2);
        double txMax = Math.max(tx1, tx2);
        //
        double yA = v1.getY(), yB = v2.getY();
        double idY = 1.0d / (yB - yA);
        double ty1 = yA == bottom
                ? Double.NEGATIVE_INFINITY
                : (bottom - yA) * idY;
        double ty2 = yA == top
                ? Double.POSITIVE_INFINITY
                : (top - yA) * idY;
        double tyMin = Math.min(ty1, ty2);
        double tyMax = Math.max(ty1, ty2);
        //
        if (tyMin <= txMax && txMin <= tyMax) {
            double min = Math.max(txMin, tyMin);
            double max = Math.max(txMax, tyMax);
            return 0.0d <= max && min <= 1.0d
                    ? Math.max(min, 0.0d)
                    : Double.POSITIVE_INFINITY;
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Return true if the line between v1 and v2 intersects with
     * this rectangle.
     * @param v1            start of the line
     * @param v2            end of the line
     * @return              true if line hits false otherwise
     */
    public boolean intersects(Vector v1, Vector v2) {
        return segmentQuery(v1, v2) != Double.POSITIVE_INFINITY;
    }

    /**
     * Little helper function that clamps a value between a minimum
     * and a maximum value.
     */
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Returns a new, clamped to a vector, rectangle.
     * @param vector        the vector to clamp to
     * @return              a new, possibly smaller, clamped rectangle
     */
    public Vector clamp(Vector vector) {
        return new Vector(
                clamp(vector.getX(), left, right),
                clamp(vector.getY(), bottom, top));
    }

    /**
     * Returns the a new rectangle that is the sum of this
     * rectangle and a vector.
     * @param vector        the vector to add
     * @return              the new rectangle
     */
    public Rectangle add(Vector vector) {
        double x = vector.getX(), y = vector.getY();
        return new Rectangle(left + x, right + x, bottom + y, top + y);
    }

    /**
     * Returns the a new rectangle that is the subtraction of this
     * rectangle and a vector.
     * @param vector        the vector to subtract
     * @return              the new rectangle
     */
    public Rectangle subtract(Vector vector) {
        double x = vector.getX(), y = vector.getY();
        return new Rectangle(left - x, right - x, bottom - y, top - y);
    }

}

