package oz.linkedlists;

import java.util.Arrays;

/**
 * Reverse sublist. Numbering of nodes begins with 1 (not 0!)
 */
public class ReverseSublist {

    public static void main(String[] args) {
        int[] fromTo = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();
        ListNode<Integer> given = new ListNode<>(1);
        given.next(new ListNode<>(2)).next(new ListNode<>(3)).next(new ListNode<>(4)).next(new ListNode<>(5)).
        next( new ListNode<>(6)).next( new ListNode<>(7)).next( new ListNode<>(8)).next( new ListNode<>(9)).
        next(new ListNode<>(10)).next( new ListNode<>(11)).next( new ListNode<>(12)).next( new ListNode<>(13)).next( new ListNode<>(14, null));

        System.out.printf("%s reversed from %d to %d inclusive:%n", given, fromTo[0], fromTo[1]);
        System.out.printf("%s%n", reverseSublist(given, fromTo[0], fromTo[1]));
    }
    
    static ListNode reverseSublist(ListNode<Integer> given, int from, int to) {
        ListNode<Integer> result = new ListNode<>(Integer.MIN_VALUE, given);
        //startPointer will always point to node before start of sublist
        ListNode<Integer> startPointer = result;
        for (int i = 1; startPointer != null && i++ < from; ) {
            startPointer = startPointer.next;
        }

        if (startPointer == null)//no sublist
            return result.next;

        //endPointer will always point to the node right before the last node in sublist
        ListNode<Integer> endPointer = startPointer.next;
        if (endPointer == null)//only 1 node in sublist
            return result.next;

        ListNode<Integer> end = endPointer.next;

        while (from++ < to && end != null ) {
            ListNode<Integer> tmp = startPointer.next;
            endPointer.next = end.next;//removing end
            startPointer.next = end;//inserting end as start
            end.next = tmp;//completing insertion
            end = endPointer.next;//next iteration
        }
                
                
        return result.next;
    }
}
