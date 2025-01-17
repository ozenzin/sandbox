package oz.FB;

import java.util.*;

/**
 * Given blackbox {@code Container} which wraps an array of sorted integers and has only two following methods:
 * <pre>
 *     boolean isEmpty()
 *
 *     int get() //returns and removes next integer from the inner array picking it from either end randomly
 *
 * </pre>
 *
 * Reconstruct array
 *
 */
public class RandomMinMax {

    static class Container {
        int[] sorted;
        int left, right = 0;
        private Random randomizer;

        Container(int[] sorted) {
            this.sorted = sorted;
            randomizer = new Random();
        }

        int get() {
            if (randomizer.nextBoolean())
                return sorted[left++];
            else
                return sorted[sorted.length - 1 - right++];
        }

        boolean isEmpty() {
            return left + right >= sorted.length;
        }
    }

    public static void main(String[] args) {
        Container c = new Container(Arrays.stream(args).mapToInt(Integer::valueOf).toArray());

        System.out.printf("For array %s we've reconstructed %s%n", Arrays.asList(args), Arrays.asList(reconstruct(c)));
    }

    private static Integer[] reconstruct(Container c) {
        LinkedList<Integer> left = new LinkedList<>(), right = new LinkedList<>();

        if (!c.isEmpty())
            left.add(c.get());
        if (!c.isEmpty()) {
            Integer n = c.get();
            if (n < left.element()) {
                right.add(left.remove());
                left.add(n);
            } else
                right.add(n);
        }

        if (!c.isEmpty()) {//defining moment. Only 3 possibilities: 2 from the left, 2 from the right, or (1 from the left and 1 from the right)
            Integer n = c.get();
            if (n < left.element()) {//2 from right: max found! queue right is confirmed! we'd only do right.addLast (no right.addFirst anymore)
                right.addFirst(left.remove());
                left.add(n);//possibly min
                while (!c.isEmpty()) {
                    n = c.get();
                    if (left.getLast() < n && n < right.getFirst()) {//in between, chose any
                        left.addLast(n);
                    } else if (n < left.getFirst()) {//new candidate for min
                        left.addFirst(n);
                    } else if (n < left.getLast()) {
                        while (n < left.getLast()) {
                            right.addFirst(left.removeLast());
                        }
                        left.addLast(n);
                    } else if (n > right.getFirst()) {
                        while (n > right.getFirst()) {
                            left.addLast(right.removeFirst());
                        }
                        right.addFirst(n);
                    }
                }
            } else if (n > right.element()) {//2 from left: min found! queue left is confirmed! we'll use left.addFirst only (no left.addLast ever)
                left.addLast(right.remove());
                right.add(n);//possibly max
                while (!c.isEmpty()) {
                    n = c.get();
                    if (left.getLast() < n && n < right.getFirst()) {//in between, growing any (chose left)
                        left.addLast(n);
                    } else if (n > right.getLast()) {//new candidate for max
                        right.addLast(n);
                    } else if (n > right.getFirst()) {
                        while (n > right.getFirst()) {
                            left.addLast(right.removeFirst());
                        }
                        right.addFirst(n);
                    } else if (n < left.getLast()) {
                        while (n < left.getLast()) {
                            right.addFirst(left.removeLast());
                        }
                        left.addLast(n);
                    }
                }
            } else if (left.element() < n && n < right.element()) {//1 left, 1 right: both min and max are found! work left.Last and right.First ONLY
                left.addLast(n);
                while (!c.isEmpty()) {
                    n = c.get();
                    if (left.getLast() < n && n < right.getFirst()) {//in between, growing any (chose left)
                        left.addLast(n);
                    } else if (n > right.getFirst()) {
                        while (n > right.getFirst()) {
                            left.addLast(right.removeFirst());
                        }
                        right.addFirst(n);
                    } else if (n < left.getLast()) {
                        while (n < left.getLast()) {
                            right.addFirst(left.removeLast());
                        }
                        left.addLast(n);
                    }
                }
            }


        }

        right.addAll(0, left);
        return right.toArray(new Integer[] {});
    }

}
