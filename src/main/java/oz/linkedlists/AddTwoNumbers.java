package oz.linkedlists;

public class AddTwoNumbers {

    /**
     *
     * @param args space separated two numbers
     */
    public static void main(String[] args) {
        //init lists
        ListNode<Integer> l1 = new ListNode<>(-1), l2 = new ListNode<>(-1), cur = l1;
        for (char ch : args[0].toCharArray()) {
            cur.next = new ListNode<>(Character.getNumericValue(ch));
            cur = cur.next;
        }

        l1 = l1.next;

        cur = l2;
        for (char ch : args[1].toCharArray()) {
            cur.next = new ListNode<>(Character.getNumericValue(ch));
            cur = cur.next;
        }

        l2 = l2.next;
        System.out.printf("Given two numbers as linked lists: %s and %s ", l1, l2);

        ListNode<Integer> result = addTwoNumbers(l1, l2);

        System.out.printf(" the sum is:%n%s%n", result);
    }

    private static ListNode<Integer> addTwoNumbers(ListNode<Integer> l1, ListNode<Integer> l2) {
        if (l1 == null && l2 == null)
            return null;

        if (l1 == null)
            return l2;

        if (l2 == null)
            return l1;

        ListNode<Integer> result = new ListNode<>(l1.data + l2.data);
        result.next = addTwoNumbers(l1.next, l2.next);
        return normalize(result);
    }

    private static ListNode<Integer> normalize(ListNode<Integer> res) {
        if (res == null)
            return null;

        if (res.data > 9) {
            res.data -= 10;
            if (res.next != null) {
                res.next.data++;
                normalize(res.next);
            } else
                res.next = new ListNode<>(1);
        }
        return res;
    }

}
