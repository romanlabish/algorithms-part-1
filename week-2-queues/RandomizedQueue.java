import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        size = 0;
    }                 // construct an empty randomized queue

    public boolean isEmpty() {
        return size == 0;
    }                 // is the randomized queue empty?

    public int size() {
        return size;
    }                // return the number of items on the randomized queue

    public void enqueue(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (queue.length == size)
            resizeQueue(queue.length * 2);
        queue[size++] = item;
    }           // add the item

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        final int randomIndex = StdRandom.uniform(size);
        final Item result = queue[randomIndex];

        queue[randomIndex] = queue[size - 1];
        queue[size - 1] = null;
        --size;

        if (size > 0 && size == queue.length / 4)
            resizeQueue(queue.length / 2);

        return result;
    }                    // remove and return a random item

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        final int randomIndex = StdRandom.uniform(size);
        return queue[randomIndex];
    }                     // return a random item (but do not remove it)

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }        // return an independent iterator over items in random order

    public static void main(final String[] args) {
        final RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);
        rq.enqueue(1);

        rq.dequeue();

        rq.enqueue(2);
        rq.dequeue();
        rq.enqueue(3);
        rq.dequeue();
        rq.enqueue(4);
        rq.dequeue();
        rq.enqueue(5);

        rq.dequeue();

        for (int i = 0; i < 5; i++) {
            for (final int item : rq) {
                StdOut.print(item);
            }
            StdOut.println();
        }

        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());

    }   // unit testing (optional)

    private void resizeQueue(final int newSize) {
        final Item[] newQueue = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[i];
        }
        
        queue = newQueue;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] randomArray;
        private int currentIndex;

        RandomizedQueueIterator() {
            randomArray = (Item[]) new Object[size];
            currentIndex = 0;

            for (int i = 0; i < size; i++) {
                randomArray[i] = queue[i];
            }

            for (int i = randomArray.length - 1; i > 0; i--) {
                final int j = StdRandom.uniform(0, i + 1);

                final Item temp = randomArray[i];
                randomArray[i] = randomArray[j];
                randomArray[j] = temp;
            }
        }

        public boolean hasNext() {
            return currentIndex != randomArray.length;
        }

        public Item next() {
            if (currentIndex == randomArray.length)
                throw new NoSuchElementException();

            return randomArray[currentIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
