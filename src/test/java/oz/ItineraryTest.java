package oz;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/*
Given pairs of start-destination flights return itinerary. Assume the pairs form a consistent chain.
 */
public class ItineraryTest {

    String[][] flights = new String[][]{
            {"SFO", "NYC"},
            {"YYZ", "SFO"},
            {"YUL", "YYZ"},
            {"HKO", "YUL"}
    };


    @Test
    public void test() {

        Set<String> ends = Arrays.stream(flights).map(fl -> fl[1]).collect(Collectors.toSet());
        Set<String> starts = Arrays.stream(flights)
                .map(fl -> fl[0])
                .filter(s -> !ends.contains(s))
                .collect(Collectors.toSet());

        assertEquals(1, starts.size());
        String start = starts.iterator().next();
        assertEquals("HKO", start);

        Map<String, String> starts2ends = Arrays.stream(flights).collect(
                HashMap::new,
                (map, fl) -> map.put(fl[0], fl[1]),
                HashMap::putAll);

        LinkedList<String> itinerary = new LinkedList<>();
        while ( start != null ) {
            itinerary.add(start);
            start = starts2ends.get(start);
        }
        assertEquals(Arrays.asList("HKO", "YUL", "YYZ", "SFO", "NYC"), itinerary);
    }
}
