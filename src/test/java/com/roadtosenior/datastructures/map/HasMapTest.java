package com.roadtosenior.datastructures.map;

import com.roadtosenior.datastructures.list.ArrayList;
import com.roadtosenior.datastructures.list.List;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HasMapTest {

    private Map<String, String> hashMap;
    private Map<String, String> emptyHashMap = new HashMap<>();

    @Before
    public void before() {
        hashMap = new HashMap<>();
        hashMap.put("M", "one");
        hashMap.put("B", "two");
        hashMap.put("N", "three");
        hashMap.put("D", "four");
        hashMap.put("E", "five");
    }

    @Test
    public void testSize() {
        assertEquals(0, emptyHashMap.size());
        assertEquals(5, hashMap.size());
    }
    @Test
    public void testPut() {
        assertNull(emptyHashMap.put("A", "one"));
        assertEquals(1, emptyHashMap.size());
        assertNull(emptyHashMap.put("B", "two"));
        assertEquals(2, emptyHashMap.size());

        assertEquals("one", emptyHashMap.put("A", "one1"));
        assertEquals("one1", emptyHashMap.get("A"));
        assertEquals(2, emptyHashMap.size());
    }

    @Test
    public void testPutArrayLists() {
        HashMap<List<Integer>, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            List<Integer> key = new ArrayList<>();
            key.add(i);
            hashMap.put(key, i);
        }
        assertEquals(9, hashMap.size());
    }

    @Test
    public void testPutNull() {
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put(null, "null");
        assertEquals("null", hashMap.get(null));
    }

    @Test
    public void testContainsKeyOnEmptyMap() {
        assertFalse(emptyHashMap.containsKey("A"));
    }

    @Test
    public void testContainsKey() {
        assertTrue(hashMap.containsKey("B"));
        assertFalse(hashMap.containsKey("Z"));
    }

    @Test
    public void testIsEmpty() {
       assertFalse(hashMap.isEmpty());
    }

    @Test
    public void testRemove() {
        String act = hashMap.remove("M");
        assertEquals("one", act);
        assertEquals(4, hashMap.size());
        assertEquals("two", hashMap.remove("B"));
        assertEquals("three", hashMap.remove("N"));

        assertFalse(hashMap.isEmpty());
        assertFalse(hashMap.containsKey("C"));

        assertEquals("four", hashMap.remove("D"));
        assertEquals("five", hashMap.remove("E"));
        assertTrue(hashMap.isEmpty());
    }

    @Test
    public void testIterator() {
        for (Map.Entry<String, String> entry : hashMap) {
            emptyHashMap.put(entry.getKey(), entry.getValue());
        }

        assertEquals(5, emptyHashMap.size());
        Iterator<Map.Entry<String, String>> it = hashMap.iterator();
        it.hasNext();
        String key = it.next().getValue();
        it.remove();
        assertFalse(hashMap.containsKey(key));
        assertThat(hashMap.size(), is(4));
    }
}
