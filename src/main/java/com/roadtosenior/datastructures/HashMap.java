package com.roadtosenior.datastructures.map;

import com.roadtosenior.datastructures.list.ArrayList;
import com.roadtosenior.datastructures.list.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
    public Object put(K key, V value) {
        V oldValue = null;
        List<Entry<K, V>> currentBucket = getBucket(key);
        if (currentBucket == null) {
            currentBucket = new ArrayList<>();
            addEntryToBucket(currentBucket, new Entry<>(key, value));
            buckets[getBucketIndex(key)] = currentBucket;
        } else {
            Entry<K, V> currentEntry = getEntryFromBucketByKey(key, currentBucket);
            if (currentEntry == null) {
                addEntryToBucket(currentBucket, new Entry<>(key, value));
            } else {
                oldValue = currentEntry.value;
                currentEntry.setValue(value);
            }
        }
        return oldValue;
    }

    @Override
    public V get(K key) {
        List<Entry<K, V>> currentBucket = getBucket(key);

        return currentBucket == null ? null : (V) getEntryFromBucketByKey(key, currentBucket).getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V remove(K key) {
        List<Entry<K, V>> bucket = getBucket(key);
        if (bucket != null) {
            Entry<K, V> removedEntry = getEntryFromBucketByKey(key, bucket);
            if (removedEntry != null) {
                size--;
                return bucket.remove(bucket.indexOf(removedEntry)).getValue();
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
        List<Entry<K, V>> currentBucket = getBucket(key);
        if (currentBucket == null) {
            return false;
        }
        return getEntryFromBucketByKey(key, getBucket(key)) != null;
    }

    private void addEntryToBucket(List<Entry<K, V>> bucket, Entry<K, V> entry) {
        bucket.add(entry);
        size++;
        mapSpreading();
    }

    private List<Entry<K, V>> getBucket(K key) {
        return buckets[getBucketIndex(key)];
    }

    private int getBucketIndex(K key) {
        return key != null ? key.hashCode() % buckets.length : 0;
    }

    private Entry getEntryFromBucketByKey(K key, List<Entry<K, V>> bucket) {
        for (Entry<K, V> entry : bucket) {
            if (key == null) {
                return entry;
            } else if(key.equals(entry.key)) {
                return entry;
            }
        }
        return null;
    }

    private void mapSpreading() {
        if (size > buckets.length * LOAD_FACTOR) {
            HashMap<K, V> newMap = new HashMap(buckets.length * INCREASE_FACTOR);
            for (Entry<K, V> entry : HashMap.this) {
                newMap.put(entry.getKey(), entry.getValue());
            }
            this.buckets = newMap.buckets;
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

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

    private class HashMapIterator implements Iterator<Entry<K, V>> {

        private int bucketIndex;
        private Iterator<Entry<K, V>> bucketIterator;
        private Entry<K, V> currentEntry;
        private boolean isRemovable;

        @Override
        public boolean hasNext() {
            return bucketIndex != -1;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (bucketIndex < buckets.length) {
                List<Entry<K, V>> currentBucket = buckets[bucketIndex];
                if (currentBucket != null) {
                    if (bucketIterator == null) {
                        bucketIterator = currentBucket.iterator();
                    }
                    while (bucketIterator.hasNext()) {
                        isRemovable = true;
                        currentEntry = bucketIterator.next();
                        if (!bucketIterator.hasNext() && (getNextBucketIndex(bucketIndex + 1) == -1)) {
                            bucketIndex = -1;
                        }
                        return currentEntry;
                    }
                }
                bucketIterator = null;
                bucketIndex = getNextBucketIndex(++bucketIndex);
            }
            return null;
        }

        private int getNextBucketIndex(int bucketIndex) {
            for (int i = bucketIndex; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public void remove() {
            if (isRemovable) {
                HashMap.this.remove(currentEntry.key);
                isRemovable = false;
            }
        }
    }

    /*private class HashMapIterator implements Iterator<Entry<K, V>> {
        private int count;
        private int currentBucketIndex = getBucketIndex(0);
        private int currentEntryIndex = 0;
        private List<Entry<K, V>> currentBucket;
        private Entry<K, V> currentEntry;
        private boolean isRemovable;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentBucket = buckets[currentBucketIndex];
            currentEntry = currentBucket.get(currentEntryIndex);
            if (currentEntryIndex < currentBucket.size() - 1) {
                currentEntryIndex++;
            } else {
                currentBucketIndex = getBucketIndex(++currentBucketIndex);
                currentEntryIndex = 0;
            }
            count++;
            isRemovable = true;
            return currentEntry;
        }

        @Override
        public void remove() {
            if (isRemovable) {
                HashMap.this.remove(currentEntry.key);
                isRemovable = false;
            }
        }

        //
        private int getBucketIndex(int index) {
            for (int i = index; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    return i;
                }
            }
            return -1;
        }
    }*/
}
