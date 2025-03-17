package oz;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static oz.SentenceSimilarity.areSentencesSimilar;

/**
 * <pre>
 *     Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs,
 *     determine if two sentences are similar. For example, "great acting skills" and "fine drama talent" are similar,
 *     if the similar word pairs are pairs = [["great", "fine"], ["acting","drama"], ["skills","talent"]].
 *
 * Note that the similarity relation is not transitive. For example, if "great" and "fine" are similar, and "fine" and "good" are similar,
 * "great" and "good" are not necessarily similar. However, similarity is symmetric. For example, "great" and "fine" being similar
 * is the same as "fine" and "great" being similar. Also, a word is always similar with itself. For example,
 * the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.
 *
 * Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar
 * to words2 = ["doubleplus","good"].
 *
 * Note:
 *
 * The length of words1 and words2 will not exceed 1000.
 * The length of pairs will not exceed 2000.
 * The length of each pairs[i] will be 2.
 * The length of each words[i] and pairs[i][j] will be in the range [1, 20]
 * </pre>
 */
public class SentenceSimilarityTest {
    
    String[] words1 = {"great", "acting", "skills", "bastard"};
    String[] words2 = {"fine", "drama", "talent", "bastard"};
    
    String[][] pairs = {{"great", "fine"}, {"acting","drama"}, {"skills","talent"}};
    
    
    @Test
    public void testAreSentancesSimilar() {
        assertTrue(areSimilar(words1, words2, pairs));
    }
    
    private boolean areSimilar(String[] words1, String[] words2, String[][] pairs) {
        //first trivial cases
        boolean res = Arrays.equals(words1, words2);
        if (res || words1 == null || words2 == null || words1.length != words2.length)
            return res;
        else {
            Map<String, String> pairsMap = Arrays.stream(pairs).collect(
                    HashMap::new,
                    (m, p) -> m.put(p[0], p[1]),
                    HashMap::putAll
                    );
            int l = words1.length;
            for (int i = 0; i < l; i++) {
                if (!words1[i].equals(words2[i]) && 
                        !pairsMap.get(words1[i]).equals(words2[i]) && !pairsMap.get(words2[i]).equals(words1[i]))
                    return false;
            }
            return true;
        }
    }
    
    
}
