package oz;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * Do not call for more than 10 elements
 *
 * https://www.baeldung.com/wp-content/uploads/2019/01/Screenshot-2018-12-30-at-09.40.23-e1546159288775.png
 */
public class Permutations {

    private static int counter;
    private static int swaps;

    public static void main(String[] args) {
        if (args.length > 0 && args[0].length() > 10) {
            args[0] = new String(Arrays.copyOfRange(args[0].toCharArray(), 0, 10));
            System.out.printf("%nDo not call for more than 10 elements!!! Too much time to calculate!%nTaking first 10: %s", args[0]);
        }

//        permutateFour(args[0].toCharArray());
//        Instant start = Instant.now();
        baeldungPermutationRecursively(args[0].toCharArray(), args[0].length());
//        long millis = Duration.between(start, Instant.now()).toMillis();
//        System.out.printf("%n%d ms %5d swaps", millis, swaps);
//        swaps = 0; counter = 0;
//        start = Instant.now();
//        permutateFour(args[0].toCharArray());
//        millis = Duration.between(start, Instant.now()).toMillis();
//        System.out.printf("%n%d ms %5d swaps", millis, swaps);

//        swaps = 0; counter = 0;
//        yevgenYampolskiyPermutation(args[0].toCharArray(), 0);
//        System.out.printf("%n%5d swaps", swaps);
    }

    private static void permutateFour(char[] numbers) {
        for (int a = 0; a < numbers.length; a++)
            for (int b = 0; b < numbers.length; b++) if (b != a)
                for (int c = 0; c < numbers.length; c++) if (c != a && c != b)
                    for (int d = 0; d < numbers.length; d++) if (d != a && d != b && d != c)
                        System.out.printf("%n%2d. %s", ++counter,
                                new String(new char[] {numbers[a], numbers[b], numbers[c], numbers[d]}));
    }

    /*
    Heap's algorithm!
        https://www.baeldung.com/java-array-permutations
     */
    private static void baeldungPermutationRecursively(char[] numbers, int setToSwap) {
        if (setToSwap < 2)//nothing to shuffle
            System.out.printf("%n%2d. %s", ++counter, new String(numbers));
        else {
            for (int i = 0; i < setToSwap - 1; i++) {
                baeldungPermutationRecursively(numbers, setToSwap - 1);
                if ((setToSwap & 1) == 0)
                    swap(numbers, i, setToSwap -1);
                else
                    swap(numbers, 0, setToSwap -1);
            }
            baeldungPermutationRecursively(numbers, setToSwap -1);
        }
    }

    /*
    https://stackoverflow.com/questions/2920315/permutation-of-array/14444037#14444037
    Analisys shows it relies on way too many swaps, compared to Heap's algo (found on baeldung)
     */
    private static void yevgenYampolskiyPermutation(char[] numbers, int k) {
        for (int i = k; i < numbers.length; i++) {
            swap(numbers, i, k);
            yevgenYampolskiyPermutation(numbers, k + 1);
            swap(numbers, i, k);
        }
        if (k == numbers.length -1)
            System.out.printf("%n%2d. %s", ++counter, new String(numbers));
    }



    public static void swap(char[] chars, int from, int to) {
        char tmp = chars[from]; chars[from] = chars[to]; chars[to] = tmp;
        ++swaps;
    }
}
