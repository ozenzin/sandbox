package oz.leetcode;

import java.util.Arrays;

public class MaxSubarray {

    public static void main(String[] args) {
        int[] nums = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();

        System.out.printf("Max sum subarray of %s is %s%n", Arrays.asList(nums), maxSubArray(nums));
    }

    public static int maxSubArray(int[] nums) {
        if (nums.length == 0)
            return Integer.MIN_VALUE;

        int curMax = nums[0], alltimeMax = curMax;

        for (int i = 1, n = nums.length; i < n; i++) {
            curMax = Math.max(nums[i], curMax + nums[i]);
            alltimeMax = Math.max(curMax, alltimeMax);
        }

        return alltimeMax;
    }
}
