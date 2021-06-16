package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackBSTTest {
    RedBlackBST<Character, Integer> TestRBB = new RedBlackBST<>();
    @BeforeEach
    void setUp() {
        Character[] ascendingArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P'};
        int index = 0;
        for (char c : ascendingArray) TestRBB.put(c, index++);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void keys() {
        assertEquals(1, 1);
        for (Character c: TestRBB.keys()) {

        }
    }

    @Test
    void min() {
        assertEquals(TestRBB.min(),'A');
    }

    @Test
    void floor() {
        /* smaller or equal to the item. Therefore if the item is not in the array, a
        * smaller item should be returned. */
        assertEquals(TestRBB.floor('Q'),'P');
    }

    @Test
    void rank() {
    }

    @Test
    void rotateLeft() {
    }

    @Test
    void rotateRight() {
    }

    @Test
    void select() {
    }

    @Test
    void size() {
    }
}