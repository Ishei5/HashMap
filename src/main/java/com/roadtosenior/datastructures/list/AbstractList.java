package com.roadtosenior.datastructures.list;

public abstract class AbstractList<E> implements List<E> {

    int size;

    @Override
    public void add(E value) {
        add(value, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E value) {
        return indexOf(value) != -1;
    }

    void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + "," +
                    " index should be in range [0, " + size + ")");
        }
    }

    void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + "," +
                    " index should be in range [0, " + size + "]");
        }
    }
}