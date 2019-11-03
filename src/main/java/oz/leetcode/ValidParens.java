package oz.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <pre>
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 *
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Note that an empty string is also considered valid.
 *
 * Example 1:
 *
 * Input: "()"
 * Output: true
 * Example 2:
 *
 * Input: "()[]{}"
 * Output: true
 * Example 3:
 *
 * Input: "(]"
 * Output: false
 * Example 4:
 *
 * Input: "([)]"
 * Output: false
 * Example 5:
 *
 * Input: "{[]}"
 * Output: true
 * </pre>
 */
public class ValidParens {

    public static void main(String[] args) {
        String s = args[0];

        System.out.printf("%s is %s valid!", s, isValid(s) ? "" : "NOT");
    }

    private static boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        final Character C = '(', K = '{', L = '[';
        char[] chars = s.toCharArray();
        for (int i = 0, n = chars.length; i < n; i++) {
            char c = chars[i];
            switch (c) {
                case '(':
                case '{':
                case '[':
                    stack.push(c);
                    break;
                case ')':
                    if (!C.equals(stack.pollFirst()))
                        return false;
                    break;
                case '}':
                    if (!K.equals(stack.pollFirst()))
                        return false;
                    break;
                case ']':
                    if (!L.equals(stack.pollFirst()))
                        return false;
                    break;
                default:
                    return false;
            }
        }
        return stack.isEmpty();
    }
}
