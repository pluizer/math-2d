package nl.pluizer.math2d;

/**
 * This class provides a universal way of working with angles whether they
 * where created using degrees or radians.
 *
 * @author Richard van Roy
 */
public class Angle implements Comparable<Angle>{

    private double radian, degree;

    private Angle(double radian) {
        this.radian = radian;
        this.degree = radian * (180 / Math.PI);
    }

    /**
     * Creates a new nl.pluizer.math2d.Angle by specifying the angle in radians.
     * @param radian    the angle in radians
     * @return          a new nl.pluizer.math2d.Angle object
     */
    public static Angle fromRadian(double radian) {
        return new Angle(radian);
    }

    /**
     * Creates a new nl.pluizer.math2d.Angle by specifying the angle in degrees.
     * @param degree    the angle in degrees
     * @return          a new nl.pluizer.math2d.Angle object
     */
    public static Angle fromDegree(double degree) {
        return new Angle(degree * (Math.PI / 180));
    }

    /**
     * Creates a new nl.pluizer.math2d.Angle converted from a nl.pluizer.math2d.Vector.
     * @param vector    the vector to convert to an angle
     * @return          a new nl.pluizer.math2d.Angle object
     */
    public static Angle fromVector(Vector vector) {
        return fromRadian(Math.atan2(vector.getY(), vector.getX()));
    }

    @Override
    public int compareTo(Angle other) {
        double rA = getRadian(), rB = other.getRadian();
        return rA < rB ? -1 : rA == rB ? 0 : 1;
    }

    /**
     * @return          the angle of this object in radians
     */
    public double getRadian() {
        return radian;
    }

    /**
     * @return          the angle of this object in degrees
     */
    public double getDegree() {
        return degree;
    }
}
