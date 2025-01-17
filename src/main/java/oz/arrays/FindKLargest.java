package oz.arrays;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * Given unsorted array of N elements find K-largest in Log (N) time.
 */
public class FindKLargest {

    public static void main(String[] args) {
        if (args.length < 2 )
            return;
        int K = Integer.valueOf(args[0]);
        args = Arrays.copyOfRange(args, 1, args.length);
        int[] nums = Stream.of(args).mapToInt(Integer::valueOf).toArray();

        if (K > nums.length)
            System.out.printf("%nOnly %d elements, while asking for %dth largest! Give more numbers, please.", nums.length, K);
        else
            System.out.printf("%nThe %dth largest element in %s is %d%n", K, Arrays.toString(nums), findKLargestIn(K, nums));
    }

    private static int findKLargestIn(int K, int[] nums) {
        int left = 0, right = nums.length -1, result = Integer.MIN_VALUE;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (left <= right) {
            int randomPivotIndx = random.nextInt( right - left + 1) + left;
            int sortedOutPivotIndx = pivotize(nums, randomPivotIndx, left, right);
            if (nums.length - K > sortedOutPivotIndx)
                left = sortedOutPivotIndx + 1;
            else if (nums.length - K < sortedOutPivotIndx)
                right = sortedOutPivotIndx -1;
            else {
                result = nums[sortedOutPivotIndx];
                break;
            }
        }
        return result;
    }

    private static int pivotize(int[] nums, int randomPivotIndx, int left, int right) {
        int pivot = nums[randomPivotIndx];
        swap(nums, randomPivotIndx, right);
        int tmpPivotPosition = right;
        while (left < right) {
            if (nums[left] < pivot)
                left++;
            else
                swap(nums, left, --right);
        }
        swap(nums, left, tmpPivotPosition);
        return left;
    }

    public static void swap(int[] nums, int from, int to) {
        int tmp = nums[from]; nums[from] = nums[to]; nums[to] = tmp;
    }
}
