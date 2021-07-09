package com.roadtosenior.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class LinkedList<E> extends AbstractList<E> implements List<E>, Iterable<E> {

    private Node head;
    private Node tail;

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private static class Node<E> {
        private Node<E> next;
        private Node<E> prev;
        private E value;

        public Node(E value) {
            this.value = value;
        }
    }

    @Override
    public void add(E value) {
        Node<E> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        }

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
    }

    @Override
    public void add(E value, int index) {
        validateIndexForAdd(index);
        Node<E> node = new Node<>(value);
        if (size == 0) {
            head = tail = node;
        } else if (index == 0) {
            head.prev = node;
            node.next = head;
            head = node;
        } else if (index == size) {
            tail.next = node;
            node.prev = tail;
            tail = node;
        } else {
            Node nextNode = getNode(index);
            node.prev = nextNode.prev;
            nextNode.prev.next = node;

            node.next = nextNode;
            nextNode.prev = node;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        Node<E> node = getNode(index);
        removeNode(node);

        return node.value;
    }

    private void removeNode(Node node) {
        if (node.prev == null) {
            head = node.next;
        } else if (node.next == null) {
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        return (E) getNode(index).value;
    }

    @Override
    public Object set(Object value, int index) {
        Object returnedValue = getNode(index).value;
        getNode(index).value = value;
        return returnedValue;
    }

    @Override
    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override
    public int indexOf(Object value) {
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (getNode(i).value == null) {
                    return i;
                }
            }
        }
        if (value != null) {
            for (int i = 0; i < size; i++) {
                if (getNode(i).value.equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        if (value == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (getNode(i).value == null) {
                    return i;
                }
            }
        }
        if (value != null) {
            for (int i = size - 1; i >= 0; i--) {
                if (getNode(i).value.equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringJoiner result = new StringJoiner(", ", "[", "]");
        Node node = head;

        while (node != null) {
            result.add(String.valueOf(node.value));
            node = node.next;
        }

        return result.toString();
    }

    private int defineNearestSide(int index) {
        return size / 2 >= index ? 0 : size - 1;
    }

    private Node getNode(int index) {
        int start = defineNearestSide(index);
        Node<E> currentNode = null;
        if (start == 0) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = start; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private class LinkedListIterator implements Iterator {
        private Node currentNode = head;
        private Node prevNode = null;
        private boolean isRemovable = false;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Object next() {
            if (currentNode.next == head) {
                throw new NoSuchElementException();
            }
            Object value = null;
            while (hasNext()) {
                value = currentNode.value;
                prevNode = currentNode;
                currentNode = currentNode.next;
                isRemovable = true;
                return value;
            }
            return value;
        }

        @Override
        public void remove() {
            if(isRemovable) {
                removeNode(prevNode);
                isRemovable = false;
            }
        }
    }
}
