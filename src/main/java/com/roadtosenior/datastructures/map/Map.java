package com.roadtosenior.datastructures.map;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {

    V put(K key, V value);

    V get(K key);

    int size();

    V remove(K key);

    boolean isEmpty();

    boolean containsKey(K key);

    interface Entry<K, V> {
        K getKey();

        V getValue();

        void setValue(V value);
    }
}
