package oz.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 *
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 *
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 *
 * A solution set is:
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 * </pre>
 */
public class ThreeSum {

    public static void main(String[] args) {
        int[] nums = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();

        System.out.printf("For given array %s following triplets sum up to 0: %n%s%n", Arrays.asList(args), threeSum(nums));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        if (nums.length < 3)
            return result;

        Arrays.sort(nums);

        if ((nums[0] < 0 && nums[n - 1] <= 0) || (nums[0] >= 0 && nums[n - 1] > 0))//guarantees nums[0] < 0 && nums[n-1] > 0
            return result;

        int minPstv = 0;
        while (nums[++minPstv] < 0) ;
        if (nums[minPstv] == 0 && nums[minPstv-1] != 0 && nums[minPstv+1] == 0) minPstv++;
        int maxNeg = minPstv - 1;

        for (int neg = 0; neg <= maxNeg; neg++) {
            for (int pstv = n - 1; pstv >= minPstv; pstv--) {
                int twoSum = nums[neg] + nums[pstv];

                int m = Arrays.binarySearch(nums, (twoSum > 0) ? neg + 1 : minPstv , (twoSum > 0) ? maxNeg + 1 : pstv, -twoSum);
                if (m > 0) {
                    result.add(Arrays.asList(nums[neg], nums[pstv], nums[m]));
                }
                while ((pstv > minPstv) && (nums[pstv] == nums[pstv - 1])) {
                    pstv--;
                }
            }
            while ((neg < maxNeg) && nums[neg] == nums[neg + 1]) {
                neg++;
            }
        }

        return result;
    }


}
