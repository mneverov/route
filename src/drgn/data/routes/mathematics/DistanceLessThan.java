package drgn.data.routes.mathematics;

import drgn.data.routes.model.Point;
import static java.lang.Math.*;

/**
 *
 */
public class DistanceLessThan  {

    private final Point _p1;
    private final Point _p2;
    private final int _width;
    private final double _bearing;
    private final double _segmentRouteLength;

    public DistanceLessThan(Point p1, Point p2, int width) {
        _p1 = p1;
        _p2 = p2;
        _width = width;
        _bearing = Geometry.getBearingTo(p1, p2);
        _segmentRouteLength = Geometry.distanceBetween(_p1, _p2);
    }

    public boolean apply(Point p) {
        double bearing = Geometry.getBearingTo(_p1, p);
        double a = abs(_bearing - bearing);
        double angle = toRadians(a % 180);
        double dist = Geometry.distanceBetween(_p1, p);
        double projectionLength = abs(dist * tan(angle));
        if (projectionLength <= _width) {
            double l = abs(dist / cos(angle));
            if (a >= 90 && l <= _width || a < 90 && l <= _segmentRouteLength + _width) {
                p.set_length(l);
            }
        }
        return p.get_length() >= 0;
    }
}
