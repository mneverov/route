/**
 *
 */
public class DistanceLessThan implements Predicate<Point> {

    private final Point _p1;
    private final Point _p2;
    private final int _width;
    private final Line _parallelLine1;
    private final Line _parallelLine2;
    private final Line _perpendicularLine1;
    private final Line _perpendicularLine2;

    public DistanceLessThan(Point p1, Point p2, int width) {
        _p1 = p1;
        _p2 = p2;
        _width = width;

        Line line = new Line(p1, p2);
        _perpendicularLine1 = line.getPerpendicularLine(p1);
        _perpendicularLine2 = line.getPerpendicularLine(p2);
        Point parPoint1 = Geometry.getPointByBearingAndDistance(p1, Geometry.getBearingTo(p1, p2) - 90, width);
        Point parPoint2 = Geometry.getPointByBearingAndDistance(p1, Geometry.getBearingTo(p1, p2) + 90, width);
        _parallelLine1 = line.getParallelLine(parPoint1);
        _parallelLine2 = line.getParallelLine(parPoint2);
    }

    public boolean apply(Point p) {
        return p.isBetween(_parallelLine1, _parallelLine2) && p.isBetween(_perpendicularLine1, _perpendicularLine2);
    }
}
