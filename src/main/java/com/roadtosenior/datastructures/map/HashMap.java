package com.roadtosenior.datastructures.map;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {

    private static final int INITIAL_CAPACITY = 5;
    private static final double LOAD_FACTOR = 0.75;
    private static final int INCREASE_FACTOR = 2;

    private int size;
    private List<Entry<K, V>>[] buckets;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int initialCapacity) {
        buckets = new ArrayList[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        List<Entry<K, V>> currentBucket = getBucket(key);
        if (currentBucket == null) {
            currentBucket = new ArrayList<>(1);
            buckets[getBucketIndex(key)] = currentBucket;
        }

        V oldValue = null;
        Entry<K, V> currentEntry = getEntry(key);
        if (currentEntry == null) {
            currentBucket.add(new Entry<>(key, value));
            size++;
            mapSpreading();
        } else {
            oldValue = currentEntry.getValue();
            currentEntry.setValue(value);
        }
        return oldValue;
    }

    @Override
    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V remove(K key) {
        List<Entry<K, V>> bucket = getBucket(key);
        if (bucket != null) {
            Entry<K, V> removedEntry = getEntry(key);
            if (removedEntry != null) {
                size--;
                bucket.remove(removedEntry);
                return removedEntry.getValue();
            }
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    private List<Entry<K, V>> getBucket(K key) {
        return buckets[getBucketIndex(key)];
    }

    private int getBucketIndex(K key) {
        return getBucketIndex(key, buckets);
    }

    private int getBucketIndex(K key, List<Entry<K, V>>[] buckets) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        if (hash == Integer.MIN_VALUE) {
            hash = Integer.MAX_VALUE;
        }
        return Math.abs(hash) % buckets.length;
    }

    private Entry<K, V> getEntry(K key) {
        List<Entry<K, V>> bucket = buckets[getBucketIndex(key)];
        if (bucket == null) {
            return null;
        }
        for (Entry<K, V> entry : bucket) {
            if (Objects.equals(key, entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    private void mapSpreading() {
        if (size > buckets.length * LOAD_FACTOR) {
            @SuppressWarnings("unchecked")
            List<Entry<K, V>>[] newBuckets = new ArrayList[buckets.length * INCREASE_FACTOR + 1];

            for (Map.Entry<K, V> entry : this) {
                K key = entry.getKey();
                V value = entry.getValue();
                int bucketIndex = getBucketIndex(key, newBuckets);
                List<Entry<K, V>> currentBucket = newBuckets[bucketIndex];
                if (currentBucket == null) {
                    currentBucket = new ArrayList<>();
                    newBuckets[bucketIndex] = currentBucket;
                }
                currentBucket.add(new Entry<>(key, value));
            }
            buckets = newBuckets;
        }
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<Map.Entry<K, V>> {

        private int bucketIndex = -1;
        private Iterator<Entry<K, V>> bucketIterator;
        private Entry<K, V> currentEntry;
        private boolean isRemovable;

        public HashMapIterator() {
            moveToNextBucketIndex();
        }

        @Override
        public boolean hasNext() {
            return bucketIndex != -1 && size > 0;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (bucketIterator == null) {
                bucketIterator = buckets[bucketIndex].iterator();
            }

            if (bucketIterator.hasNext()) {
                currentEntry = bucketIterator.next();
                isRemovable = true;
                if (!bucketIterator.hasNext()) {
                    bucketIterator = null;
                    moveToNextBucketIndex();
                }
                return currentEntry;
            }
            return null;
        }

        private void moveToNextBucketIndex() {
            for (int i = bucketIndex + 1; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    bucketIndex = i;
                    return;
                }
            }

            bucketIndex = -1;
        }

        @Override
        public void remove() {
            if (!isRemovable) {
                throw new IllegalStateException();
            }

            HashMap.this.remove(currentEntry.getKey());
            isRemovable = false;
        }
    }

    static class Entry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
