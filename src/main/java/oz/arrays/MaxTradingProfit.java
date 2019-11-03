package oz.arrays;

import java.util.Arrays;

public class MaxTradingProfit {

    public static void main(String[] args) {
        int[] stocks = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();

        int minPrice = Integer.MAX_VALUE, maxProfit = 0;

        for (int i = 0; i < stocks.length; i++) {
            minPrice = Integer.min(stocks[i], minPrice);
            maxProfit = Integer.max(maxProfit, stocks[i] - minPrice);
        }

        System.out.printf("Buying at %d results in max profit %d%n", minPrice, maxProfit);
    }
}
