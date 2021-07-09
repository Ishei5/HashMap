package com.roadtosenior.datastructures.map;

public interface Map<K, V> {
    Object put(K key, V value);

    Object get(K key);

    int size();

    V remove(K key);

    boolean isEmpty();

    boolean containsKey(K key);
}
