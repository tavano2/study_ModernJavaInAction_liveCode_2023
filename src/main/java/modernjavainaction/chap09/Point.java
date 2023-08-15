package modernjavainaction.chap09;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Point {
    private final int x;
    private final int y;

    private Point(int x,int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Point moveRightBy(int x) {
        return new Point(this.x+x, this.y);
    }

    public final static Comparator<Point> compareByXandThenY = Comparator.comparing(Point::getX).thenComparing(Point::getY);

    public static void main (String[] args) throws Exception {
        Point p1 = new Point(5,5);
        Point p2 = p1.moveRightBy(10);
        assertEquals(15,p2.getX());
        assertEquals(5,p2.getY());
        testMoveAllPointsRightBy();
    }

    @Test
    public void testComparingTwoPoints() throws Exception {
        Point p1 = new Point(10,15);
        Point p2 = new Point(10,20);
        int result = Point.compareByXandThenY.compare(p1,p2);
        assertTrue(result < 0);
    }

    public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
        return points.stream()
                .map(point -> new Point(point.getX() + x, point.getY()))
                .collect(Collectors.toList());
    }
    @Test
    public static void testMoveAllPointsRightBy() throws Exception {
        List<Point> points = Arrays.asList(new Point(5,5), new Point(10,5));
        List<Point> expectedPoints = Arrays.asList(new Point(15,5), new Point(20,5));
        List<Point> newPoints = Point.moveAllPointsRightBy(points,10);
    }
}
