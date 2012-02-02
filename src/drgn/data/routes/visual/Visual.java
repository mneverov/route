package drgn.data.routes.visual;

import drgn.data.routes.mathematics.PointFinder;
import drgn.data.routes.model.Point;
import drgn.data.routes.model.Route;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
* Created by IntelliJ IDEA.
* User: igor
* Date: 29.01.12
* Time: 10:50
* To change this template use File | Settings | File Templates.
*/
public class Visual {

    static Random random = new Random(); {
        random.setSeed(System.currentTimeMillis());
    }

    public static double randomInRange(double min, double max) {
        //  0 < x < 1
        return min + (random.nextDouble() * (max-min));
    }


    public static void main(String[] args) throws IOException {
        PointGeo points[] = new PointGeo[100000];
        // Generate test data
        for (int i = 0; i < points.length; i++) {
            points[i] = new PointGeo();
            points[i].ll = randomInRange(Canvas.ll_left, Canvas.ll_right);
            points[i].lt = randomInRange(Canvas.lt_bot, Canvas.lt_top);
        }

        PointGeo route[] = new PointGeo[10];
        for (int i = 0; i < route.length; i++) {
            route[i] = new PointGeo();
            route[i].ll = randomInRange(Canvas.ll_left, Canvas.ll_right);
            route[i].lt = randomInRange(Canvas.lt_bot, Canvas.lt_top);
        }

        // Draw test data
        Canvas c = new Canvas();
        for( PointGeo pg : points ) {
            c.drawPoint(pg.ll, pg.lt, 0xFFFFFF);
        }

        for (int i = 0; i < route.length - 1; i++) {
            PointGeo p1 = route[i], p2 = route[i+1];
            c.drawLine(c.getPoint(p1.ll, p1.lt), c.getPoint(p2.ll, p2.lt), 0x00FF00);
        }

        // Computing
        // 1. remapping
        List<Point> points_list = new ArrayList<Point>(points.length);
        for( PointGeo pg : points ) {
            points_list.add(new Point(pg.lt, pg.ll));
        }
        List<Point> route_points_list = new ArrayList<Point>(route.length);
        for( PointGeo pg : route ) {
            route_points_list.add(new Point(pg.lt, pg.ll));
        }
        Route r = new Route(1, route_points_list);

        // 2. computing
        List<Point> res = new PointFinder(points_list).findPoints(r, 300);

        // Draw result
        double color = 0;
        for (Point re : res) {
            color += (double)0xFFFFFF / (double)res.size();
            c.drawPoint(re.getLongitude(), re.getLatitude(), (int)color);
        }

        color = 0;
        for( int t = 0; t < Canvas.x; t++ ) {
            color += (double)0xFFFFFF / (double)Canvas.x;

            for( int i = 0; i < 20 ; i++)
                c.drawPoint(new Point2D(t, i), (int)color);
        }

        ImageIO.write(c.img, "gif", new FileOutputStream("routes-result.gif"));
    }

    public static class PointGeo {
        public double ll, lt;
    }

    public static class Point2D {
        public int x, y;

