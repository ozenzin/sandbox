package oz.leetcode;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * <pre>
 *
 Given an array of strings, group anagrams together.

 Example:

 Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 Output:
 [
 ["ate","eat","tea"],
 ["nat","tan"],
 ["bat"]
 ]
 Note:

 All inputs will be in lowercase.
 The order of your output does not matter.
 * </pre>
 */
public class GroupAnagrams {

    public static void main(String[] args) {
        System.out.printf("Grouped anagrams:%n%s%n", groupAnagrams(args));
    }

    private static List<List<String>> groupAnagrams(String[] args) {
        return new ArrayList<>(Arrays.stream(args).collect(Collectors.groupingBy(s -> s.chars().sorted().mapToObj(String::valueOf).collect(joining()))).values());
    }
}
