package oz;

public class BitviseProduct {

    public static void main(String[] args) {
        long l1 = Long.valueOf(args[0]);
        long l2 = Long.valueOf(args[1]);

        System.out.printf("%d * %d = %d", l1, l2, product(l1, l2));
    }

    static long sum(long l1, long l2) {
        while (l2 != 0) {
            long carry = l1 & l2;
            l1 = l1 ^ l2;
            l2 = carry <<1;
        }
        return l1;
    }

    static long product(long l1, long l2) {
        long res = 0;
        while (l1 != 0) {
            if ((l1 & 1) > 0) {
                res = sum(res, l2);
            }
            l1 >>= 1;
            l2 <<= 1;
        }
        return res;
    }
}

