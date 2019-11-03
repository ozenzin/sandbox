package oz.uber;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * Calculate all combinations for game of 24:
 * Given four numbers 1..9 in any order (repetitions allowed) and using four arithmetic ops (+, -, *, and /) augmented with parentheses,
 * find all combinations resulting in number 24.
 * For instance, given 5, 3, 3, 6 following combinations match:
 * <pre>
 *     5 * 3 + 3 + 6 = 24
 *     5 * 6 - 3 - 3 = 24
 *    (5 + 3)*(6 - 3)= 24
 *     ...
 * </pre>
 */
public class TwentyFour {

    private static Integer TWENTY_FOUR = 24;

    private static char[][] STENCILS = new char[448][15];

    static {
        seedSTENCILS();
    }

    private static ScriptEngine engine;

    static {
        ScriptEngineManager mgr = new ScriptEngineManager();
        engine = mgr.getEngineByName("JavaScript");
    }

    private static int counter;

    public static void main(String[] args) {
//        twentyFourAll();
//        twentyFourOf(args[0].toCharArray());
        heapPermutation(args[0].toCharArray(), args[0].length());
//        counter = 0;
//        picking4timesAndExcludingPrevious(args[0].toCharArray());
//        evalSTANCILS(args[0].toCharArray());
    }

    private static void heapPermutation(char[] numbers, int toShuffle) {
        if (toShuffle < 2)//nothing to shuffle
            evalSTANCILS(numbers);
        else {
            for (int i = 0; i < toShuffle -1; i++) {
                heapPermutation(numbers, toShuffle -1);
                if ((toShuffle &1) == 0)
                    swap(numbers, i, toShuffle -1);
                else
                    swap(numbers, 0, toShuffle -1);
            }
            heapPermutation(numbers, toShuffle -1);
        }
    }

    private static void picking4timesAndExcludingPrevious(char[] numbers) {
        for (int a = 0; a < 4; a++)
            for (int b = 0; b < 4; b++) if (b != a)
                    for (int c = 0; c < 4; c++) if (c != a && c != b)
                            for (int d = 0; d < 4; d++) if (d != a && d != b && d != c)
                                    evalSTANCILS(numbers[a], numbers[b], numbers[c], numbers[d]);

    }

    /*
    Allows each element to take every seat.
    Seems most optimal to me since it's optimized to not swap equal elements
    */
    private static void swappingDistinct(char[] numbers) {
        evalSTANCILS(numbers);
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] != numbers[j]) {
                    char tmp = numbers[i]; numbers[i] = numbers[j]; numbers[j] = tmp;
                    evalSTANCILS(numbers);
                    tmp = numbers[i]; numbers[i] = numbers[j]; numbers[j] = tmp;//swap back
                }
            }
        }
    }

    private static void twentyFourAll() {
        for (char a = '1'; a <= '9'; a++) {
            for (char b = '1'; b <= '9'; b++) {
                for (char c = '1'; c <= '9'; c++) {
                    for (char d = '1'; d <= '9'; d++) {
                        evalSTANCILS(a, b, c, d);
                    }
                }
            }
        }
    }

    private static void evalSTANCILS(char... numbers) {
        for (char[] stencil : STENCILS) {
            stencil[1] = numbers[0];stencil[5] = numbers[1];stencil[9] = numbers[2];stencil[13] = numbers[3];//plug the numbers
            String expression = new String(stencil);
            try {
                if (TWENTY_FOUR.equals(engine.eval(expression))) {
                    System.out.printf("%n%3d. %s = 24", ++counter, expression);
                }
            } catch (ScriptException se) {
                System.out.printf("%s is bad!", expression);
            }
        }
    }

    private static void seedSTENCILS() {
        char[] ops = {'+', '-', '*', '/'};
        int stencilIndex = 0;
        for (char op1 : ops) {
            for (char op2 : ops) {
                for (char op3 : ops) {
                    stencilIndex = permutateParentheses(stencilIndex, op1, op2, op3);
                }
            }
        }
    }

    private static int permutateParentheses(int stencilIndex, char... op) {
        STENCILS[stencilIndex++] = new char[]{' ', 0, ' ', op[0], ' ', 0, ' ', op[1], ' ', 0, ' ', op[2], ' ', 0, ' '};
        STENCILS[stencilIndex++] = new char[]{'(', 0, ' ', op[0], ' ', 0, ')', op[1], ' ', 0, ' ', op[2], ' ', 0, ' '};
        STENCILS[stencilIndex++] = new char[]{' ', 0, ' ', op[0], ' ', 0, ' ', op[1], '(', 0, ' ', op[2], ' ', 0, ')'};
        STENCILS[stencilIndex++] = new char[]{'(', 0, ' ', op[0], ' ', 0, ')', op[1], '(', 0, ' ', op[2], ' ', 0, ')'};
        STENCILS[stencilIndex++] = new char[]{'(', 0, ' ', op[0], ' ', 0, ' ', op[1], ' ', 0, ')', op[2], ' ', 0, ' '};
        STENCILS[stencilIndex++] = new char[]{' ', 0, ' ', op[0], '(', 0, ' ', op[1], ' ', 0, ' ', op[2], ' ', 0, ')'};
        STENCILS[stencilIndex++] = new char[]{' ', 0, ' ', op[0], '(', 0, ' ', op[1], ' ', 0, ')', op[2], ' ', 0, ' '};
        return stencilIndex;
    }

    private static void swap(char[] chars, int from, int to) {
        if (chars[from] == chars[to]) return;
        char tmp = chars[from];chars[from] = chars[to];chars[to] = tmp;
    }
}
