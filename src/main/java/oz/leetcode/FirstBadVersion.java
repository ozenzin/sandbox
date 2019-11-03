package oz.leetcode;

/**
 * <pre>
 * You are a product manager and currently leading a team to develop a new product. Unfortunately,
 * the latest version of your product fails the quality check. Since each version is developed based on the previous
 * version, all the versions after a bad version are also bad.
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following
 * ones to be bad.
 *
 * You are given an API bool isBadVersion(version) which will return whether version is bad. Implement a function to
 * find the first bad version. You should minimize the number of calls to the API.
 *
 * Example:
 *
 * Given n = 5, and version = 4 is the first bad version.
 *
 * call isBadVersion(3) -> false
 * call isBadVersion(5) -> true
 * call isBadVersion(4) -> true
 *
 * Then 4 is the first bad version.
 * </pre>
 */
public class FirstBadVersion {

    static int badVersion;

    public static void main(String[] args) {
        badVersion = Integer.valueOf(args[0]);

        System.out.printf("For given %d we have %d%n", badVersion, firstBadVersion(Integer.valueOf(args[1])));
    }

    public static int firstBadVersion(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;

        int li = 0, ri = n - 1;

        while (li <= ri) {
            int mid = li + (ri - li) / 2;
            if (isBadVersion(mid)) {
                ri = mid - 1;
            } else {
                li = mid + 1;
            }
        }
        return li;
    }


    static boolean isBadVersion(int k) {
        return k >= badVersion;
    }

}
