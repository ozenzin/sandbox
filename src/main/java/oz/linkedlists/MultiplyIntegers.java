package oz.linkedlists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given two integers in form of arrays calculate their product
 * Example: [1,9,3,7,0,7,7,2,1] x [-7,6,1,8,3,8,2,5,7,2,8,7] = [-1,4,7,5,7,3,9,5,2,5,8,9,6,7,6,4,1,2,9,2,7], because
 * 193707721 x -761838257287 = -147573952589676412927
 */
public class MultiplyIntegers {

    public static void main(String[] args) {
        int aNeg = args[0].startsWith("-") ? -1 : 1, bNeg = args[1].startsWith("-") ? -1 : 1, prodSign = aNeg * bNeg;
        System.out.printf("%s x %s = ", args[0], args[1]);
        if (aNeg < 0)
            args[0] = args[0].substring(1);
        if (bNeg < 0)
            args[1] = args[1].substring(1);
        List<Integer> a = args[0].chars().mapToObj(c -> c - '0').collect(Collectors.toList());
        List<Integer> b = args[1].chars().mapToObj(c -> c - '0').collect(Collectors.toList());

        System.out.printf("%s%n", productOf(a, b, prodSign));
    }

    private static List<Integer> productOf(List<Integer> a, List<Integer> b, int sign) {
        List<Integer> result = new LinkedList<>(Collections.nCopies(a.size() + b.size(), 0));
        for (int i = a.size(); --i >= 0;)
            for (int j = b.size(); --j >= 0; ) {
                result.set(i+j+1, result.get(i+j+1) + a.get(i) * b.get(j));
                result.set(i+j, result.get(i+j) + result.get(i+j+1) / 10);
                result.set(i+j+1, result.get(i+j+1) % 10);
            }

        //remove leading 0's
        int leading0s = 0;
        while (leading0s < result.size() && result.get(leading0s) == 0)
            leading0s++;

        result = result.subList(leading0s, result.size());
        if (result.isEmpty())
            return Collections.singletonList(0);

        result.set(0, result.get(0) * sign);
        return result;
    }
}
