package org.example;

import edu.princeton.cs.algs4.StdDraw;

public class RBBDraw<Key extends Comparable<Key>, Value> extends RedBlackBST <Key, Value> {
    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int size;              // number of nodes in tree rooted here
        private double xCoordinate, yCoordinate;

        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }
    private Node root;
    private int treeLevel;

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        return node.size;
    }

    public static void main(String[] args) {
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(StdDraw.BLUE);
        //StdDraw.point(0.5, 0.5);
        StdDraw.circle(.02, .02, .02);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);
    }
}
