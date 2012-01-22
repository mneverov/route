import java.util.*;

/**
 *
 */
public class PointFinderImpl implements PointFinder {
    private List<Point> _points;

    public PointFinderImpl() {
        this(Collections.<Point>emptyList());
    }

    public PointFinderImpl(List<Point> points) {
        _points = points;
    }

    public List<Point> findPoints(Route route, int width) {
        Set<Point> pointsForRoute = new HashSet<Point>();
        for (Point p : route.getPoints()) {
            pointsForRoute.addAll(findPoints(p, width));
        }
        return sortByDistance(pointsForRoute, route);
    }

    private List<Point> findPoints(Point point, int distance) {
        List<Point> result = new ArrayList<Point>();
        for (Point p : _points) {
            if (Point.getDistance(point, p) < distance) {
                result.add(p);
            }
        }
        return result;
    }

    private List<Point> sortByDistance(Collection<Point> points, final Route route) {
        List<Point> result = new ArrayList<Point>(points);
        if (route.getPoints().isEmpty())
            return result;
        Point start = route.getPoints().get(0);
        Collections.sort(result, byDistanceFrom(start));
        return result;
    }

    private Comparator<Point> byDistanceFrom(Point p) {
        return new DistanceComparator(p);
    }

    private final static class DistanceComparator implements Comparator<Point> {
        private final Point _start;

        public DistanceComparator(Point start) {
            _start = start;
        }

        public int compare(Point p1, Point p2) {
            double distance1 = Point.getDistance(_start, p1);
            double distance2 = Point.getDistance(_start, p2);
            if (distance1 < distance2) {
                return -1;
            } else if (distance1 == distance2) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}


