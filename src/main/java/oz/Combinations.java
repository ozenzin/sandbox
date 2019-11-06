package oz;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Combinations of K out of N given.
 */
public class Combinations {

    /**
     *
     * @param args K = args[0], thus N = args.length-1
     */
    public static void main(String[] args) {
        int K = Integer.parseInt(args[0]);
        int[] nums = Stream.of(Arrays.copyOfRange(args, 1, args.length)).mapToInt(Integer::valueOf).toArray();

        System.out.printf("%nCombinations of %s of %s:%n%s%n", K, Arrays.toString(nums), combinations(K, nums));
    }

    private static List<List<Integer>> combinations(int k, int[] nums) {
        List<List<Integer>> result = new LinkedList<>();

        directedCombinations(nums, k, 0, new LinkedList<Integer>(), result);
        return result;
    }

    private static void directedCombinations(int[] nums, int k, int offset, LinkedList<Integer> partial, List<List<Integer>> result) {

        if (partial.size() == k) {
            result.add(new LinkedList<>(partial));
            return;
        }

        int remaining = k - partial.size();
        for (int i = offset; i < nums.length && i <= nums.length -remaining; ++i) {
            partial.add(nums[i]);
            directedCombinations(nums, k, i + 1, partial, result);
            partial.remove(partial.size() -1);
        }
    }
}
