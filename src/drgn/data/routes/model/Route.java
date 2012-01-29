package drgn.data.routes.model;

import java.util.List;

/**
 *
 */
public class Route {
    private final int _id;
    private List<Point> _points;

    public Route(int id, List<Point> points){
        _points = points;
        _id = id;
    }

    public int getId() {
        return _id;
    }

    public List<Point> getPoints() {
        return _points;
    }

    public void setPoints(List<Point> points) {
        _points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (_points != null ? !_points.equals(route._points) : route._points != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return _points != null ? _points.hashCode() : 0;
    }
}
