package drgn.data.routes.mathematics;

import drgn.data.routes.model.Point;
import drgn.data.routes.model.Route;

import java.util.*;

/**
 *
 */
public class PointFinder {

    private List<Point> _points;

    public PointFinder() {
        this(Collections.<Point>emptyList());
    }

    public PointFinder(List<Point> points) {
        _points = points;
    }

    public List<Point> findPoints(Route route, int width) {
        List<Point> points = route.getPoints();
        if (points.isEmpty() || points.size() == 1)
            return Collections.emptyList();

//        Set<Point> pointsForRoute = new HashSet<Point>();
        List<Point> pointsForRoute = new ArrayList<Point>();
        Point prevPoint = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Point curPoint = points.get(i);
            pointsForRoute.addAll(pointsForSegment(prevPoint, curPoint, width));
            prevPoint = curPoint;
        }
//        return new ArrayList<Point>(pointsForRoute);
        return pointsForRoute;
    }

    private List<Point> pointsForSegment(Point segmentStart, Point segmentFinish, int width) {
        DistanceLessThan predicate = new DistanceLessThan(segmentStart, segmentFinish, width);
        List<Point> pointForSegment = new ArrayList<Point>();
        for (Point p : _points) {
            if (!p.isOnRoute() && predicate.apply(p)) {
                pointForSegment.add(p);
                p.set_isOnRoute(true);
            }
        }
        sortByProjectionDistance(segmentStart, segmentFinish, pointForSegment);
        return pointForSegment;
    }

    private void sortByProjectionDistance(final Point segmentStart, final Point segmentFinish, List<Point> points) {
        Collections.sort(points, byDistanceOfProjectionOn(segmentStart, segmentFinish));
    }

    private Comparator<Point> byDistanceOfProjectionOn(Point segmentStart, Point segmentFinish) {
        return new ProjectionDistanceComparator(segmentStart, segmentFinish);
    }

    private final static class ProjectionDistanceComparator implements Comparator<Point> {
        private final Point _segmentStart;
        private final Point _segmentFinish;

        private ProjectionDistanceComparator(Point segmentStart, Point segmentFinish) {
            _segmentStart = segmentStart;
            _segmentFinish = segmentFinish;
        }

        public int compare(Point o1, Point o2) {
            Point proj1 = Geometry.findProjectionOn(o1, _segmentStart, _segmentFinish);
            Point proj2 = Geometry.findProjectionOn(o2, _segmentStart, _segmentFinish);
            return Double.compare(Geometry.distanceBetween(_segmentStart, proj1),
                    Geometry.distanceBetween(_segmentStart, proj2));
        }
    }
}


