package oz;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Sort {

    public static void main(String[] args) {//3 -4 6 45 12 155 -31 5 7 1 0 -4 -1 -7 -7 45 23 34 -12 0
        int[] nums = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();
        mergeSort(nums, 0, nums.length - 1);
        System.out.printf("%nMergeSort of %s%n%s%n", Arrays.asList(args), IntStream.of(nums).boxed().collect(toList()));

        nums = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();
        quickSort(nums, 0, nums.length - 1);
        System.out.printf("%nQuickSort sort of %s%n%s%n", Arrays.asList(args), IntStream.of(nums).boxed().collect(toList()));

        nums = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();
        heapsort(nums);
        System.out.printf("%nHeapSort of %s%n%s%n", Arrays.asList(args), IntStream.of(nums).boxed().collect(toList()));
    }


    public static void mergeSort(int[] nums, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(nums, l, m);
            mergeSort(nums, m +1, r);

            merge(nums, l, m, r);
        }
    }

    /**
     * Improved merge step of mergesort:
     * in attempt to merge in place it only copies LEFT half before merging back to sorted array.
     * This way required space is cut in half. In fact, when RIGHT contains items larger than LEFT this procedure returns earlier.
     * The more such items the earlier it returns (up to half time). Which would benefit partially sorted arrays (right order).
     *
     * @param nums
     * @param l
     * @param m
     * @param r
     */
    private static void merge(int[] nums, int l, int m, int r) {
        int nL = m + 1 - l;

        int[] L = Arrays.copyOfRange(nums, l, m +1);

        int i = 0;
        while ( i < nL && m < r ) {
            if (L[i] > nums[m + 1]) {
                nums[l++] = nums[++m];
            } else {//if equals than no items move, attempt to achieve stable sorting
                nums[l++] = L[i++];
            }
        }
        while (i < nL) {//happens if any of right (m..r) items moved
            nums[l++] = L[i++];
        }
    }



    public static void quickSort(int[] nums, int l, int r) {
        if (l < r) {
            int pi = partition(nums, l, r);

            quickSort(nums, l, pi -1);
            quickSort(nums, pi +1, r);
        }
    }

    /**
     * Improved partition step of quicksort:
     *  - less swaps (especially strange-looking swap of the element with itself!)
     *  - more neat indices traversing
     *
     * @param nums
     * @param bottom
     * @param currentPlace
     * @return
     */
    private static int partition(int[] nums, int bottom, int currentPlace) {
        int pivot = nums[currentPlace];

        //moving lesser than pivot items down, starting from top
        for (int goingDown = currentPlace -1; goingDown >= bottom; ) {
            if (nums[goingDown] < pivot) {
                swap(nums, goingDown, bottom);
                bottom++;//new bottom
            } else
                goingDown--;//big enough to stay on top
        }

        //now putting pivot at its place2be
        if (bottom != currentPlace) {//just in case, might save us a swap
            nums[currentPlace] = nums[bottom];
            nums[bottom] = pivot;
        }
        return bottom;
    }

    public static void heapsort(int[] nums) {
        if (nums == null || nums.length < 2)
            return;
        int n = nums.length;

        //starting with last parent node we walk back thoroughly heapify'ing each subtree
        for (int i = n / 2 -1; i >= 0; i--)
            heapify(nums, i, n);
        //as result we have heap (each node is at least as great as its children) with max on top

        //now moving max from head to tail, extracting tail and adding it to the heap. Hence n*log(n) complexity
        // Can I improve that? by:
        //1. leave max intact and move head to the next index instead
        //2. heapify head to tail
        //3. go to #1
        for (int i = n -1; i >= 0; i--) {
            swap(nums, 0, i);
            heapify(nums, 0, i);
        }
    }

    private static void heapify(int[] nums, int max, int size) {
        int trueMax = max;
        int left = 2 * trueMax + 1;
        int right = 2 * trueMax + 2;

        if (left < size && nums[left] > nums[trueMax])
            trueMax = left;
        if (right < size && nums[right] > nums[trueMax])
            trueMax = right;

        if (trueMax != max) {//trueMax > max
            swap(nums, max, trueMax);
            heapify(nums, trueMax, size);
        }
    }

    private static void swap(int[] nums, int i1, int i2) {
        int tmp = nums[i1];nums[i1] = nums[i2];nums[i2] = tmp;
    }

}
