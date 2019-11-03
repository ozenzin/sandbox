package oz;

import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * <pre>
 *     Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.
 *
 * For example, "great acting skills" and "fine drama talent" are similar, if the similar word pairs are pairs = [["great", "fine"], ["acting","drama"], ["skills","talent"]].
 *
 * Note that the similarity relation is not transitive. For example, if "great" and "fine" are similar, and "fine" and "good" are similar, "great" and "good" are not necessarily similar.
 *
 * However, similarity is symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.
 *
 * Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.
 *
 * Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].
 *
 * Note:
 *
 * The length of words1 and words2 will not exceed 1000.
 * The length of pairs will not exceed 2000.
 * The length of each pairs[i] will be 2.
 * The length of each words[i] and pairs[i][j] will be in the range [1, 20]
 * </pre>
 */
public class SentenceSimilarity {

    public static void main(String[] args) {
        String[] sentance1 = {"great", "acting", "skills"};
        String[] sentance2 = {"fine", "drama", "talent"};
        List<List<String>> pairs = Arrays.asList(Arrays.asList("great", "fine"), Arrays.asList("acting","drama"), Arrays.asList("skills","talent"));
        System.out.printf("Strings %s and %s are similar: %s%n", Arrays.asList(sentance1), Arrays.asList(sentance2), areSentencesSimilar(sentance1, sentance2, pairs));
    }

    public static boolean areSentencesSimilar(String[] words1, String[] words2, List<List<String>> pairs) {
        if (words1 == null && words2 == null)
            return true;

        if ((words1 == null ^ words2 == null) || words1.length != words2.length)
            return false;

        for (int i = 0; i < words1.length; i++) {
            String w1 = words1[i], w2 = words2[i];
            if (w1.equals(w2))
                continue;
            if (!pairs.contains(Arrays.asList(w1, w2)) && !pairs.contains(Arrays.asList(w2, w1)))
                return false;
        }
        return true;
    }


    public static boolean areSentencesSimilarBad(String[] words1, String[] words2, List<List<String>> pairs) {
        if (words1 == null && words2 == null)
            return true;

        if ((words1 == null ^ words2 == null) || words1.length != words2.length)
            return false;

        Map<String, Set<String>> left = pairsAsMap(pairs, 0);
        Map<String, Set<String>> right = pairsAsMap(pairs, 1);
        for (int i = 0; i < words1.length; i++) {
            String w1 = words1[i], w2 = words2[i];
            if (w1.equals(w2))
                continue;
            Set<String> leftSet = left.get(w1);
            Set<String> rightSet = right.get(w1);
            if ((leftSet == null || !leftSet.contains(w2)) && (rightSet == null || !rightSet.contains(w2)))
                return false;
        }
        return true;
    }

    private static Map<String, Set<String>> pairsAsMap(List<List<String>> pairs, final int key) {
        return pairs.stream().collect(groupingBy(pair -> pair.get(key), mapping(pair -> pair.get(key^1), toSet())));

//        Map<String, Set<String>> result = new HashMap<>();
//        for (List<String> pair : pairs) {
//            result.computeIfAbsent(pair.get(key), w -> new HashSet<>(Arrays.asList(w, w, w, pair.get(key ^ 1))));
//            result.computeIfPresent(pair.get(key), (w, set) -> {
//                set.add(pair.get(key ^ 1)); return set;
//            });
//        }
//        return result;
    }
}
