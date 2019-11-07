package oz;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Given a collection of pairs [parent, child] return:
 * - set of people who don't have parents;
 * - set of people who have exactly 1 parent;
 */
public class ParentChildren {

    public static void main(String[] args) {
        int[][] relations =
        Stream.of(args).map(s -> {
            String[] pair = s.split(",");
            return Stream.of(pair).mapToInt(Integer::parseInt).toArray();
                }
        ).toArray(int[][]::new);

        System.out.printf("%nFor relations %s%n0-parent people: %s%n1-parent people: %s%n",
                Arrays.asList(args), sortThemOut(relations).get(0), sortThemOut(relations).get(1));
    }

    private static List<List<Integer>> sortThemOut(int[][] relations) {
        Map<Integer, List<Integer>> p2c = new HashMap<>();
        Map<Integer, List<Integer>> c2p = new HashMap<>();
        for (int[] pair : relations) {
            p2c.computeIfAbsent(pair[0], k-> new ArrayList<>(pair[1]));
            p2c.computeIfPresent(pair[0], (k, l)-> {l.add(pair[1]); return l;});

            c2p.computeIfAbsent(pair[1], k-> new ArrayList<>(pair[0]));
            c2p.computeIfPresent(pair[1], (k, l)-> {l.add(pair[0]); return l;});
        }
        List<Integer> zeroParent = new ArrayList<>(p2c.keySet());
        zeroParent.removeAll(p2c.values().stream().flatMap(List::stream).collect(Collectors.toList()));
        List<Integer> oneParent = c2p.entrySet().stream().filter(e -> e.getValue().size() ==1).map(Map.Entry::getKey).collect(Collectors.toList());
        return Arrays.asList(zeroParent, oneParent);
    }
}
