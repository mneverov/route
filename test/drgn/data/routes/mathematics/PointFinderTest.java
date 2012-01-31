package drgn.data.routes.mathematics;

import drgn.data.routes.model.Point;
import drgn.data.routes.model.Route;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class PointFinderTest extends TestCase {

    private double latitude = 0, longitude = 0;
    private int pointId = 0;
    private int routeId = 0;

    public void test_for_empty_route() {
        PointFinder finder = createFinder();
        Route emptyRoute = createRoute();
        List<Point> points = finder.findPoints(emptyRoute, 100);
        assertTrue(points.isEmpty());
    }

    public void test_one_point_in_route() {
        Point point = createPoint(latitude, longitude);
        PointFinder finder = createFinder(point);
        Route onePointRoute = createRoute(point);
        List<Point> pointsForRoute = finder.findPoints(onePointRoute, Integer.MAX_VALUE);
        assertEquals(0, pointsForRoute.size());
        assertTrue(!pointsForRoute.contains(point));
    }

    public void test_one_point_out_of_route() {
        Point pointOutOfRoute = createPoint(latitude, longitude + 1);
        PointFinder finder = createFinder(pointOutOfRoute);
        Route onePointRoute = createRoute(createPoint(latitude, longitude));
        List<Point> pointsForRoute = finder.findPoints(onePointRoute, Integer.MAX_VALUE);
        assertEquals(0, pointsForRoute.size());
        assertTrue(!pointsForRoute.contains(pointOutOfRoute));
    }

    public void test_points_sorted() {
        Point point = createPoint(latitude, longitude);
        Route route = createRoute(point, createPoint(latitude + 5, longitude +10));
        PointFinder pointFinder = createFinder(createPoint(latitude + 2, longitude + 2), createPoint(latitude + 1, longitude + 1));
        List<Point> result = pointFinder.findPoints(route, Integer.MAX_VALUE);
        assertTrue(Geometry.distanceBetween(point, result.get(0)) < Geometry.distanceBetween(point, result.get(1)));
    }

    public void test_not_return_duplicate_points() {
        PointFinder finder = createFinder(createPoint(latitude, longitude));
        Route route = createRoute(createPoint(latitude+1, longitude+1), createPoint(latitude+2, longitude+2));
        List<Point> result = finder.findPoints(route, Integer.MAX_VALUE);
        assertEquals(1, result.size());
    }

    public void test_find_point2() {
        Point pointOutOfRoute = createPoint(latitude+2, longitude + 1);
        PointFinder finder = createFinder(pointOutOfRoute);
        Route route = createRoute(createPoint(latitude-10, longitude-10), createPoint(latitude+10, longitude+10));
        List<Point> result = finder.findPoints(route, (int) Geometry.distanceBetween(pointOutOfRoute, createPoint(latitude - 11, longitude - 11)));
        assertTrue(result.contains(pointOutOfRoute));
    }

    public void test_find_south_point() {
        Point p1 = createPoint(0, 0);
        Point p2 = createPoint(-10, 0);
        Point p3 = createPoint(-5, -5);
        PointFinder finder = createFinder(p3);
        Route route = createRoute(p1, p2);
        List<Point> result = finder.findPoints(route, Integer.MAX_VALUE);
        assertTrue(result.contains(p3));
    }

    private Route createRoute(Point... points) {
        return new Route(routeId++, Arrays.asList(points));
    }

    private PointFinder createFinder(Point... points) {
        return new PointFinder(Arrays.asList(points));
    }

    private Point createPoint(double latitude, double longitude) {
        return new Point(pointId++, latitude, longitude);
    }
}
