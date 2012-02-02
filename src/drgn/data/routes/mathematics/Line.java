package drgn.data.routes.mathematics;

import drgn.data.routes.model.Point;

/**
 *
 */
public class Line {

    private final double _a;
    private final double _b;
    private final double _c;

    private final Point _p1;
    private final Point _p2;

    public Line(Point start, Point end) {
        _a = start.getLatitude() - end.getLatitude();
        _b = end.getLongitude() - start.getLongitude();
        _c = start.getLongitude() * end.getLatitude() - end.getLongitude() * start.getLatitude();
        _p1 = start;
        _p2 = end;
    }

    public Line(double a, double b, double c, Point start) {
        _a = a;
        _b = b;
        _c = c;
        _p1 = start;
        double lat = _p1.getLatitude() - _a;
        double lon = _p1.getLongitude() + _b;
        _p2 = new Point(lat, lon);
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

    public Point get_p1() {
        return _p1;
    }

    public Point get_p2() {
        return _p2;
    }


    public Line getParallelLine(Point point) {
        return new Line(_a, _b, -(_a *point.getLongitude() + _b * point.getLatitude()), point);
    }

    public Line getPerpendicularLine(Point point) {
        double b = Geometry.getBearingTo(this._p1, this._p2);
        Point p = Geometry.getPointByBearingAndDistance(point, b+90, 10);
        return new Line(point, p);
    }
}
