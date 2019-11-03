package oz.tree;

import java.util.Arrays;

public class FloorCeiling {

    private static Double floor;
    private static Double ceiling;

    public static void main(String[] args) {
        Double dano = Double.valueOf(args[0]);
        Double floor = findFloor(Trees.bst, dano);
        Double ceiling = findCeiling(Trees.bst, dano);

        System.out.printf(" %s < %s < %s %n", floor, dano, ceiling);
        System.out.printf(" %s < %s%n", ceilOf(dano, Trees.bst), dano);
        Double[] beforeAtfer = beforeAfter(dano, Trees.bst, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        System.out.printf(" %s < %s < %s%n", beforeAtfer[0], dano, beforeAtfer[1]);
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

    private static Double ceilOf(Double d, Trees.TreeNode<Double> in) {
        if (in == null)
            return Double.NEGATIVE_INFINITY;
        else if (d > in.data)
            return Math.max(in.data, ceilOf(d, in.right));
        else
            return ceilOf(d, in.left);
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
