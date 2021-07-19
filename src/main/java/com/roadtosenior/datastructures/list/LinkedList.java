package com.roadtosenior.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class LinkedList<E> extends AbstractList<E> {

    private Node<E> head;
    private Node<E> tail;

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private static class Node<E> {
        private Node<E> next;
        private Node<E> prev;
        private E value;

        private Node(E value) {
            this.value = value;
        }
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
            Node<E> nextNode = getNode(index);
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

    private void removeNode(Node<E> node) {
        if (size == 1) {
            head = tail = null;
        }
        if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        return getNode(index).value;
    }

    @Override
    public E set(E value, int index) {
        E returnedValue = get(index);
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
        Node<E> current = head;
        int index = 0;

        if (value == null) {
            while (current != null) {
                if (current.value == null) {
                    return index;
                }
                current = current.next;
                index++;
            }
        }
        if (value != null) {
            while (current != null) {
                if (value.equals(current.value)) {
                    return index;
                }
                current = current.next;
                index++;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        Node<E> current = tail;
        int index = size - 1;

        if (value == null) {
            while (current != null) {
                if (current.value == null) {
                    return index;
                }
                current = current.prev;
                index--;
            }
        }
        if (value != null) {
            while (current != null) {
                if (value.equals(current.value)) {
                    return index;
                }
                current = current.prev;
                index--;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");

        for (E e : this) {
            result.add(String.valueOf(e));
        }
        return result.toString();
    }

    private Node<E> getNode(int index) {
        Node<E> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node<E> currentNode = head;
        private Node<E> prevNode = null;
        private boolean isRemovable = false;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public E next() {
            if (currentNode.next == head) {
                throw new NoSuchElementException();
            }
            E value = null;
            if (hasNext()) {
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
            if (isRemovable) {
                removeNode(currentNode.prev);
                isRemovable = false;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
