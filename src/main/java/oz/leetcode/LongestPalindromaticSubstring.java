package oz.leetcode;

/**
 * <pre>
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 *
 * Example 1:
 *
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * Example 2:
 *
 * Input: "cbbd"
 * Output: "bb"
 * </pre>
 */
public class LongestPalindromaticSubstring {

    public static void main(String[] args) {
        System.out.printf("%s is longest palindrom from %s", longestPalindrome(args[0]), args[0]);
    }

    public static String longestPalindrome(String s) {
        if (s.length() < 2)
            return s;
        char[] cs = s.toCharArray();
        int p0 = 0, pN = 1, maxP = pN - p0;
        for (int i = 0, j = 0, k = 0, n = cs.length; i < n-1; i++) {
            j = i;
            while (i < n-1 && cs[i] == cs[i+1]) {
                i++;
            }
            k = i;
            if (k - j + 1 > 1) {//repetition detected, trying to extend it now:
                while (j > 0 && k + 1 < n && cs[j - 1] == cs[k + 1]) {
                    j--; k++;
                }
                if (k - j + 1 > maxP) {
                    p0 = j;
                    pN = k + 1;
                    maxP = pN - p0;
                }
            }

            if (i < n -2 && cs[i] == cs[i+2]) {//one odd char in between
                j = i;
                k = i + 2;
                while (j > 0 && k + 1 < n && cs[j - 1] == cs[k + 1]) {
                    j--; k++;
                }
                if (k - j + 1 > maxP) {
                    p0 = j;
                    pN = k + 1;
                    maxP = pN - p0;
                }
            }
        }

        return s.substring(p0, pN);
    }
}
