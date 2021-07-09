package com.roadtosenior.datastructures.list;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkedListTest extends AbstractListTest{

    @Override
    List<Integer> getList() {
        return new LinkedList<>();
    }

    @Test
    public void testAddToHead() {
        listWithFiveElements.add(11, 0);
        assertEquals(6, listWithFiveElements.size());
        assertEquals(11, (int) listWithFiveElements.get(0));
        assertEquals(0, (int) listWithFiveElements.get(1));
    }

    @Test
    public void testAddToTail() {
        listWithFiveElements.add(11, 4);
        assertEquals(6, listWithFiveElements.size());
        assertEquals(11, (int) listWithFiveElements.get(4));
        assertEquals(4, (int) listWithFiveElements.get(5));
    }
}
