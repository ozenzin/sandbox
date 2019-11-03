package oz;

/**
 * Compute square root
 */
public class SqRoot {

    public static void main(String[] args) {
        Double d = Double.valueOf(args[0]);

        System.out.printf("√%.5f = %.5f%n", d, squareRoot(d));
    }

    static double squareRoot(final double d) {
        double start, end;

        if (d < 1.0) {
            start = d;
            end = 1.0;
        } else if (d > 1.0) {
            start = 1.0;
            end = d;
        } else {
            return d;// √1.0 = 1.0
        }

        while (compareDoubles(start, end) != D_ORDER.EQUAL) {
            double half = start + (end - start) * .5;
            switch (compareDoubles(half*half, d)) {
                case LESS_THAN:
                    start = half;
                    break;
                case GREATER_THAN:
                    end = half;
                    break;
                case EQUAL:
                    return half;
            }
        }
        return start;
    }

    enum D_ORDER {LESS_THAN, EQUAL, GREATER_THAN};

    static D_ORDER compareDoubles(double d1, double d2) {
        final double EPS = .0000001;
        double diff = (d1 - d2) / Math.max(Math.abs(d1), Math.abs(d2));
        return diff < -EPS ? D_ORDER.LESS_THAN : (diff > EPS ? D_ORDER.GREATER_THAN : D_ORDER.EQUAL);
    }
}
