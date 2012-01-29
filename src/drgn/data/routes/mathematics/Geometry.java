package drgn.data.routes.mathematics;

import drgn.data.routes.model.Point;

import static java.lang.Math.*;

/**
 *
 */
public final class Geometry {

    private Geometry(){}

    private final static int EARTH_RADIUS = 6371000;

    public static double distanceBetween(Point p1, Point p2) {
        double dLat = toRadians(p2.getLatitude() - p1.getLatitude());
        double dLon = toRadians(p2.getLongitude() - p1.getLongitude());
        double e = sin(dLat/2)*sin(dLat/2) + cos(toRadians(p1.getLatitude())) * cos(toRadians(p2.getLatitude())) * sin(dLon/2) * sin(dLon/2);
        double c = 2 * asin(Math.sqrt(e));
        double distance = EARTH_RADIUS * c;
        return distance;
    }

    public static boolean isOnLine(Point p, Line line) {
        return (line.getA() * p.getLongitude() + line.getB() * p.getLatitude() + line.getC()) == 0;
    }

    public static boolean isBetween(Point p, Line line1, Line line2) {
        double y1 = 0 == line1.getB() ? line1.getC() : (-line1.getA() * p.getLongitude() - line1.getC()) / line1.getB();
        double y2 = 0 == line2.getB() ? line2.getC() : (-line2.getA() * p.getLongitude() - line2.getC()) / line2.getB();
        return y1 > p.getLatitude() && y2 < p.getLatitude() || y2 > p.getLatitude() && y1 < p.getLatitude();
    }

    public static double getBearingTo(Point from, Point to) {
        double y = sin(toRadians(to.getLongitude() - from.getLongitude())) * cos(toRadians(to.getLatitude()));
        double x = cos(toRadians(from.getLatitude())) * sin(toRadians(to.getLatitude())) -
                sin(toRadians(from.getLatitude())) * cos(toRadians(to.getLatitude())) * cos(toRadians(to.getLongitude() - from.getLongitude()));
        double bearing = toDegrees(atan2(y, x));
        double normalizeBearing = (bearing + 360) % 360;
        return normalizeBearing;
    }

    public static Point getPointByBearingAndDistance(Point p, double bearing, double distance) {
        double latitude = asin(sin(toRadians(p.getLatitude())) * cos(distance / EARTH_RADIUS) +
                cos(toRadians(p.getLatitude())) * sin(distance / EARTH_RADIUS) * cos(toRadians(bearing)));
        double longitude = toRadians(p.getLongitude()) +
                atan2(sin(toRadians(bearing)) * sin(distance / EARTH_RADIUS) * cos(toRadians(p.getLatitude())),
                        cos(distance / EARTH_RADIUS) - sin(toRadians(p.getLatitude())) * sin(latitude));
        double roundLat = ((int)(toDegrees(latitude) * 1000000)) / 1000000.0;
        double roundLong = ((int)(toDegrees(longitude) * 1000000)) / 1000000.0;
        return new Point(roundLat, roundLong);
    }

    public static Point findProjectionOn(Point p, Point p1, Point p2) {
        Line l = new Line(p1, p2);
        Line perpLine = l.getPerpendicularLine(p);
        double zn = l.getA() * perpLine.getB() - l.getB() * perpLine.getA();
        double longitude = (l.getB() * perpLine.getC() - l.getC() * perpLine.getB()) / zn;
        double latitude = (l.getC() * perpLine.getA() - l.getA() * perpLine.getC()) / zn;
        return new Point(latitude, longitude);
    }
}
