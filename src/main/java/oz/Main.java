package oz;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {

    static class DataPoint {
        private long id;
        private long timestamp;
        public double response;
        private String clientId;

        DataPoint(double response) {
            this.response = response;
        }

        public double getResponse() {
            return response;
        }
    }

    static List<DataPoint> incomingData = new ArrayList<>(1000);

    static LinkedHashMap<Long, DataPoint> window = new LinkedHashMap<Long, DataPoint>(1000) {

        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, DataPoint> eldest) {
            return eldest.getValue().timestamp < System.currentTimeMillis() - 36000;
        }
    };

    public static void main(String[] args) {
//        int[] nums = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();
//
//        System.out.printf("Missing %s amongst %s%n", findMissing(nums), Arrays.asList(args));
//
//        List<String> blocks = Arrays.asList("red", "2", "blue", "4");
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int n = 1000; n-- > 0;) {
            random.doubles(1000, 5, 300).forEach(d ->
                    incomingData.add(new DataPoint(d)));
            if (Double.compare(collectorsAveragingDouble(incomingData), mapToDoubleAverage(incomingData)) != 0) {
                System.out.printf("%nCollectors average: %f%n", collectorsAveragingDouble(incomingData));
                System.out.printf("%nMapping average: %f%n", mapToDoubleAverage(incomingData));
                break;
            }
        }

        window.values().stream().mapToDouble(DataPoint::getResponse).average();
    }

    private static double collectorsAveragingDouble(List<DataPoint> incomingData) {
        return incomingData.stream().collect(Collectors.averagingDouble(DataPoint::getResponse));
    }

    private static double mapToDoubleAverage(List<DataPoint> incomingData) {
        return incomingData.stream().mapToDouble(DataPoint::getResponse).average().orElse(0);
    }
/*
    public static void main(String... args) {
        int i = args.length == 2 ?
                Integer.valueOf(args[0], Integer.valueOf(args[1])) :
                args.length == 1 ? Integer.valueOf(args[0]) : 0 ;
        System.out.printf("i = %d : ", i);
        System.out.println(toBinaryString(i));

        int n = 1;
        for (int d = 31; d > 0; d--)
            checkXor((n << d) -1);

        checkXor(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
        checkXor(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
        checkXor(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
        checkXor(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
        checkXor(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
    }*/

    private static String toBinaryString(int i) {
        StringBuilder sb = new StringBuilder();
        for (int k = 32; k-- > 0; i >>>= 1) {
            if ((k +1) % 4 == 0) sb.append(" ");
            sb.append(i & 1);
        }
        return sb.reverse().toString();
    }

    private static void checkXor(int n) {
        System.out.printf("XOR'ed %d = ", n);
        int k = n;
        while (n-- > 0) {
            k ^= n;
        }
        System.out.printf("%s%n", toBinaryString(k));
    }

    /**
     * find a missing number k | k > 0 & k <= n+1 in int[n] where all numbers 1...n+1
     * @param nums
     * @return
     */
    private static int findMissing(int[] nums) {
        int missing = nums.length + 1, i = nums.length;
        do {
            missing ^= i ^ nums[--i];
        } while (i > 0 );
        return missing;
    }
}
