import static java.lang.Math.*;

/**
 */
public class Point {
    private final int _id;
    private final double _latitude;
    private final double _longitude;
    private final static int EARTH_RADIUS = 6371000;

    public Point(int id, double latitude, double longitude){
        if (latitude <= -90 || latitude >= 90) {
            throw new IllegalArgumentException("latitude not in range -90..90");
        }
        if (longitude <= -180 || longitude >= 180) {
            throw new IllegalArgumentException("longitude not in range -180..180");
        }
        _latitude = latitude;
        _longitude = longitude;
        _id = id;
    }

    public Point(double latitude, double longitude) {
        this(-1, latitude, longitude);
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

    public static double distanceBetween(Point p1, Point p2) {
        double dLat = toRadians(p2.getLatitude() - p1.getLatitude());
        double dLon = toRadians(p2.getLongitude() - p1.getLongitude());
        double e = sin(dLat/2)*sin(dLat/2) + cos(toRadians(p1.getLatitude())) * cos(toRadians(p2.getLatitude())) * sin(dLon/2) * sin(dLon/2);
        double c = 2 * asin(Math.sqrt(e));
        double distance = EARTH_RADIUS * c;
        return distance;
    }

    public boolean isOnLine(Line line) {
        return (line.getA() * _latitude + line.getB() * _longitude + line.getC()) == 0;
    }

    public boolean isBetween(Line line1, Line line2) {
        double y1 = 0 == line1.getB() ? line1.getC() : (-line1.getA() * _latitude - line1.getC()) / line1.getB();
        double y2 = 0 == line2.getB() ? line2.getC() : (-line2.getA() * _latitude - line2.getC()) / line2.getB();
        return y1 > _longitude && y2 < _longitude;
    }

    public double getBearingTo(Point p) {
        double y = sin(toRadians(p._longitude - _longitude)) * cos(toRadians(p._latitude));
        double x = cos(toRadians(_latitude)) * sin(toRadians(p._latitude)) -
                sin(toRadians(_latitude)) * cos(toRadians(p._latitude)) * cos(toRadians(p._longitude - _longitude));
        double bearing = toDegrees(atan2(y, x));
        double normalizeBearing = (bearing + 360) % 360;
        return normalizeBearing;
    }

    public Point getPointByBearingAndDistance(double bearing, double distance) {
        double latitude = asin(sin(toRadians(_latitude)) * cos(distance / EARTH_RADIUS) +
                       cos(toRadians(_latitude)) * sin(distance / EARTH_RADIUS) * cos(toRadians(bearing)));
        double longitude = toRadians(_longitude) +
                atan2(sin(toRadians(bearing)) * sin(distance / EARTH_RADIUS) * cos(toRadians(_latitude)),
                        cos(distance / EARTH_RADIUS) - sin(toRadians(_latitude)) * sin(toRadians(latitude)));
        double roundLat =   ((int)(toDegrees(latitude) * 1000000)) / 1000000.0;
        double roundLong =   ((int)(toDegrees(longitude) * 1000000))/ 1000000.0;
        Point result = new Point(roundLat, roundLong);
        return result;
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