        public Point2D() {
        }

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Canvas {

        private final static double x = 800;
        private final static double y = 600;
        private final static double ll_left = 30.319471; // долгота
        private final static double lt_bot = 59.875799; // широта
        private final static double ll_right = 30.462074; // 8км на восток от левой точки
        private final static double lt_top = 59.930855;   // 6км на сервер от нижней точки

        BufferedImage img = new BufferedImage((int)x, (int)y, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D gr = img.createGraphics();

        public Point2D getPoint( double ll, double lt) {
            if( ll > ll_right || ll < ll_left )
                throw new IllegalArgumentException();
            if( lt < lt_bot || lt > lt_top )
                throw new IllegalArgumentException();

            double px = (x / (ll_right - ll_left)) * (ll - ll_left);
            double py = y - ( (y / (lt_top - lt_bot)) * (lt - lt_bot) );

            Point2D p = new Point2D();
            p.x = (int) px;
            p.y = (int) py;

            return p;
        }

        public void drawPoint( double ll, double lt, int rgb) {
            drawPoint(getPoint(ll,lt), rgb);
        }

        public void drawPoint(Point2D p, int rgb) {
            img.setRGB(p.x, p.y, rgb);
        }

        public void drawLine(Point2D p1, Point2D p2, int rgb) {
            gr.setPaint(new Color(rgb));
            gr.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

    }

}
//
//package drgn.data.routes.visual;
//
//import drgn.data.routes.mathematics.Geometry;
//import drgn.data.routes.mathematics.PointFinder;
//import drgn.data.routes.model.Point;
//import drgn.data.routes.model.Route;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.*;
//import java.util.List;
//
///**
// * Created by IntelliJ IDEA.
// * User: igor
// * Date: 29.01.12
// * Time: 10:50
// * To change this template use File | Settings | File Templates.
// */
//public class Visual {
//
//    static Random random = new Random(); {
//        random.setSeed(System.currentTimeMillis());
//    }
//
//    public static double randomInRange(double min, double max) {
//        //  0 < x < 1
//        return min + (random.nextDouble() * (max-min));
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        PointGeo points[] = new PointGeo[100000];
//        // Generate test data
//        for (int i = 0; i < points.length; i++) {
//            points[i] = new PointGeo();
//            points[i].ll = randomInRange(Canvas.ll_left, Canvas.ll_right);
//            points[i].lt = randomInRange(Canvas.lt_bot, Canvas.lt_top);
//        }
//
////        PointGeo route[] = new PointGeo[10];
////        for (int i = 0; i < route.length; i++) {
////            route[i] = new PointGeo();
////            route[i].ll = randomInRange(Canvas.ll_left, Canvas.ll_right);
////            route[i].lt = randomInRange(Canvas.lt_bot, Canvas.lt_top);
////        }
//
//        Point center = new Point(Canvas.lt_bot + (Canvas.lt_top - Canvas.lt_bot)/2, Canvas.ll_left + (Canvas.ll_right - Canvas.ll_left)/2);
//
//        Point route[] = new Point[5];
//        route[0] = center;
//        route[1] = Geometry.getPointByBearingAndDistance(route[0], 45, 1000);
//        route[2] = Geometry.getPointByBearingAndDistance(route[1], 135, 1000);
//        route[3] = Geometry.getPointByBearingAndDistance(route[2], 90, 1000);
//        route[4] = Geometry.getPointByBearingAndDistance(route[3], 180, 1000);
//
//
//
//        // Draw test data
//        Canvas c = new Canvas();
////        for( PointGeo pg : points ) {
////            c.drawPoint(pg.ll, pg.lt, 0xFFFFFF);
////        }
//
//        for (int i = 0; i < route.length - 1; i++) {
//            Point p1 = route[i], p2 = route[i+1];
//            c.drawLine(c.getPoint(p1.getLongitude(), p1.getLatitude()), c.getPoint(p2.getLongitude(), p2.getLatitude()), 0x00FF00);
//        }
//
//        // Computing
//        // 1. remapping
//        List<Point> points_list = new ArrayList<Point>(points.length);
//        for( PointGeo pg : points ) {
//            points_list.add(new Point(pg.lt, pg.ll));
//        }
//        List<Point> route_points_list = new ArrayList<Point>(route.length);
//        for( Point pg : route ) {
//            route_points_list.add(pg);
//        }
//        Route r = new Route(1, route_points_list);
//
//        // 2. computing
//        List<Point> res = new PointFinder(points_list).findPoints(r, 300);
//
//        // Draw result
//        double color = 0xFFFFFF;
//        for (Point re : res) {
//            //color += (double)0xFFFFFF / (double)res.size();
//            c.drawPoint(re.getLongitude(), re.getLatitude(), (int)color);
//        }
//
//        color = 0;
//        for( int t = 0; t < Canvas.x; t++ ) {
//            color += (double)0xFFFFFF / (double)Canvas.x;
//
//            for( int i = 0; i < 20 ; i++)
//                c.drawPoint(new Point2D(t, i), (int)color);
//        }
//
//        // Draw scale lines
//
//        Point north = Geometry.getPointByBearingAndDistance(center, 0, 1000);
//        Point west = Geometry.getPointByBearingAndDistance(center, 90, 1000);
//
//        System.out.println(Geometry.distanceBetween(center, north));
//        System.out.println(Geometry.distanceBetween(center, west));
//
////        c.drawLine(c.getPoint(center.getLongitude(), center.getLatitude()), c.getPoint(north.getLongitude(), north.getLatitude()), 0x0000FF);
////        c.drawLine(c.getPoint(center.getLongitude(), center.getLatitude()), c.getPoint(west.getLongitude(), west.getLatitude()), 0x0000FF);
//
//        ImageIO.write(c.img, "gif", new FileOutputStream("routes-result.gif"));
//    }
//
//
//    public static class PointGeo {
//        public double ll, lt;
//
//        public PointGeo() {
//        }
//
//        public PointGeo(double lt, double ll) {
//            this.ll = ll;
//            this.lt = lt;
//
//        }
//    }
//
//    public static class Point2D {
//        public int x, y;
//
//        public Point2D() {
//        }
//
//        public Point2D(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//    public static class Canvas {
//
//        private final static double x = 800;
//        private final static double y = 600;
//        private final static double ll_left = 30.319471; // долгота
//        private final static double lt_bot = 59.875799; // широта
//        private final static double ll_right = 30.462074; // 8км на восток от левой точки
//        private final static double lt_top = 59.930855;   // 6км на сервер от нижней точки
//
//        BufferedImage img = new BufferedImage((int)x, (int)y, BufferedImage.TYPE_3BYTE_BGR);
//        Graphics2D gr = img.createGraphics();
//
//        public Point2D getPoint( double ll, double lt) {
//            if( ll > ll_right || ll < ll_left )
//                throw new IllegalArgumentException();
//            if( lt < lt_bot || lt > lt_top )
//                throw new IllegalArgumentException();
//
//            double px = (x / (ll_right - ll_left)) * (ll - ll_left);
//            double py = y - ( (y / (lt_top - lt_bot)) * (lt - lt_bot) );
//
//            Point2D p = new Point2D();
//            p.x = (int) px;
//            p.y = (int) py;
//
//            return p;
//        }
//
//        public void drawPoint( double ll, double lt, int rgb) {
//            drawPoint(getPoint(ll,lt), rgb);
//        }
//
//        public void drawPoint(Point2D p, int rgb) {
//            img.setRGB(p.x, p.y, rgb);
//        }
//
//        public void drawLine(Point2D p1, Point2D p2, int rgb) {
//            gr.setPaint(new Color(rgb));
//            gr.drawLine(p1.x, p1.y, p2.x, p2.y);
//        }
//
//    }
//
//}