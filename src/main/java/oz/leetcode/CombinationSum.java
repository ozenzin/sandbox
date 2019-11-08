package oz.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sums to target.
 *
 * The same repeated number may be chosen from candidates unlimited number of times.
 *
 * Note:
 *
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * Example 1:
 *
 * Input: candidates = [2,3,6,7], target = 7,
 * A solution set is:
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * Example 2:
 *
 * Input: candidates = [2,3,5], target = 8,
 * A solution set is:
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 */
public class CombinationSum {

    public static void main(String[] args) {
        int[] candidates = Stream.of(args).mapToInt(Integer::parseInt).toArray();
        int target = candidates[candidates.length -1];
        candidates = Arrays.copyOfRange(candidates, 0, candidates.length -1);

        System.out.printf("%nFollowing combinations of candidate numbers %s sum up to %d%n%s",
                Arrays.toString(candidates), target, combinationSum(candidates, target));
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new LinkedList<>();
        combinationSumHelper(target, candidates, 0, new LinkedList<>(), result);
        return result;
    }

    private static void combinationSumHelper(int target, int[] nums, int offset, List<Integer> candidates, List<List<Integer>> result) {
        int sum = candidates.stream().mapToInt(Integer::intValue).sum();
        if (sum < target) for (int i = offset; i < nums.length; i++) {
            candidates.add(nums[i]);
            combinationSumHelper(target, nums, i, candidates, result);
            candidates.remove(candidates.size() -1);
        } else if (sum == target) {
            result.add(new ArrayList<>(candidates));
        }
    }
}
