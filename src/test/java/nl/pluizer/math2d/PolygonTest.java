package nl.pluizer.math2d;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class PolygonTest {

    /**
     * Simply test if we can create a correct convex hull
     */
    @Test
    public void testConvexHull() {
        List<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(0.0d, 0.0d));
        vectors.add(new Vector(1.0d, 0.0d));
        vectors.add(new Vector(1.0d, 1.0d));
        vectors.add(new Vector(0.5d, 0.5d));
        vectors.add(new Vector(0.0d, 1.0d));

        List<Vector> expected = new ArrayList<>();
        expected.add(new Vector(0.0d, 0.0d));
        expected.add(new Vector(1.0d, 0.0d));
        expected.add(new Vector(1.0d, 1.0d));
        expected.add(new Vector(0.0d, 1.0d));

        Polygon polygon = new Polygon(vectors);
        Polygon convex  = polygon.convexHull();
        List<Vector> result = convex.getVectors();

        // Check if contents of the lists are equal.
        Iterator<Vector> iR = result.iterator();
        Iterator<Vector> iE = expected.iterator();
        boolean same = result.size() == expected.size();
        while(iR.hasNext() && iE.hasNext() && same) {
            if (!iR.next().equals(iE.next())) {
                same = false;
            }
        }
        assertTrue("Got a wrong convex hull", same);
    }
}