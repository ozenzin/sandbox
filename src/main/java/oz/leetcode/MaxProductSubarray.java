package oz.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.
 * <pre>
 * Example 1:
 *
 * Input: [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 * Example 2:
 *
 * Input: [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 * </pre>
 */
public class MaxProductSubarray {

    public static void main(String[] args) {
        int[] nums = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();

        System.out.printf("Max Product sub-array of %s is %s%n", Arrays.asList(args), maxProduct(nums));
    }

    private static int maxProduct(int[] nums) {
        return maxProductHelper(nums, 0, nums.length);
    }

    private static int maxProductHelper(int[] nums, int start, int end) {
        int maxP = Integer.MIN_VALUE;
        int length = end - start;

        if (length <= 0)
            return maxP;
        if (length == 1)
            return nums[start];

        List<Integer> negs = new LinkedList<>();

        for (int i = start; i < end; i++) {
            if (nums[i] == 0) {
                maxP =  Math.max(maxProductHelper(nums, start, i), maxProductHelper(nums, i +1, end));
                return Math.max(maxP, nums[i]);
            } else if (nums[i] < 0)
                negs.add(i);
        }

        if ((negs.size() & 1) == 0)
            return productOf(nums, start, end);
        else {//odd number of negatives
            int neg = negs.get(0);
            maxP =  Math.max(maxProductHelper(nums, start, neg), maxProductHelper(nums, neg +1, end));
            maxP = Math.max(maxP, nums[neg]);

            if (negs.size() > 1) {
                neg = negs.get(negs.size() -1);
                maxP = Math.max(maxProductHelper(nums, start, neg), maxP);
                maxP = Math.max(maxP, maxProductHelper(nums, neg +1, end));
                maxP = Math.max(maxP, nums[neg]);
            }
            return maxP;
        }
    }

    private static int productOf(int[] nums, int start, int end) {
        int product = 1;
        while (start < end) {
            product *= nums[start++];
        }
        return product;
    }
}
