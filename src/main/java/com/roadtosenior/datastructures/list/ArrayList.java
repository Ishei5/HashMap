package com.roadtosenior.datastructures.list;

import java.util.Iterator;

public class ArrayList<E> extends AbstractList<E>  {

    private static final int INITIAL_CAPACITY = 5;

    private E[] array;

    public ArrayList() {
        this(INITIAL_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        array = (E[]) new Object[initialCapacity];
    }

    @Override
    public void add(E value, int index) {
        validateIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        E removedObject = get(index);
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[size - 1] = null;
        size--;
        return removedObject;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public E set(E value, int index) {
        validateIndex(index);
        E originalObject = get(index);
        array[index] = value;
        return originalObject;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(E value) {
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (value.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E value) {
        if (value == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (value.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void ensureCapacity() {
        if (size == array.length) {
            E[] tempArray = (E[]) new Object[(int) (array.length * 1.5) + 1];
            System.arraycopy(array, 0, tempArray, 0, size);
            array = tempArray;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<E> {

        private int currentIndex = 0;
        private boolean isRemovable = false;

        @Override
        public boolean hasNext() {
            return size > currentIndex;
        }

        @Override
        public E next() {
            E returnedValue = (E) array[currentIndex];
            currentIndex++;
            isRemovable = true;
            return returnedValue;
        }

        @Override
        public void remove() {
            if (isRemovable) {
                int prevIndex = currentIndex - 1;
                ArrayList.this.remove(prevIndex);
                currentIndex = prevIndex;
                isRemovable = false;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
