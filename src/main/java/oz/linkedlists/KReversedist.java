package oz.linkedlists;

/**
 * Takes linked list and non-negative K and reverses the list K nodes at a time. Remaining N % K nodes stay unchanged
 * (N - total number of nodes).
 */
public class KReversedist {

    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);

        ListNode<Integer> given = new ListNode<>(1);
        given.next(new ListNode<>(2)).next(new ListNode<>(3)).next(new ListNode<>(4)).next(new ListNode<>(5)).
                next( new ListNode<>(6)).next( new ListNode<>(7)).next( new ListNode<>(8)).next( new ListNode<>(9)).
                next(new ListNode<>(10)).next( new ListNode<>(11)).next( new ListNode<>(12)).next( new ListNode<>(13)).next( new ListNode<>(14, null));

        System.out.printf("%s with every %d reversed:%n", given, k);
        System.out.printf("%s%n", reversingEveryK(given, k));
    }

    private static ListNode<Integer> reversingEveryK(ListNode<Integer> given, int k) {
        if (given == null || given.next == null || k <= 0)
            return given;

        ListNode<Integer> result = new ListNode<>(Integer.MIN_VALUE, given);
        ListNode<Integer> startPointer = result;
        while (startPointer != null && checkLength(startPointer, k)) {
            ListNode<Integer> endPointer = startPointer.next;//at least 2 nodes in sublist, so endPointer != null && endPointer.next != null
            ListNode<Integer> end = endPointer.next;
            for (int i = k; --i > 0 && end != null; end = endPointer.next) {
                endPointer.next = end.next;//removing end
                ListNode<Integer> tmp = startPointer.next;
                startPointer.next = end;//re-inserting end
                end.next = tmp;//finalizing removal-insertion
            }
            startPointer = endPointer;
        }
        return result.next;
    }

    private static boolean checkLength(ListNode<Integer> startPointer, int k) {
        do {
            startPointer = startPointer.next;
        } while (k-- > 0 && startPointer != null);
        return k < 0;
    }


}
