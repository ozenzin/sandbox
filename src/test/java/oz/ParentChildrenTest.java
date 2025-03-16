package oz;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Given a collection of pairs [parent, child] return:
 * - set of people who don't have parents;
 * - set of people who have exactly 1 parent;
 */
public class ParentChildrenTest {
    

    /*
    An example input:
    2,3 2,4 7,5 9,4
    Output:

    For relations [2,3, 2,4, 7,5, 9,4]
    0-parent people: [2, 7, 9]
    1-parent people: [3, 5]

     */

    String input = "2,3 2,4 7,5 9,4";
    String[] relations = input.split(" ");
    
    @Test
    public void solution() {
        assertArrayEquals(new int[]{3, 4, 5}, children(relations));
        assertArrayEquals(new int[]{2, 7, 9}, zeroParent(relations));
    }
    
    private int[] zeroParent(String[] relations) {
        int[] children = children(relations);
        return Arrays.stream(relations).flatMap(r -> Arrays.stream(r.split(",")))
                .mapToInt(Integer::parseInt).distinct().filter(p -> Arrays.stream(children).noneMatch(c -> c == p)).toArray();
    }
    
    private int[] children(String[] relations) {
        return Arrays.stream(relations).map(r -> r.split(",")[1]).mapToInt(Integer::parseInt).distinct().toArray();
    }
}
