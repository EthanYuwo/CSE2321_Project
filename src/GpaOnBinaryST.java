import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * GpaOnBinaryST.
 */
public class GpaOnBinaryST {
    /**
     * @param t
     * @param x
     */
    private static void add(BinaryTree<Double> t, double x) {
        BinaryTree<Double> l = t.newInstance();
        BinaryTree<Double> r = t.newInstance();
        if (t.size() > 0) {
            double root = t.disassemble(l, r);
            //add an element based on BST convention.
            if (x <= root) {
                add(l, x);
            } else {
                add(r, x);
            }
            t.assemble(root, l, r);
        } else {
            //t is empty, make the root x.
            t.assemble(x, l, r);
        }
    }

    /**
     * Identifies the next smallest double in order, removes it, and returns the
     * value.
     *
     * @param t
     * @return next double in order.
     */
    private static double pullSmallSuccessor(BinaryTree<Double> t) {
        assert (t.size() > 0) : "Binary tree must be nonempty!";
        double n = 0;
        BinaryTree<Double> l = t.newInstance();
        BinaryTree<Double> r = t.newInstance();
        double root = t.disassemble(l, r);
        if (t.height() > 1) {
            if (l.height() > 0) {
                //Looks for the leftmost node of the tree through recursive calls.
                n = pullSmallSuccessor(l);
                t.assemble(root, l, r);
            } else {
                /**
                 * Found the leftmost node of the tree. We then shift up the
                 * right subtree in place of t.
                 */
                n = root;
                t.transferFrom(r);
            }
        } else {
            /**
             * t is a single node.
             */
            n = root;
        }
        return n;
    }

    /**
     * Identifies the next smallest double in order, removes it, and returns the
     * value.
     *
     * @param t
     * @return next double in order.
     */
    private static double pullLargeSuccessor(BinaryTree<Double> t) {
        assert (t.size() > 0) : "Binary tree must be nonempty!";
        double n = 0;
        BinaryTree<Double> l = t.newInstance();
        BinaryTree<Double> r = t.newInstance();
        double root = t.disassemble(l, r);
        if (t.height() > 1) {
            if (r.height() > 0) {
                //Looks for the rightmost node of the tree through recursive calls.
                n = pullLargeSuccessor(r);
                t.assemble(root, l, r);
            } else {
                /**
                 * Found rightmost node. We then shift up the left subtree in
                 * place of t.
                 */
                n = root;
                t.transferFrom(l);
            }
        } else {
            /**
             * t is a single node.
             */
            root = t.disassemble(l, r);
            n = root;
        }
        return n;
    }

    /**
     * @param t
     * @param x
     */
    private static void remove(BinaryTree<Double> t, double x) {
        assert (t.size() > 0) : "Binary tree must be nonempty!";
        BinaryTree<Double> l = t.newInstance();
        BinaryTree<Double> r = t.newInstance();
        if (t.size() > 1) {
            double root = t.disassemble(l, r);
            if (root == x) {
                double replace = 0;
                if (r.height() > 0) {
                    replace = pullSmallSuccessor(r);
                } else {
                    replace = pullLargeSuccessor(l);
                }
                t.assemble(replace, l, r);
            } else {
                remove(l, x);
                remove(r, x);
                t.assemble(root, l, r);
            }
        } else if (t.size() == 1 && t.root() == x) {
            //t is a leaf node, so we simply remove it.
            t.disassemble(l, r);
        }

    }

    /**
     *
     * @param t
     * @return sum of values of elements in t.
     */
    private static double sum(BinaryTree<Double> t) {
        double sum = 0;
        if (t.height() > 0) {
            BinaryTree<Double> l = t.newInstance();
            BinaryTree<Double> r = t.newInstance();
            double root = t.disassemble(l, r);
            sum += root + sum(l) + sum(r);
            t.assemble(root, l, r);
        }
        return sum;
    }

