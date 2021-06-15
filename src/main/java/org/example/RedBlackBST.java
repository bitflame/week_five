package org.example;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Queue q = new Queue();
    private int treeLevel;

    private class Node {
        /*
         * order
         * perfect black balance
         * no consecutive red links on any path
         * no right-leaning red links
         * */

        Key key; // key
        Value val; // associated data
        Node left, right; // subtrees
        int N; // # nodes in this subtree
        boolean color;
        private double xCoordinate, yCoordinate;

        Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    public void draw() {
        treeLevel = 0;
        setCoordinates(root, 0.9);

        StdDraw.setPenColor(StdDraw.BLACK);
        drawLines(root);
        drawNodes(root);
    }

    private void setCoordinates(Node node, double distance) {
        if (node == null) {
            return;
        }

        setCoordinates(node.left, distance - 0.05);
        node.xCoordinate = (0.5 + treeLevel++) / size();
        node.yCoordinate = distance - 0.05;
        setCoordinates(node.right, distance - 0.05);
    }

    private void drawLines(Node node) {
        if (node == null) {
            return;
        }

        drawLines(node.left);

        if (node.left != null) {
            StdDraw.line(node.xCoordinate, node.yCoordinate, node.left.xCoordinate,
                    node.left.yCoordinate);
        }
        if (node.right != null) {
            StdDraw.line(node.xCoordinate, node.yCoordinate, node.right.xCoordinate,
                    node.right.yCoordinate);
        }

        drawLines(node.right);
    }

    private void drawNodes(Node node) {
        if (node == null) {
            return;
        }

        double nodeRadius = 0.032;

        drawNodes(node.left);

        StdDraw.setPenColor(StdDraw.WHITE);
        //Clear the node circle area
        StdDraw.filledCircle(node.xCoordinate, node.yCoordinate, nodeRadius);
        if (isRed(node)) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(node.xCoordinate, node.yCoordinate, nodeRadius);
        StdDraw.text(node.xCoordinate, node.yCoordinate, String.valueOf(node.key));
        drawNodes(node.right);
    }


    private Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    public Queue<Node> keys() {
        return keys(root);
    }

    /* Test this method to make sure it does not use up more queue space than it actually needs */
    private Queue<Node> keys(Node h) {

        if (h != null) q.enqueue(h.key);
        if (h.left != null) {
            keys(h.left);
        }
        if (h.right != null) {
            keys(h.right);
        }
        return q;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    /* Change a right-leaning tree to a left-leaning */
    Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    /* Color the root black after each insertion into a 3-node */
    void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public Key select(int k) {
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    public int size() {

        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value val) {
        if (h == null) // Do standard insert with red link to parent
            return new Node(key, val, 1, RED);
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    // min, max, select, rank, floor, ceiling and range queries is the same as standard BST
    private void delete(Node h) {
        /// p 441
    }

    private void deletMin() {

    }

    private void deleteMax() {

    }

    public static void main(String[] args) throws InterruptedException {
        RedBlackBST<String, Integer> RBB = new RedBlackBST();
        RedBlackBST<Character, Integer> RBChars = new RedBlackBST<>();
        int index = 0;
        while (RBChars.size() < 16) {
            char c = (char) StdRandom.uniform(65, 91);
            RBChars.put(c, index++);
        }
        StdDraw.text(.4,.08,"Red Black Tree of the First Random Batch");
        RBChars.draw();
        //StdDraw.save("random1.png");
        StdDraw.pause(10000);
        StdDraw.clear();
        RBChars = new RedBlackBST<>();
        index = 0;
        while (RBChars.size() < 16) {
            char c = (char) StdRandom.uniform(65, 91);
            RBChars.put(c, index++);
        }
        StdDraw.text(.4,.08,"Red Black Tree of the Second Random Batch");
        RBChars.draw();
        //StdDraw.save("random2.png");
        /* print one of the problems you worked out already */
        StdDraw.pause(10000);
        StdDraw.clear();
        Character [] solvedArray = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
                'P'};
        RBChars = new RedBlackBST<>();
        index=0;
        for (char c: solvedArray) {
            RBChars.put(c,index++);
        }
        StdDraw.text(.4,.08,"Red Black Tree of the Ascending Array of Characters");
        RBChars.draw();
        //StdDraw.save("ascending.png");
        StdDraw.pause(10000);
        StdDraw.clear();
        Collections.reverse(Arrays.asList(solvedArray));
        RBChars = new RedBlackBST<>();
        index=0;
        for (char c: solvedArray) {
            RBChars.put(c,index++);
        }
        StdDraw.text(.4,.08,"Red Black Tree of the Descending Array of Characters");
        RBChars.draw();
        //StdDraw.save("descending.png");
    }
}
