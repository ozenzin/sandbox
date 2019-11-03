package oz.tree;

public class Trees {

    public static class TreeNode<T> {
        boolean visited;
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public static TreeNode<String> tree = new TreeNode<>("root",
            new TreeNode<>("L1",
                    new TreeNode<>("L2_1",
                            new TreeNode<>("L3_1", null, null), new TreeNode<>("R3_1", null, null)
                    ),
                    new TreeNode<>("R2_1",
                            new TreeNode<>("L3_2", null, null), new TreeNode<>("R3_2", null, null)
                    )
            ),
            new TreeNode<>("R1",
                    new TreeNode<>("L2_2",
                            new TreeNode<>("L3_3", null, null), new TreeNode<>("R3_3", null, null)
                    ),
                    new TreeNode<>("R2_2",
                            new TreeNode<>("L3_4", null, null), new TreeNode<>("R3_4", null, null)
                    )
            )
    );

    public static TreeNode<Integer>  bstOfInt = new TreeNode<>(8,
            new TreeNode<>(4,
                    new TreeNode<>(2,
                            new TreeNode<>(1, null, null), new TreeNode<>(3, null, null)
                    )
                    ,
                    new TreeNode<>(6,
                            new TreeNode<>(5, null, null), new TreeNode<>(7, null, null)
                    )
            ),
            new TreeNode<>(12,
                    new TreeNode<>(10,
                            new TreeNode<>(9, null, null), new TreeNode<>(11, null, null)
                    )
                    ,
                    new TreeNode<>(14,
                            new TreeNode<>(13, null, null), new TreeNode<>(15, null, null)
                    )
            )
    );

    public static TreeNode<Double> bst = new TreeNode<>(8.0,
            new TreeNode<>(4.0,
                    new TreeNode<>(2.0,
                            new TreeNode<>(1.0, null, null), new TreeNode<>(3.0, null, null)
                    )
                    ,
                    new TreeNode<>(6.0,
                            new TreeNode<>(5.0, null, null), new TreeNode<>(7.0, null, null)
                    )
            ),
            new TreeNode<>(12.0,
                    new TreeNode<>(10.0,
                            new TreeNode<>(9.0, null, null), new TreeNode<>(11.0, null, null)
                    )
                    ,
                    new TreeNode<>(14.0,
                            new TreeNode<>(13.0, null, null), new TreeNode<>(15.0, null, null)
                    )
            )
    );
    public static TreeNode<Double> bst2 = new TreeNode<>(8.5,
            new TreeNode<>(4.5,
                    new TreeNode<>(2.5,
                            new TreeNode<>(1.5, null, null), new TreeNode<>(3.5, null, null)
                    )
                    ,
                    new TreeNode<>(6.5,
                            new TreeNode<>(5.5, null, null), new TreeNode<>(7.5, null, null)
                    )
            ),
            new TreeNode<>(12.5,
                    new TreeNode<>(10.5,
                            new TreeNode<>(9.5, null, null), new TreeNode<>(11.5, null, null)
                    )
                    ,
                    new TreeNode<>(14.0,
                            new TreeNode<>(13.0, null, null), new TreeNode<>(15.0, null, null)
                    )
            )
    );


    public static void main(String... args) {

        traversTree(bstOfInt);

        deleteCorrected(bstOfInt, 10);

        traversTree(bstOfInt);
    }

    private static void traversTree(TreeNode<?> tree) {
        if (tree == null) return;
        System.out.printf("PRE---%s%n", tree.data);
        traversTree(tree.left);
        System.out.printf("\t\t\t\tIN---%s%n", tree.data);
        traversTree(tree.right);
        System.out.printf("\t\t\t\t\t\t\t\tPOST---%s%n", tree.data);
    }

    public static void inOrder(TreeNode<?> treeNode) {
        if (treeNode == null) return;
        inOrder(treeNode.left);
        System.out.printf(" " + treeNode.data + " ");
        inOrder(treeNode.right);
    }


    static public TreeNode<Integer> deleteCorrected(TreeNode<Integer> node, int key) {
        if (node != null) {
            if (key < node.data) {
                node.left = deleteCorrected(node.left, key);
            } else if (key > node.data) {
                node.right = deleteCorrected(node.right, key);
            } else {
                if (node.left == null) {
                    node =  node.right;
                } else if (node.right == null) {
                    node = node.left;
                } else {
                    TreeNode<Integer> successor = getLeftmostNode(node.right);
                    node.data = successor.data;
                    node.right = deleteCorrected(node.right, successor.data);//this time guaranteed to lack either left or both children
                }
            }
        }
        return node;
    }

    private static TreeNode<Integer> getLeftmostNode(TreeNode<Integer> root) {
        if (root.left == null)
            return root;
        else
            return getLeftmostNode(root.left);
    }


    static public TreeNode<Integer> delete(TreeNode<Integer> root, int key) {
        if (root == null) {
            return root;
        } else if (key < root.data) {
            root.left = delete(root.left, key);
            return root;
        } else if (key > root.data) {
            root.right = delete(root.right, key);
            return root;
        } else {
            if (root.left == null) {
                return root.right;
            } else if(root.right == null) {
                return root.left;
            } else {
                return populateWithNextInOrder(root.right, root);
            }
        }
    }
    private static TreeNode<Integer> populateWithNextInOrder(TreeNode<Integer> root, TreeNode<Integer> parent) {
        if (root.left == null) {
            parent.right.data = root.data;
            return root.right;
        } else {
            root.left = populateWithNextInOrder(root.left, parent);
            return root;
        }
    }

    public static void printMerged(TreeNode<Double> one, TreeNode<Double> two) {
        if (one != null && two != null) {
            printMerged(one.left, two.left);
            if (one.data.compareTo(two.data) < 0) {
                printOnceOnly(one);
                printMerged(one.right, two);
            } else {
                printOnceOnly(two);
                printMerged(one, two.right);
            }
        } else if (one != null) {
            printMerged(one.left, two);
            printOnceOnly(one);
            printMerged(one.right, two);
        } else if (two != null) {
            printMerged(one, two.left);
            printOnceOnly(two);
            printMerged(one, two.right);
        }
    }

    private static void printOnceOnly(TreeNode<?> node) {
        if (!node.visited) {
            System.out.printf("%s ", node.data);
            node.visited = true;
        }
    }
}