    /**
     * @param t
     * @return average of values of elements in t.
     */
    private static double average(BinaryTree<Double> t) {
        assert (t.size() > 0) : "Binary tree must be nonempty!";
        double avg = t.root();
        if (t.height() > 1) {
            BinaryTree<Double> l = t.newInstance();
            BinaryTree<Double> r = t.newInstance();
            double root = t.disassemble(l, r);
            avg += sum(l) + sum(r);
            t.assemble(root, l, r);
        }
        avg = avg / t.size();
        return avg;
    }

    /**
     * @param t
     * @return lowest value of the elements in the tree.
     */
    private static double min(BinaryTree<Double> t) {
        assert (t.size() > 0) : "Binary tree must be nonempty!";
        double min = t.root();
        if (t.height() > 0) {
            BinaryTree<Double> l = t.newInstance();
            BinaryTree<Double> r = t.newInstance();
            double root = t.disassemble(l, r);
            /**
             * if l is nonempty, we recurse down the left subtree. Otherwise, we
             * return the root value of the tree.
             */
            if (l.height() > 0) {
                min = min(l);
            }
            t.assemble(root, l, r);

        }
        return min;
    }

    /**
     * @param t
     * @return highest value of the elements in the tree.
     */
    private static double max(BinaryTree<Double> t) {
        assert (t.size() > 0) : "Binary tree must be nonempty!";
        double max = t.root();
        if (t.height() > 0) {
            BinaryTree<Double> l = t.newInstance();
            BinaryTree<Double> r = t.newInstance();
            double root = t.disassemble(l, r);
            /**
             * if r is nonempty, we recurse down the right subtree. Otherwise,
             * we return the root value of the tree.
             */
            if (r.height() > 0) {
                max = max(r);
            }
            t.assemble(root, l, r);

        }
        return max;
    }

    /**
     * @param t
     * @param pos
     * @return target element to be found.
     */
    private static double findElementAtPosition(BinaryTree<Double> t, int pos) {
        int currentPos = 0;
        double target = 0;
        if (t.height() > 0) {
            BinaryTree<Double> l = t.newInstance();
            BinaryTree<Double> r = t.newInstance();
            double root = t.disassemble(l, r);
            currentPos = l.size();
            if (pos < currentPos) {
                target = findElementAtPosition(l, pos);
            } else if (pos > currentPos) {
                target = findElementAtPosition(r, l.size() + r.size() - pos);
            } else {
                target = root;
            }
            t.assemble(root, l, r);
        }
        return target;
    }

    /**
     * @param t
     * @return median value of elements in t.
     */
    private static double median(BinaryTree<Double> t) {
        assert (t.size() > 0) : "Binary tree must be nonempty!";
        double med = 0;
        if (t.size() % 2 == 0) {
            double temp1 = findElementAtPosition(t, t.size() / 2);
            double temp2 = findElementAtPosition(t, (t.size() / 2) - 1);
            med = (temp1 + temp2) / 2;
        } else {
            med = findElementAtPosition(t, t.size() / 2);
        }
        return med;
    }

    /**
     * @param x
     * @return Binary tree from args.
     */
    private static BinaryTree<Double> createFromArgs(double[] x) {
        BinaryTree<Double> t = new BinaryTree1<>();
        for (Double y : x) {
            add(t, y);
        }
        return t;
    }

    /**
     * Main.
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * Binary tree to store values.
         */
        final double root = 9;
        final double lD = 6;
        final double rD = 15;
        final double llD = 4;
        final double lrD = 8;
        final double lllD = 3;
        final double rlD = 12;
        final double rlrD = 14;
        final double rlrlD = 13;

        SimpleWriter out = new SimpleWriter1L();
        double[] d = { root, lD, rD, lrD, llD, rlD, lllD, rlrD, rlrlD };
        BinaryTree<Double> gpaStore = createFromArgs(d);
        out.println(gpaStore);
        double a = average(gpaStore);
        double min = min(gpaStore);
        double max = max(gpaStore);
        double e = findElementAtPosition(gpaStore, 0);
        remove(gpaStore, root);
        out.println(gpaStore);
        double mid = median(gpaStore);
        //out.println(a);
        //out.println(min);
        //out.println(max);
        //out.println(e);
        out.println(mid);
        out.close();

    }

}
