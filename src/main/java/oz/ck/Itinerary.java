package oz.ck;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Itinerary {

    public static void main(String[] args) {
        IntStream.range(1, 51).forEach(i -> System.out.printf("%s ---> %s%n", i, printCreditKarma(i)));

        List<List<String>> flights = Arrays.asList(
                Arrays.asList("SFO", "DAL"),
                Arrays.asList("LAX", "CTO"),
                Arrays.asList("DAL", "LAX"),
                Arrays.asList("CTO", "NYC")
        );

        System.out.printf("Itinerary %s requires flights %s", itinerary(flights), flights);
    }

    private static String printCreditKarma(int n) {
        int rem = n % 15;
        if (rem == 0)
            return "CreditKarma";
        else if (rem % 3 == 0)
            return "Credit";
        else if (rem % 5 == 0)
            return "Karma";

        return String.valueOf(n);
    }

    /*
    Given pairs of start-destination flights return itinerary. Assume the pairs form a consistent chain.
     */
    private static List<String> itinerary(List<List<String>> flights) {
        Map<String, String> starts2ends = flights.stream().collect(toMap(fl -> fl.get(0), fl -> fl.get(1)));

        List<String> itin = new LinkedList<>(starts2ends.keySet());
        itin.removeAll(starts2ends.values());
        for (int i = 0; i < flights.size(); i++) {
            itin.add(starts2ends.get(itin.get(i)));
        }
        return itin;
    }
}
