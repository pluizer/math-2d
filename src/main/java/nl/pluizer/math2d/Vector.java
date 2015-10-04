package nl.pluizer.math2d;

/**
 * The Vector class provides many immutable operations for
 * working with vectors.
 *
 * @author Richard van Roy
 */
public class Vector implements Comparable<Vector> {

    private double x, y;

    /**
     * Creates a zero-length vector.
     */
    public Vector() {
        this(0, 0);
    }

    /**
     * Creates a vector by specifying its x and y values.
     * @param x         the x value
     * @param y         the y value
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copies a vector
     * @param vector    the vector to copy
     */
    public Vector(Vector vector) {
        this(vector.getX(), vector.getY());
    }

    /**
     * Creates a <i>unit length</i> vector from a given angle.
     * @param angle     the angle to use
     */
    public Vector(Angle angle) {
        this(Math.cos(angle.getRadian()), Math.sin(angle.getRadian()));
    }

    /**
     *  Compare this vector with another vector by there lengths.
     */
    @Override
    public int compareTo(Vector other) {
        // Returning the square root of the length is faster
        // than calculation its real length and works all the
        // same.
        double lA = dotProduct(this);
        double lB = other.dotProduct(other);
        return lA < lB ? -1 : lA == lB ? 0 : 1;
    }

    /**
     * @return          the x value of this vector
     */
    public double getX() {
        return x;
    }

    /**
     * @return          the y value of this vector
     */
    public double getY() {
        return y;
    }

    /**
     * Check if two vectors are equal.
     * @param other     the other vector to compare
     * @return          true if vectors are equal, false otherwise
     */
    public boolean equals(Vector other) {
        double epsilon = 0.000001;
        return  Math.abs(getX() - other.getX()) <= epsilon &&
                Math.abs(getY() - other.getY()) <= epsilon;
    }

    /**
     * Adds two vectors
     * @param other     the vector to add to this one
     * @return          a new vector that is an addition of this vector
     *                  and the other vector
     */
    public Vector add(Vector other) {
        return new Vector(getX() + other.getX(), getY() + other.getY());
    }

    /**
     * Subtracts two vectors
     * @param other     the vector to add to this one
     * @return          a new vector that is an subtraction of this vector
     *                  and the other vector
     */
    public Vector subtract(Vector other) {
        return new Vector(getX() - other.getX(), getY() - other.getY());
    }

    /**
     * Returns the scalar multiplication of a vector an a scalar.
     * @param scalar    the scalar to use
     * @return          a new vector that is scaled by the scalar
     */
    public Vector scale(double scalar) {
        return new Vector(getX() * scalar, getY() * scalar);
    }

    /**
     * Returns the dot product of this vector and an other
     * @param other     the other vector
     * @return          the dot product of this vector and the other
     */
    public double dotProduct(Vector other) {
        double aX = getX(), aY = getY();
        double bX = other.getX(), bY = other.getY();
        return (aX * bX) + (aY * bY);
    }

    /**
     * Returns the magnitude of z-axis of the 3d vector resulting
     * from taking the cross product of this vector and an other.
     * @param other     the other vector
     * @return          the magnitude of the z-axis
     */
    public double crossProduct(Vector other) {
        double aX = getX(), aY = getY();
        double bX = other.getX(), bY = other.getY();
        return (aX * bY) - (aY * bX);
    }

    /**
     * Returns the horizontal perpendicular vector of this one
     * @return          a new vector that is horizontally perpendicular
     *                  to this vector.
     */
    public Vector perpendicularHorizontal() {
        return new Vector(-getY(), getX());
    }

    /**
     * Returns the vertical perpendicular vector of this one
     * @return          a new vector that is vertically perpendicular
     *                  to this vector.
     */
    public Vector perpendicularVertical() {
        return new Vector(getY(), -getX());
    }

    /**
     * Projects this vector onto an other vector.
     * @param other     the other vector
     * @return          a new vector that resulted from projecting
     *                  an other vector onto this one
     */
    public Vector project(Vector other) {
        return scale(this.dotProduct(other) / other.dotProduct(other));
    }

    /**
     * Rotates this vector by an other vector.
     * @param other     the other vector
     * @return          a new vector resulting from the rotation
     */
    public Vector rotate(Vector other) {
        double aX = getX(), aY = getY();
        double bX = other.getX(), bY = other.getY();
        return new Vector(
                (aX * bX) + (aY * bY),
                (aX * bY) - (aY * bX));
    }

    /**
     * The inverse of rotate. Rotates this vector back by an other
     * vector.
     * @param other     the other vector
     * @return          a new vector resulting from the "unrotation".
     */
    public Vector unrotate(Vector other) {
        double aX = getX(), aY = getY();
        double bX = other.getX(), bY = other.getY();
        return new Vector(
                (aX * bX) + (aY * bY),
                (aY * bX) + (aX * bY));
    }

    /**
     * Returns the length of a vector.
     * @return          the length of the vector
     */
    public double length() {
        return Math.sqrt(dotProduct(this));
    }

    /**
     * Linear interpolate between this vector and an other vector and the
     * interpolate t.
     * @param other     the other vector
     * @param t         the interpolate, clamped between [0..1]
     * @return          a new vector that is the interpolation between
     *                  this vector and the other.
     */
    public Vector lerp(Vector other, double t) {
        return scale(1.0d - t).add(other.scale(t));
    }

    /**
     * Returns a the <i>unit length</i> vector of the vector.
     * @return          a new <i>unit length</i> vector
     */
    public Vector normalise() {
        return scale(1.0d / (length() + Double.MIN_VALUE));
    }

    /**
     * Clamps this vector to a specific length, decreasing its
     * size if this vector's length is bigger, else leaving it
     * the same.
     * @param length    the target's maximum length
     * @return          a new vector clamped to length
     */
    public Vector clampLength(double length) {
        return dotProduct(this) > Math.sqrt(length)
                ? normalise().scale(length)
                : new Vector(this);
    }

    /**
     * Linearly interpolated between this vector towards an other vector
     * by a specific distance.
     * @param other     the other vector
     * @param distance  the distance
     * @return          a new interpolated vector
     */
    public Vector towards(Vector other, double distance) {
        // TODO: Check if this works.
        return add(other.clampLength(this.length() + distance));
    }

    /**
     * Returns the distance between this vector and another.
     * @param other     the other vector
     * @return          the distance between the vectors
     */
    public double distance(Vector other) {
        return subtract(other).length();
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
