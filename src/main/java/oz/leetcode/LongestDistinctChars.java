package oz.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class LongestDistinctChars {

    public static void main(String[] args) {
        Set<Character> dChars = new TreeSet<>();
        for (char c : args[0].toCharArray()) {
            dChars.add(c);
        }
        System.out.printf("The answer is %s, with the length of %d%n", new ArrayList<>(dChars), dChars.size());


        System.out.printf("The answer is %s, with the length of %d%n", new ArrayList<>(dChars), lengthOfLongestSubstring(args[0]));
    }

    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        if (chars.length == 0)
            return 0;
        if (chars.length == 1)
            return 1;
        int i = 0, j = 1, start = i, end = j;
        while (j < s.toCharArray().length) {
            for (int k = j -1; k >= i; k--) {
                if ((chars[j] ^ chars[k]) == 0) {
                    if ((j - i) > (end - start)) {//maximizing substring
                        start = i; end = j;
                    }
                    i = k + 1;
                    break;
                }
            }
            j++;
        }
        return Integer.max(j - i, end - start);
    }


    public static int lengthOfLongestSubstringWithMap(String s) {
        char[] chars = s.toCharArray();
        int i = 0, j = 0, start = i, end = j;
        HashMap<Character, Integer> lastPositions = new HashMap<>();
        while (j < s.toCharArray().length) {
            Integer k = lastPositions.get(chars[j]);
            if (k != null && k >= i) {
                if ((j - i) > (end - start)) {//maximizing substring
                    start = i; end = j;
                }
                i = ++k;
            }
            lastPositions.put(chars[j], j);
            j++;
        }


        return Integer.max(j - i, end - start);
    }
}
