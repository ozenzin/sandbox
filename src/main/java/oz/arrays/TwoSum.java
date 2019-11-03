package oz.arrays;

import java.util.Arrays;

/**
 * Given sorted array of numbers find two which sum to a given number
 */
public class TwoSum {

    public static void main(String[] args) {
        String[] numbers = Arrays.copyOf(args, args.length -1);
        Integer targetSum = Integer.valueOf(args[numbers.length]);
        int[] nums = Arrays.stream(numbers).mapToInt(Integer::valueOf).toArray();

        int i = 0, n = nums.length - 1;
        do {
            int sum = nums[i] + nums[n];
            if (sum > targetSum)
                n--;
            else if (sum < targetSum)
                i++;
            else
                break;
        } while (i < n);

        System.out.printf("We pick #%s and #%s to sum to %s from given numbers %s", i, n, targetSum, Arrays.asList(args));
    }
}
