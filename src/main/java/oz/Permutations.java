package oz;

public class Permutations {

    private static int counter;

    public static void main(String[] args) {
//        permutateFour(args[0].toCharArray());
        permutateRecursively(args[0].toCharArray(), args[0].length());
    }

    private static void permutateMyWay(char[] numbers) {
        System.out.printf("%n%s%n", new String(numbers));
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i+1; j < numbers.length; j++) {
                if (numbers[i] != numbers[j]) {
                    swap(numbers, i, j);
                    System.out.printf("%n%d. %s", ++counter, new String(numbers));
                    swap(numbers, i, j);//put it back
                }
            }
        }
    }

    private static void permutateFour(char[] numbers) {
        for (int a = 0; a < numbers.length; a++)
            for (int b = 0; b < numbers.length; b++) if (b != a)
                for (int c = 0; c < numbers.length; c++) if (c != a && c != b)
                    for (int d = 0; d < numbers.length; d++) if (d != a && d != b && d != c)
                        System.out.printf("%n%2d. %s", ++counter,
                                new String(new char[] {numbers[a], numbers[b], numbers[c], numbers[d]}));
    }

    private static void permutateRecursively(char[] numbers, int subset) {
        if (subset == 1)
            System.out.printf("%n%2d. %s", ++counter, new String(numbers));
        else {
            for (int i = 0; i < subset - 1; i++) {
                permutateRecursively(numbers, subset - 1);
                if ((subset & 1) == 0)
                    swap(numbers, i, subset -1);
                else
                    swap(numbers, 0, subset -1);
            }
            permutateRecursively(numbers, subset -1);
        }
    }

    public static void swap(char[] chars, int from, int to) {
        char tmp = chars[from]; chars[from] = chars[to]; chars[to] = tmp;
    }
}
