package oz;

import java.time.Duration;
import java.time.Instant;

public class Permutations {

    private static int counter;
    private static int swaps;

    public static void main(String[] args) {
//        permutateFour(args[0].toCharArray());
        Instant start = Instant.now();
        baeldungPermutationRecursively(args[0].toCharArray(), args[0].length());
        long millis = Duration.between(start, Instant.now()).toMillis();
        System.out.printf("%n%d ms %5d swaps", millis, swaps);
        swaps = 0; counter = 0;
        start = Instant.now();
        permutateFour(args[0].toCharArray());
        millis = Duration.between(start, Instant.now()).toMillis();
        System.out.printf("%n%d ms %5d swaps", millis, swaps);

//        swaps = 0; counter = 0;
//        yevgenYampolskiyPermutation(args[0].toCharArray(), 0);
//        System.out.printf("%n%5d swaps", swaps);
//        swaps = 0; counter = 0;
//        permutateMyWay(args[0].toCharArray(), 0);
//        System.out.printf("%n%5d swaps", swaps);
    }

    private static void permutateMyWay(char[] numbers, int k) {
        for (int i = k; i < numbers.length; i++) {
            swap(numbers, i, k);
            permutateMyWay(numbers, k + 1);
            swap(numbers, i, k);//put it back
        }
        if (k + 1 == numbers.length)
            System.out.printf("%n%2d. %s", ++counter, new String(numbers));
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
        https://www.baeldung.com/java-array-permutations
     */
    private static void baeldungPermutationRecursively(char[] numbers, int setToSwap) {
        if (setToSwap < 2)
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
