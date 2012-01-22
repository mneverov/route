import junit.framework.TestCase;

/**
 */

public class PointTest extends TestCase {
    private static double longitude = 11.123456, latitude = 22.654321;

    public void testCreatePoint() {
        Point point = new Point(1, latitude, longitude);
        assertEquals(point.getLatitude(), latitude);
        assertEquals(point.getLongitude(), longitude);
        assertEquals(point.getId(), 1);
    }
}
