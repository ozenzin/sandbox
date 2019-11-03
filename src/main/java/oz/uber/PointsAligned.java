package oz.uber;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given their coordinates find max number of points connected with one line.
 */
public class PointsAligned {

    public static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x; this.y = y;
        }
        Point(String comaSeparatedXY) {
            String[] xy = comaSeparatedXY.split(",");
            this.x = Integer.parseInt(xy[0]);
            this.y = Integer.parseInt(xy[1]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + "}";
        }
    }

    static class Line {
        int a, b, c;//for line equation ax + by = c

        Line(int a, int b, int c) {
            this.a = a; this.b = b; this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            /*
            Now, we can't just compare a, b, and c for equality because Line(3, 2, 5) is actually same as Line(6, 4, 10)
            or Line(-9, -6, -15). I.e. lines are equal as long as their (a, b, c)'s are multiples:
            (6, 4, 10) = (3, 2, 5) * 2
            (-9, -6, -15) = (3, 2, 5) * -3

            In other words a1/a2 == b1/b2 == c1/c2 , which I convert to a1*b2*c2 == a2*b1*c2 == a2*b2*c1 in order to stick to integers vs float numbers
             */
            int mult = a * line.b * line.c;
            return mult == line.a * b * line.c && mult == line.a * line.b * c;
        }


        @Override
        public int hashCode() {
            /*
            This method must be consistent with Line.equals(), i.e. all multiples of a triplet (a, b, c) have to have
            same hash. Following implementation based on observation that such triplets' invariant is ratio a:b:c .
             */
            // both a and b can't be 0 at the same time
            if (c == 0) {
                if (a == 0)// line equation is y = 0, i.e. y-axis
                    return Integer.MAX_VALUE;//I can't return Integer.hashCode(b) because b can be any number. I don't want return b/b = 1 because that might collide with the other case when b==0&a!=0
                if (b == 0)// line equation is x = 0, i.e. x-axis
                    return Integer.MIN_VALUE;//see previous comment
                return Objects.hash(a/b, b/a);
            } else if (a == 0) {//b != 0, see above
                return Objects.hash(b / c, c / b);
            } else if (b == 0) {
                return Objects.hash(a / c, c / a);
            }
            return Objects.hash(a/b, a/c, b/a, b/c, c/a, c/b);
        }
    }

    /**
     * Given their coordinates find max number of points connected with one line
     * @param args points' coordinates in form "x1,y2 x2,y2 x3,y3". I.e. coma separated coordinates for points separated with space " "
     */
    public static void main(String[] args) {
        List<Point> points = Arrays.stream(args).map(Point::new).collect(Collectors.toList());
//        List<Point> points = Arrays.asList(new Point(1, 2), new Point(5, 10), new Point(3, 6), new Point(-1, -2), new Point(-3, -6) );

        System.out.printf("%s of points %s are on the same line%n", aligned(points), points);
    }

    private static int aligned(List<Point> points) {
        Map<Line, Set<Point>> counts = new HashMap<>();
        for (Point p1 : points) {
            for (Point p2 : points) {
                if (!p1.equals(p2)) {
                    counts.computeIfAbsent(line(p1, p2), l -> new HashSet<>(Arrays.asList(p1, p2)));
                    counts.computeIfPresent(line(p1, p2), (l, set) -> {set.add(p2); return set;});
                }
            }
        }

        return counts.values().stream().mapToInt(Set::size).max().orElse(0);
    }

    /**
     * Pair <i>a</i> and <i>b</i> in {@code y = ax + b} of line defined by two points.
     * @param p1
     * @param p2
     * @return
     */
    private static Line line(Point p1, Point p2) {
        int a = p1.y - p2.y;
        int b = p1.x - p2.x;
        if (a == 0)
            return new Line(0, 1, p1.y);
        if (b == 0)
            return new Line(1, 0, p1.x);

        int c = p1.y * b - a * p1.x;// from y = ax + b where a = (y1 - y2) / (x1 - x2) and using any of two given points,
        return new Line(a, b, c);
    }

}
