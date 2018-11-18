/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    public Deque() {
        size = 0;
        first = null;
        last = null;
    }                           // construct an empty deque

    public boolean isEmpty() {
        return size == 0;
    }                 // is the deque empty?

    public int size() {
        return size;
    }                        // return the number of items on the deque

    public void addFirst(final Item item) {
        if (item == null)
            throw new IllegalArgumentException("Argument cannot be null");

        if (isEmpty()) {
            first = new Node(item, null, null);
            last = first;
        }
        else {
            final Node oldFirst = first;
            first = new Node(item, oldFirst, null);
            oldFirst.prev = first;
        }

        size++;
    }          // add the item to the front

    public void addLast(final Item item) {
        if (item == null)
            throw new IllegalArgumentException("Argument cannot be null");

        if (isEmpty()) {
            first = last = new Node(item, null, null);
        }
        else {
            final Node oldLast = last;
            last = new Node(item, null, oldLast);
            oldLast.next = last;
        }
        size++;
    }           // add the item to the end

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");

        final Item result = first.element;

        if (size == 1) {
            first = null;
            last = null;
            size--;

            return result;
        }
        Node oldFirst = first;
        first = first.next;
        if (first != null)
            first.prev = null;
        oldFirst = null;
        size--;

        return result;
    }             // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");

        final Item result = last.element;

        if (size == 1) {
            first = null;
            last = null;
            size--;
            return result;
        }

        Node oldLast = last;
        last = last.prev;
        if (last != null)
            last.next = null;
        oldLast = null;
        size--;

        return result;
    }                 // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }        // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException();

            final Item result = current.element;
            current = current.next;

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(final String[] args) {
        final Deque<Integer> deque = new Deque<Integer>();
        //deque.addLast(1);
        deque.addFirst(2);
        //deque.addFirst(3);
        //deque.addFirst(4);
        //StdOut.println(deque.removeFirst()); //   ==> 4
        //deque.addLast(6);
        StdOut.println(deque.removeLast()); //   ==> 6

        for (final int i : deque)
            StdOut.print(i);
    }  // unit testing (optional)

    private class Node {
        private final Item element;
        private Node next;
        private Node prev;

        Node(final Item element, final Node next, final Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
