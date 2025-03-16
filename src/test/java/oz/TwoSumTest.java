package oz;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class TwoSumTest {
    
    @Test
    public void twoSum() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] expected = {0, 1};
        int[] actual = TwoSum.twoSum(nums, target);
        assertArrayEquals(expected, actual);
    }
    
    static class TwoSum {
        public static int[] twoSum(int[] nums, int target) {
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[]{};
        }
    }
}
