package oz.linkedlists;

public class ListNode<T> {
    T data;
    ListNode<T> next;

    ListNode(T data) {
        this.data = data;
    }

    ListNode(T data, ListNode<T> next) {
        this.data = data;
        this.next = next;
    }

    ListNode<T> next(ListNode<T> next) {
        this.next = next;
        return next;
    }

    @Override
    public String toString() {
        return "LN" + data + " -> " + next;
    }
}
