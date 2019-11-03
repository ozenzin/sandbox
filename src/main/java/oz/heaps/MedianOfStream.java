package oz.heaps;

import java.util.*;
import java.util.stream.Stream;

/**
 * Given a potentially indefinite stream of numbers produce median;
 */
public class MedianOfStream {

    public static void main(String[] args) {
        System.out.printf("%s%nmedians:%n  %s%n", Arrays.asList(args), medians(Stream.of(args).map(Integer::valueOf)));
    }

    private static List<Double> medians(Stream<Integer> integerStream) {
        List<Double> result = new LinkedList<>();
        PriorityQueue<Integer> max = new PriorityQueue<>(30, Comparator.reverseOrder());//items to the left from middle
        PriorityQueue<Integer> min = new PriorityQueue<>(30);//items to the right from middle
        Iterator<Integer> iter = integerStream.iterator();
        while (iter.hasNext()) {
            max.add(iter.next());//ingesting number
            min.add(max.remove());//balancing middle

            //make sure max's size is at least min's size
            if (min.size() > max.size())
                max.add(min.remove());
            result.add(min.size() == max.size() ? (min.peek() + max.peek()) / 2.0 : max.peek().doubleValue());
        }
        return result;
    }
}
