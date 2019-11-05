package oz.leetcode;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * <pre>
 *  Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
 *
 * The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.
 *
 * You may assume the integer does not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 *
 * Input: [1,2,3]
 * Output: [1,2,4]
 * Explanation: The array represents the integer 123.
 * Example 2:
 *
 * Input: [4,3,2,1]
 * Output: [4,3,2,2]
 * Explanation: The array represents the integer 4321.
 * </pre>
 */
public class IntegerAsArrayPlusOne {

    public static void main(String[] args) {
        int[] digits = Stream.of(args).mapToInt(Integer::valueOf).toArray();
        System.out.printf("%n%s plus one equals %s%n", Arrays.toString(plusOne(digits)));
    }

    private static int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0)
            return new int[] {1};
        int n = digits.length;
        digits[n-1]++;
        if (digits[n-1] > 9) for (int i = n-1; i > 0; i--) {
            digits[i-1] += digits[i] / 10;
            digits[i] %= 10;
        }

        if (digits[0] > 9) {
            digits[0] %= 10;
            int[] plus1 = new int[n + 1];
            plus1[0] = 1;
            for (int i = n; i > 0;) {
                plus1[i] = digits[--i];
            }
            return plus1;
        } else
            return digits;
    }
}
