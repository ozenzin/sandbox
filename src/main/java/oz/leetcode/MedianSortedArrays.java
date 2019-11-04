package oz.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 * You may assume nums1 and nums2 cannot be both empty.
 * <pre>
 * Example 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * The median is 2.0
 *
 * Example 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * The median is (2 + 3)/2 = 2.5</pre>
 */
public class MedianSortedArrays {

    public static void main(String[] args) {
        int[] ar1 = Arrays.stream(args[0].split(",")).map(Integer::valueOf).mapToInt(Integer::intValue).toArray();
        int[] ar2 = Arrays.stream(args[1].split(",")).map(Integer::valueOf).mapToInt(Integer::intValue).toArray();

        System.out.println(Arrays.asList(args));
        System.out.printf("Median: %f%n", findMedianSortedArrays(ar1, ar2) );
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length, s1 = 0, s2 = 0;

        if (len1 == 0) {
            return median(nums2, s2, len2);
        }

        if (len2 == 0) {
            return median(nums1, s1, len1);
        }

        boolean isOdd = ((len1 + len2) & 1) > 0;
        int med = (len1 + len2) >>1;
        int minNums1 = Math.max(0, (len1 - len2) >> 1);
        int maxNums1 = Math.min(len1, med);

        while (minNums1 < maxNums1) {
            int exactNums1 = minNums1 + ((maxNums1 - minNums1) >>1);
            int nums1Upper = exactNums1 > 0 ? nums1[exactNums1 - 1] : Integer.MIN_VALUE;
            int nums1Lower = exactNums1 < len1 ? nums1[exactNums1] : Integer.MAX_VALUE;

            int nums2Upper = (med - exactNums1) > 0 ? nums2[med - 1 - exactNums1] : Integer.MIN_VALUE;
            int nums2Lower = (med - exactNums1) < len2 ? nums2[med - exactNums1] : Integer.MAX_VALUE;

            if (nums1Lower < nums2Upper)
                minNums1 = ++exactNums1;
            else if (nums1Upper > nums2Lower)
                maxNums1 = --exactNums1;
            else {
                if (isOdd)
                    return Math.max(nums1Upper, nums2Upper);
                else
                    return (nums1Upper + nums2Upper) / 2.0;
            }
        }

        if (isOdd)
            return Math.max(minNums1 > 0 ? nums1[minNums1 -1] : Integer.MIN_VALUE, (med - 1 - minNums1) >= 0 ? nums2[med - 1 - minNums1] : Integer.MIN_VALUE);
        else
            return (nums1[minNums1 -1] + nums2[med - 1 - minNums1]) / 2.0;
    }

    private static double median(int[] nums, int s, int e) {
        int len = e - s;

        if ((len & 1) > 0)
            return nums[len >>1];
        else
            return (nums[(len >>1) -1] + nums[len >>1]) / 2.0;
    }

    public static double findMedianSortedArraysHelperBad(int[] nums1, int[] nums2, int s1, int e1, int s2, int e2) {
        int len1 = e1 - s1, len2 = e2 - s2;

        if (len1 < 3 && len2 < 3) {
            if (len1 == 1 && len2 == 1)
                return (nums1[s1] + nums2[s2]) / 2.0;
            else if (len1 == 1 && len2 == 2)
                return nums1[s1] < nums2[s2] ? nums2[s2] : Math.min(nums1[s1], nums2[s2 + 1]);
            else if (len1 == 2 && len2 == 1)
                return nums2[s2] < nums1[s1] ? nums1[s1] : Math.min(nums2[s2], nums1[s1 + 1]);
            else if (len1 == 2 && len2 == 2) {
                if (nums1[s1 + 1] <= nums2[s2] || (nums1[s1] < nums2[s2] && nums1[s1 + 1] < nums2[s2 + 1]))
                    return (nums1[s1 + 1] + nums2[s2]) / 2.0;
                else if (nums2[s2 + 1] <= nums1[s1] || (nums2[s2] < nums1[s1] && nums2[s2 + 1] < nums1[s1 + 1]))
                    return (nums2[s2 + 1] + nums1[s1]) / 2.0;
                else if (nums1[s1] > nums2[s2] && nums1[s1 + 1] < nums2[s2 + 1])
                    return (nums1[s1] + nums1[s1 + 1]) / 2.0;
                else if (nums2[s2] > nums1[s1] && nums2[s2 + 1] < nums1[s1 + 1])
                    return (nums2[s2] + nums2[s2 + 1]) / 2.0;
            }

        }

        double m1 = median(nums1, s1, e1);
        double m2 = median(nums2, s2, e2);


        if (Double.compare(m1, m2) < 0) {
            return findMedianSortedArraysHelperBad(nums1, nums2, s1 + (len1 >>1), e1, s2, e2 - (++len2 >>1) );
        } else if (Double.compare(m1, m2) > 0) {
            return findMedianSortedArraysHelperBad(nums1, nums2,  s1, e1 - (len1 >>1), s2 + (++len2 >>1), e2);
        } else {//med1 == med2
            return m1;
        }
    }
}
