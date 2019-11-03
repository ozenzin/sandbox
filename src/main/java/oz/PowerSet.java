package oz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PowerSet {

    public static void main(String[] args) {
        List<Integer> inputSet = Arrays.stream(args).map(Integer::new).collect(Collectors.toList());
        List<List<Integer>> superSet = new ArrayList<>(1 << inputSet.size());
        directedRecursiveSuperset(inputSet, 0, new LinkedList<>(), superSet);

        System.out.printf(superSet.toString());
    }

    private static void directedRecursiveSuperset(List<Integer> inputSet, int i, LinkedList<Integer> currentSet, List<List<Integer>> superSet) {

        if (i == inputSet.size()) {
            superSet.add(new ArrayList<>(currentSet) );
            return;
        }

        currentSet.add(inputSet.get(i));
        directedRecursiveSuperset(inputSet, i + 1, currentSet, superSet);
        currentSet.remove(currentSet.size() -1);
        directedRecursiveSuperset(inputSet, i + 1, currentSet, superSet);
    }

}
