package com.roadtosenior.datastructures.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public abstract class AbstractListTest {
    List<Integer> listWithZeroElements;
    List<Integer> listWithFiveElements;
    List<Integer> listWithTenElements;

    @Before
    public void before() {
        listWithZeroElements = getList();

        listWithFiveElements = getList();
        for (int i = 0; i < 5; i++) {
            listWithFiveElements.add(i);

        }
        listWithTenElements = getList();
        for (int i = 0; i < 10; i++) {
            listWithTenElements.add(i);
        }
    }

    abstract List<Integer> getList();

    @Test
    public void testSizeOnEmptyList() {
        assertEquals(0, listWithZeroElements.size());
    }

    @Test
    public void testSizeOnNonEmptyList() {
        assertEquals(5, (int) listWithFiveElements.size());
    }

    @Test
    public void testAddByIndex() {
        listWithFiveElements.add(6, 1);
        assertEquals(6, (int) listWithFiveElements.get(1));
        assertEquals(6, listWithFiveElements.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddByInvalidIndex() {
        listWithFiveElements.add(12, 12);
    }

    @Test
    public void testGet() {
        for (int i = 0; i < 10; i++) {
            assertEquals(i, (int) listWithTenElements.get(i));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetByIndexLessThenZero() {
        listWithZeroElements.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetByIndexAfterSize() {
        listWithFiveElements.get(6);
    }

    @Test
    public void testCleatList() {
        listWithFiveElements.clear();
        assertEquals(true, listWithFiveElements.isEmpty());
    }

    @Test
    public void testContains() {
        boolean actual = listWithTenElements.contains(5);
        assertEquals(true, actual);
    }

    @Test
    public void testIndexOf() {
        listWithTenElements.add(1, 2);
        int expected = 1;
        int actual = listWithTenElements.indexOf(1);
        assertEquals(expected, actual);
    }

    @Test
    public void testLastIndexOf() {
        listWithTenElements.add(1, 2);
        int expected = 2;
        int actual = listWithTenElements.lastIndexOf(1);
        assertEquals(expected, actual);
    }

    @Test
    public void testSet() {
        Object changedValue = listWithFiveElements.set(7, 1);
        assertEquals(1, changedValue);
        assertEquals(7, (int) listWithFiveElements.get(1));
    }

    @Test
    public void testRemove() {
        Object removedValue = listWithFiveElements.remove(3);
        assertEquals(3, removedValue);
        assertEquals(4, (int) listWithFiveElements.get(3));
        assertEquals(4, listWithFiveElements.size());
    }

    @Test
    public void testIterator() {
        Iterator it = listWithFiveElements.iterator();

        while (it.hasNext()) {
            for (int i = 0; i < 5; i++) {
                Object value = it.next();
                assertEquals(i, value);
            }
        }
    }

    @Test
    public void testIteratorRemove() {
        Iterator it = listWithFiveElements.iterator();
        while (it.hasNext()) {
            Object listElement = it.next();
            if (listElement.equals(1) || listElement.equals(3)) {
                it.remove();
            }
        }
        assertEquals(3, listWithFiveElements.size());
        assertFalse(listWithFiveElements.contains(1));
        assertFalse(listWithFiveElements.contains(3));
    }
}
