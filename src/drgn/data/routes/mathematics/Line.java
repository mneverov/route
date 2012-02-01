package drgn.data.routes.mathematics;

import drgn.data.routes.model.Point;

/**
 *
 */
public class Line {

    private final double _a;
    private final double _b;
    private final double _c;

    public Line(Point start, Point end) {
        _a = start.getLatitude() - end.getLatitude();
        _b = end.getLongitude() - start.getLongitude();
        _c = start.getLongitude() * end.getLatitude() - end.getLongitude() * start.getLatitude();
    }

    public Line(double a, double b, double c) {
        _a = a;
        _b = b;
        _c = c;
    }

    public double getA() {
        return _a;
    }

    public double getB() {
        return _b;
    }

    public double getC() {
        return _c;
    }

    public Line getParallelLine(Point point) {
        return new Line(_a, _b, -(_a *point.getLongitude() + _b * point.getLatitude()));
    }

    public Line getPerpendicularLine(Point point) {
        double b = Geometry.getBearingTo(this);
        Point p = Geometry.getPointByBearingAndDistance(point, b+90, 10);
        return new Line(point, p);
    }
}
