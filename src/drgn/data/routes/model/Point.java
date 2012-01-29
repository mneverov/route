package drgn.data.routes.model;

/**
 *
 */
public class Point {
    private final int _id;
    private final double _latitude;
    private final double _longitude;
    private boolean _onRoute;

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
        _onRoute = false;
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

    public boolean isOnRoute() {
        return _onRoute;
    }

    public void set_isOnRoute(boolean onRoute) {
        _onRoute = onRoute;
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
