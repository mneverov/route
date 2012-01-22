import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class PointFinderTest extends TestCase {
    private double latitude = 11.123456, longitude = 22.654321;

    public void test_for_empty_path() {
        PointFinder pointFinder = new PointFinderImpl();
        List<Point> pointList = new ArrayList<Point>();
        Route emptyRoute = new Route(1, pointList);
        List<Point> points = pointFinder.findPoints(emptyRoute, 100);
        assertTrue(points.isEmpty());
    }

    public void test_one_point_in_route() {
        Point point = new Point(1, latitude, longitude);
        List<Point> pointList = new ArrayList<Point>();
        pointList.add(point);
        PointFinder pointFinder = new PointFinderImpl(pointList);
        Route onePointRoute = new Route(1, pointList);
        List<Point> pointsForRoute = pointFinder.findPoints(onePointRoute, Integer.MAX_VALUE);
        assertEquals(1, pointsForRoute.size());
        assertTrue(pointsForRoute.contains(point));
    }

    public void test_one_point_out_of_route() {
        Point pointOutOfRoute = new Point(2, latitude, longitude+1);
        List<Point> points = new ArrayList<Point>();
        points.add(pointOutOfRoute);
        PointFinder pointFinder = new PointFinderImpl(points);
        Point point = new Point(1, latitude, longitude);
        List<Point> pointList = new ArrayList<Point>();
        pointList.add(point);
        Route onePointRoute = new Route(1, pointList);
        List<Point> pointsForRoute = pointFinder.findPoints(onePointRoute, Integer.MAX_VALUE);
        assertEquals(1, pointsForRoute.size());
        assertTrue(pointsForRoute.contains(pointOutOfRoute));
    }

    public void test_points_sorted() {
        Point point = new Point(1, latitude, longitude);
        Point p1 = new Point(2, latitude + 1, longitude + 1);
        Point p2 = new Point(3, latitude + 2, longitude + 2);
        List<Point> pointsForRoute = new ArrayList<Point>();
        pointsForRoute.add(point);
        Route route = new Route(1, pointsForRoute);
        List<Point> pointsOutOfRoute = new ArrayList<Point>();
        pointsOutOfRoute.add(p2);
        pointsOutOfRoute.add(p1);
        PointFinder pointFinder = new PointFinderImpl(pointsOutOfRoute);
        List<Point> result = pointFinder.findPoints(route, Integer.MAX_VALUE);
        assertTrue(Point.getDistance(point, result.get(0)) < Point.getDistance(point, result.get(1)));
    }

    public void test_not_return_duplicate_points() {
        PointFinder finder = new PointFinderImpl(Collections.singletonList(new Point(1, latitude, longitude)));
        Route route = new Route(1, Arrays.asList(new Point(2, latitude+1, longitude+1),
                new Point(3, latitude+2, longitude+2)));
        List<Point> result = finder.findPoints(route, Integer.MAX_VALUE);
        assertEquals(1, result.size());
    }
}
