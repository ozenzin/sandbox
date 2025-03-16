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
        int start = 0, end = nums.length -1, result = Integer.MIN_VALUE;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (start <= end) {
            int randomPivotIndx = random.nextInt( end - start + 1) + start;
            int sortedOutPivotIndx = pivotize(nums, randomPivotIndx, start, end);
            if (nums.length - K > sortedOutPivotIndx)
                start = sortedOutPivotIndx + 1;
            else if (nums.length - K < sortedOutPivotIndx)
                end = sortedOutPivotIndx -1;
            else {
                result = nums[sortedOutPivotIndx];
                break;
            }
        }
        return result;
    }

    private static int pivotize(int[] nums, int randomPivotIndx, int start, int end) {
        int pivot = nums[randomPivotIndx];
        swap(nums, randomPivotIndx, end);
        int tmpPivotPosition = end;
        while (start < end) {
            if (nums[start] < pivot)
                start++;
            else
                swap(nums, start, --end);
        }
        swap(nums, start, tmpPivotPosition);
        return start;
    }

    public static void swap(int[] nums, int from, int to) {
        int tmp = nums[from]; nums[from] = nums[to]; nums[to] = tmp;
    }
}
