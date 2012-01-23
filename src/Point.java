import static java.lang.Math.*;

/**
 */
public class Point {
    private final int _id;
    private final double _latitude;
    private final double _longitude;

    public Point(int id, double latitude, double longitude){
        _latitude = latitude;
        _longitude = longitude;
        _id = id;
    }

    public int getId() {
        return _id;
    }

    public double getLatitude() {
        return _latitude;
    }

    public double getLongitude() {
        return _longitude;
    }

    public double distanceTo(Point p) {
        return distanceBetween(this, p);
    }

    public static double distanceBetween(Point a, Point b) {
        double dLat = toRadians(b.getLatitude() - a.getLatitude());
        double dLon = toRadians(b.getLongitude() - a.getLongitude());
        double e = sin(dLat/2)*sin(dLat/2) + cos(toRadians(a.getLatitude())) * cos(toRadians(b.getLatitude())) * sin(dLon/2) * sin(dLon/2);
        double c = 2 * asin(Math.sqrt(e));
        double distance = 6371000 * c;
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point._latitude, _latitude) != 0) return false;
        if (Double.compare(point._longitude, _longitude) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = _latitude != +0.0d ? Double.doubleToLongBits(_latitude) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = _longitude != +0.0d ? Double.doubleToLongBits(_longitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
