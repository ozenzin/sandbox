package oz;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Sort {

    public static void main(String[] args) {//3 -4 6 45 12 155 -31 5 7 1 0 -4 -1 -7 -7 45 23 34 -12 0
        int[] nums = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();

//        mergeSort(nums, 0, nums.length - 1);
        quickSort(nums, 0, nums.length - 1);
//        heapsort(nums);
        System.out.printf("%s%n%s%n", Arrays.asList(args), IntStream.of(nums).mapToObj(Integer::valueOf).collect(toList()));
    }


    static void mergeSort(int[] nums, int l, int r) {
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
    static void merge(int[] nums, int l, int m, int r) {
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
        while (i < nL) {//if any RIGHT item moved
            nums[l++] = L[i++];
        }
    }



    static void quickSort(int[] nums, int l, int r) {
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
     * @param place2be
     * @param currentPlace
     * @return
     */
    private static int partition(int[] nums, int place2be, int currentPlace) {
        int pivot = nums[currentPlace];

        //let sediment to settle: moving lesser than pivot items downward
        for (int goingDown = currentPlace -1; goingDown >= place2be; ) {
            if (nums[goingDown] < pivot) {
                int tmp = nums[goingDown]; nums[goingDown] = nums[place2be]; nums[place2be] = tmp;//move smaller to bottom
                place2be++;//new bottom
            } else
                goingDown--;//big enough to stay on top
        }
//        if (nums[place2be] <= pivot)//edge case
//            place2be++;

        //now putting pivot at place2be
        if (place2be != currentPlace) {
            nums[currentPlace] = nums[place2be];
            nums[place2be] = pivot;
        }
        return place2be;
    }

    private static void heapsort(int[] nums) {
        if (nums == null || nums.length < 2)
            return;
        int n = nums.length;

        //starting with last parent we walk back thoroughly heapify'ing each subtree
        for (int i = n / 2 -1; i >= 0; i--)
            heapify(nums, i, n);
        //as result we have heap (each node is at least as great as its children) with max on top

        //now moving max from head to tail, taking tail and adding it to the heap. Hence n*log(n) complexity
        // Can I improve that? by:
        //1. leave max intact and move head to the next index instead
        //2. heapify head to tail
        //3. go to #1
        for (int i = n -1; i >= 0; i--) {
            int tmp = nums[0]; nums[0] = nums[i]; nums[i] = tmp;
            heapify(nums, 0, i);
        }
    }

    private static void heapify(int[] nums, int max, int size) {
        int maxInd = max;
        int left = 2 * maxInd + 1;
        int right = 2 * maxInd + 2;

        if (left < size && nums[left] > nums[maxInd])
            maxInd = left;
        if (right < size && nums[right] > nums[maxInd])
            maxInd = right;

        if (maxInd != max) {
            int tmp = nums[max];nums[max] = nums[maxInd];nums[maxInd] = tmp;
            heapify(nums, maxInd, size);
        }

    }

}
