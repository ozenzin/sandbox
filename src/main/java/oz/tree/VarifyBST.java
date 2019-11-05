package oz.tree;

import java.util.Comparator;

public class VarifyBST {

    private static Double watermark = Double.NEGATIVE_INFINITY;

    public static void main(String[] args) {
//        Trees.inOrder(Trees.bst);
//        System.out.println();
//
//        System.out.printf("BST with watermark: %s %n",
//                verifyBST(Trees.bst));
//
//        System.out.printf("Recursive BST: %s %n",
//                verifyBSTRecursive(Trees.bst, Double::compareTo, Double.POSITIVE_INFINITY));
        Trees.TreeNode<Integer> bst = Trees.bstOfInt;
        Trees.inOrder(bst);
        System.out.printf("%nis wellformed: %s%n", verifyBSThelper(bst, Integer.MIN_VALUE, Integer.MAX_VALUE));

        bst.left.right.data = 10;
        Trees.inOrder(bst);
        System.out.printf("%nis wellformed: %s%n", verifyBSThelper(bst, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    private static boolean verifyBSThelper(Trees.TreeNode<Integer> node, int minValue, int maxValue) {
        if (node == null)
            return true;
        if (node.data < minValue || node.data > maxValue)
            return false;
        return verifyBSThelper(node.left, minValue, node.data) && verifyBSThelper(node.right, node.data, maxValue);
    }

    private static boolean verifyBSTdetailed(Trees.TreeNode<Double> bst) {
        if (bst == null) return true;
        boolean result = verifyBSTdetailed(bst.left);
        if (Double.compare(bst.data, watermark) < 0) {
            System.out.printf("Wrong: %s > %s !%n", watermark, bst.data);
            return false;
        } else {
            watermark = bst.data;
            System.out.printf("%s%n", bst.data);
        }
        result &= verifyBSTdetailed(bst.right);
        return result;
    }

    private static boolean verifyBST(Trees.TreeNode<Double> bst) {
        if (bst == null) return true;
        return verifyBST(bst.left) &&
         (Double.compare(bst.data, watermark) < 0) ?
             false : ((watermark = bst.data) > Double.NEGATIVE_INFINITY)
        && verifyBST(bst.right);
    }

    /**
     * Wrong. Because it is localized rather than global (or discreet rather than continuous), i.e. it
     * only verifies on each node level, but does not carry comparison over between tree levels or siblings.
     * @param bst
     * @param comparator
     * @param compareTo
     * @return
     */
    private static boolean verifyBSTRecursive(Trees.TreeNode<Double> bst, Comparator<Double> comparator, Double compareTo) {
        if (bst == null) return true;
        return verifyBSTRecursive(bst.left, comparator, bst.data) &&
         comparator.compare(bst.data, compareTo) < 0
        && verifyBSTRecursive(bst.right, comparator.reversed(), bst.data);
    }
}
