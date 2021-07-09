package com.roadtosenior.datastructures.list;

public abstract class AbstractList<E> implements List<E> {

    int size;

    @Override
    public void add(E value) {
        add(value, size);
    }

    @Override
    abstract public void add(E value, int index);

    @Override
    abstract public E remove(int index);

    @Override
    abstract public E get(int index);

    @Override
    abstract public E set(E value, int index);

    @Override
    abstract public void clear();

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
        return indexOf(value) != -1 ? true : false;
    }

    @Override
    abstract public int indexOf(E value);

    @Override
    abstract public int lastIndexOf(E value);

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