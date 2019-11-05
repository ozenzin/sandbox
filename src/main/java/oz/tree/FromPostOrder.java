package oz.tree;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class FromPostOrder {

    private static class Node {
        Integer key;
        Node left, right;

        Node(Integer key) {this.key = key;}
    }

    //args = 1 3 2 5 7 6 4 9 11 10 13 15 14 12 8
    public static void main(String[] args) {
        int[] postOrder = Arrays.stream(args).mapToInt(Integer::valueOf).toArray();

        Node root = fromPostOrder(postOrder, 0, postOrder.length -1);
        inOrderPrint(root);
        AtomicInteger indx = new AtomicInteger(postOrder.length -1);
        root = fromPostOrderRecursively(postOrder, indx, Integer.MIN_VALUE, Integer.MAX_VALUE);
        inOrderPrint(root);
        System.out.printf("%nFrom postorder %s%n", Arrays.asList(args));
    }

    private static Node fromPostOrderRecursively(int[] postOrder, AtomicInteger indx, Integer floor, Integer ceil) {
        if (postOrder == null)
            return null;
        int i = indx.get();
        if (i < 0 ||
                postOrder[i] < floor || postOrder[i] > ceil)
            return null;

        Integer key = postOrder[indx.getAndDecrement()];
        Node node = new Node(key);
        node.right = fromPostOrderRecursively(postOrder, indx, key, ceil);
        node.left = fromPostOrderRecursively(postOrder, indx, floor, key);
        return node;
    }

    private static Node fromPostOrder(int[] postOrder, int leftMost, int rootIndx) {
        if (postOrder == null || leftMost > rootIndx)
            return null;

        Node root = new Node(postOrder[rootIndx]);
        int nextRootIndx = rootIndx;
        //looking for left subtree root (left child of current root)
        while (nextRootIndx > leftMost &&
                postOrder[nextRootIndx -1] > postOrder[rootIndx])//first right subtree (greater than root), than - left subtree
            nextRootIndx--;

        root.right = fromPostOrder(postOrder, nextRootIndx, rootIndx -1);
        root.left = fromPostOrder(postOrder, leftMost, nextRootIndx -1);
        return root;
    }

    private static void inOrderPrint(Node node) {
        if (node == null)
            return;
        inOrderPrint(node.left);
        System.out.printf("%s ", node.key);
        inOrderPrint(node.right);
    }

}
