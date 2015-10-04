package nl.pluizer.math2d;

import java.util.*;

/**
 * The Polygon class provides many immutable operations for
 * working with polygons.
 *
 * @author Richard van Roy
 */
public class Polygon {

    private List<Vector> vectors;

    /**
     * Creates a new polygon from a rectangle.
     * @param rectangle     the rectangle to make the polygon out of
     */
    public Polygon(Rectangle rectangle) {
        double left   = rectangle.getTopLeft().getX();
        double right  = rectangle.getBottomRight().getX();
        double bottom = rectangle.getBottomRight().getY();
        double top    = rectangle.getTopLeft().getY();
        vectors = new ArrayList<>();
        vectors.add(new Vector(left , top));
        vectors.add(new Vector(left , bottom));
        vectors.add(new Vector(right, bottom));
        vectors.add(new Vector(right, top));
    }

    /**
     * Creates a new polygon from a list of vectors.
     * @param vectors       the list of vector to make to polygon
     *                      out of
     */
    public Polygon(List<Vector> vectors) {
        this.vectors = new ArrayList<>(vectors);
    }

    /**
     * Copies a polygon
     * @param polygon       the polygon to copy
     */
    public Polygon(Polygon polygon) {
        this(polygon.vectors);
    }

    private double clockwise(Vector o, Vector a, Vector b) {
        double pA = (a.getX() - o.getX()) * (b.getY() - o.getY());
        double pB = (a.getY() - o.getY()) * (b.getX() - o.getX());
        return pA - pB;
    }

    public Polygon convexHull() {
        // Can't make a non-convex with 3 points.
        if (vectors.size() <= 3) {
            return new Polygon(vectors);
        }
        // Sort vectors by X, if there is a tie, sort them by Y...
        List<Vector> sortedVectors = new ArrayList<>(vectors);
        Collections.sort(sortedVectors, (v1, v2) -> {
            if (v1.getX() < v2.getX()) {
                return -1;
            } else if (v1.getX() == v2.getX()) {
                return v1.getY() < v2.getY() ? -1 : 1;
            } else {
                return 1;
            }
        });

        List<Vector> lower = new ArrayList<>();
        sortedVectors.forEach((v) -> {
            while (lower.size() >= 2 &&
                    new Triangle(v, lower.get(lower.size() - 2),
                            lower.get(lower.size() - 1)).clockwise()) {
                lower.remove(lower.size() - 1);
            }
            lower.add(v);
        });

        List<Vector> reverseSorted = new ArrayList<>(sortedVectors);
        Collections.reverse(reverseSorted);
        Stack<Vector> upper = new Stack<>();
        reverseSorted.forEach((v) -> {
            while (upper.size() >= 2 &&
                    new Triangle(v, upper.get(upper.size()-2),
                            upper.get(upper.size()-1)).clockwise()) {
                upper.remove(upper.size() - 1);
            }
            upper.add(v);
        });

        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);
        List<Vector> result = new ArrayList<>(lower);
        result.addAll(upper);
        return new Polygon(result);
    }

    public List<Vector> getVectors() {
        return vectors;
    }
}
