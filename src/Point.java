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

    public static double getDistance(Point a, Point b) {
        double distance = 111.12 * Math.acos(Math.sin(a.getLatitude()) * Math.sin(b.getLatitude()) +
                                             Math.cos(a.getLatitude()) * Math.cos(b.getLatitude()) * Math.cos(b.getLongitude() - b.getLongitude()));
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
