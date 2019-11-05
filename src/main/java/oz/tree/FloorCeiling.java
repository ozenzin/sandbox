package oz.tree;

public class FloorCeiling {

    private static Double floor;
    private static Double ceiling;

    public static void main(String[] args) {
        Double given = Double.valueOf(args[0]);
        Double floor = findFloor(Trees.bst, given);
        Double ceiling = findCeiling(Trees.bst, given);

        System.out.printf(" %s < %s < %s %n", floor, given, ceiling);
        System.out.printf(" %s < %s < %s %n", maxTreeNodeLessThanGiven(given, Trees.bst), given, minTreeNodeGreaterThanGiven(given, Trees.bst));
        Double[] beforeAtfer = beforeAfter(given, Trees.bst, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        System.out.printf(" %s < %s < %s%n", beforeAtfer[0], given, beforeAtfer[1]);
    }

    private static Double findFloor(Trees.TreeNode<Double> node, Double d) {
        if (node == null) {
            return floor;
        }

        if (Double.compare(d, node.data) > 0) {
            floor = node.data;
            return findFloor(node.right, d);
        } else {
            return findFloor(node.left, d);
        }
    }

    private static Double findCeiling(Trees.TreeNode<Double> node, Double d) {
        if (node == null) {
            return ceiling;
        }

        if (Double.compare(d, node.data) < 0) {
            ceiling = node.data;
            return findCeiling(node.left, d);
        } else {
            return findCeiling(node.right, d);
        }
    }

    private static Double minTreeNodeGreaterThanGiven(Double d, Trees.TreeNode<Double> tn) {
        if (tn == null)
            return Double.POSITIVE_INFINITY;

        else if (Double.compare(d, tn.data) > 0)
            return minTreeNodeGreaterThanGiven(d, tn.right);
        else
            return Math.min(tn.data, minTreeNodeGreaterThanGiven(d, tn.left));
    }

    private static Double maxTreeNodeLessThanGiven(Double d, Trees.TreeNode<Double> in) {
        if (in == null)
            return Double.NEGATIVE_INFINITY;
        else if (Double.compare(d, in.data) < 0)
            return maxTreeNodeLessThanGiven(d, in.left);
        else
            return Math.max(in.data, maxTreeNodeLessThanGiven(d, in.right));
    }

    private static Double[] beforeAfter(Double d, Trees.TreeNode<Double> in, Double maxMin, Double minMax) {
        if (in == null)
            return new Double[] {maxMin, minMax};
        if (in.data < d)
            return beforeAfter(d, in.right, Math.max(in.data, maxMin), minMax);
        else if (d < in.data)
            return beforeAfter(d, in.left, maxMin, Math.min(in.data, minMax));
        else // d == in.data
            return new Double[] {in.data, in.data};
    }
}
