/**
 *
 */
public class Line {
    private double _a;
    private double _b;
    private double _c;

    public Line(Point start, Point end) {
        _a = start.getLongitude() - end.getLongitude();
        _b = end.getLatitude() - start.getLatitude();
        _c = start.getLatitude() * end.getLongitude() - end.getLatitude() * start.getLongitude();
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
        return new Line(_a, _b, -(_a * point.getLatitude() + _b * point.getLongitude()));
    }

    public Line getPerpendicularLine(Point point) {
        return new Line(_b, -_a, _a * point.getLongitude() - _b * point.getLatitude());
    }
}
