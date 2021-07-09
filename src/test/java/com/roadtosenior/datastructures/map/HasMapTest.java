package com.roadtosenior.datastructures.map;

import com.roadtosenior.datastructures.map.HashMap.Entry;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class HasMapTest {

    private HashMap<String, String> hashMap;
    private HashMap<String, String> emptyHashMap = new HashMap<>();

    @Before
    public void before() {
        hashMap = new HashMap<>();
        hashMap.put("A", "one");
        hashMap.put("B", "two");
        hashMap.put("C", "three");
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
        assertEquals("one", hashMap.remove("A"));
        assertEquals(4, hashMap.size());
        assertEquals("two", hashMap.remove("B"));
        assertEquals("three", hashMap.remove("C"));

        assertFalse(hashMap.isEmpty());
        assertFalse(hashMap.containsKey("C"));

        assertEquals("four", hashMap.remove("D"));
        assertEquals("five", hashMap.remove("E"));
        assertTrue(hashMap.isEmpty());
    }

    @Test
    public void testIterator() {
        for (Entry<String, String> entry : hashMap) {
            emptyHashMap.put(entry.key, entry.value);
        }

        assertEquals(5, emptyHashMap.size());

        Iterator<Entry<String, String>> it = hashMap.iterator();
        it.hasNext();
        String key = it.next().getValue();
        it.remove();
        assertFalse(hashMap.containsKey(key));
        assertEquals(4, hashMap.size());
    }
}