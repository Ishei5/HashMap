package com.roadtosenior.datastructures.list;

public interface List<E> extends Iterable<E> {
    // add value to the end of the list
    void add(E value);

    // [A, B, C, null, null ] size = 3
    // add (D, [0,1,2,3])
    // we can add value by index between [0, size]
    // otherwise throw new IndexOutOfBoundsException
    void add(E value, int index);

    // we can remove value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException

    // [A, B, C] remove = 0
    // [B (index = 0) , C (index = 1)]
    E remove(int index);

    // [A, B, C] size = 3
    // we can get value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    E get(int index);

    // we can set value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    E set(E value, int index);

    void clear();

    int size();

    boolean isEmpty();

    boolean contains(E value);

    // [A, B, A, C] indexOf(A) -> 0
    // -1 if not exist
    int indexOf(E value);

    // [A, B, A, C] lastIndexOf(A) -> 2
    int lastIndexOf(E value);

    // [A, B, C]
    String toString();
}