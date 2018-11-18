/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(final String[] args) {
        final int k = Integer.parseInt(args[0]);
        int printed = 0;
        final RandomizedQueue<String> rqs = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            final String s = StdIn.readString();
            rqs.enqueue(s);
        }
        while (printed < k) {
            StdOut.println(rqs.dequeue());
            printed++;
        }
    }
}
