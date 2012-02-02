package drgn.data.routes.mathematics;

import drgn.data.routes.model.Point;
import junit.framework.TestCase;

/**
 */

public class PointTest extends TestCase {

    private static double longitude = 11.123456, latitude = 22.654321;
    private int pointId = 0;

    public void testCreatePoint() {
        Point point = new Point(1, latitude, longitude);
        assertEquals(point.getLatitude(), latitude);
        assertEquals(point.getLongitude(), longitude);
        assertEquals(point.getId(), 1);
    }

    public void test_is_point_on_line() {
        Point point = createPoint(0, 0);
        Line line = new Line(createPoint(-10, 0), createPoint(10, 0));
        assertTrue(Geometry.isOnLine(point, line));
    }

    public void test_is_point_between_lines() {
        Point point = createPoint(0, 0);
        Line line1 = new Line(createPoint(5, 0), createPoint(5, 5));
        Line line2 = new Line(createPoint(-5, 0), createPoint(-5, 5));
        assertTrue(Geometry.isBetween(point, line1, line2));
    }

    public void test_get_perp_line() {
        Point point = createPoint(0, 0);
        Line line = new Line(point, createPoint(10, 0));
        Line linePerp = line.getPerpendicularLine(point);
        assertTrue(Geometry.isOnLine(createPoint(0, 10), linePerp));
    }

    public void test_get_bearing() {
        Point p1 = createPoint(0, 0);
        Point p2 = createPoint(0, 10);
        assertEquals(90.0, Geometry.getBearingTo(p1, p2));
    }

    public void test_bearing_by_line_and_by_point_are_same() {
        Point p1 = createPoint(3, 43);
        Point p2 = createPoint(34, 23);
        Line l = new Line(p1, p2);
        double bp = Geometry.getBearingTo(p1, p2);
        double bl = Geometry.getBearingTo(l);
        assertEquals(bp, bl);
    }

    public void test_get_point_by_distance() {
        Point p1 = createPoint(0, 0);
        Point p2 = createPoint(0, 10);
        Point p3 = createPoint(10, 0);
        double bearing = Geometry.getBearingTo(p1, p3);
        double distance = Geometry.distanceBetween(p1, p3);
        assertEquals(p3, Geometry.getPointByBearingAndDistance(p1, bearing, distance));
    }

    public void test_distance_to_projection() {
        Point p1 = createPoint(0, 0);
        Point p2 = createPoint(0, 10);
        Point p3 = createPoint(0, 5);
        double distance = Geometry.distanceBetween(p1, p3);
        assertEquals(distance, Geometry.distanceBetween(p1, Geometry.findProjectionOn(p3, p1, p2)));
    }

    private Point createPoint(double latitude, double longitude) {
        return new Point(pointId++, latitude, longitude);
    }
}