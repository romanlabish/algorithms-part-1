/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;

    private final double mean;
    private final double stddev;
    private final double sqrtTresholdLength;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        double[] trialsPercolationTreshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }

            trialsPercolationTreshold[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }

        mean = StdStats.mean(trialsPercolationTreshold);
        stddev = StdStats.stddev(trialsPercolationTreshold);
        sqrtTresholdLength = Math.sqrt((double) trialsPercolationTreshold.length);
    } // perform trials independent experiments on an n-by-n grid

    public double mean() {
        return mean;
    }                         // sample mean of percolation threshold

    public double stddev() {
        return stddev;
    }                       // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return (mean() - (CONFIDENCE * stddev() / sqrtTresholdLength));
    }                  // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return (mean() + (CONFIDENCE * stddev() / sqrtTresholdLength));
    }                // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }        // test client (described below)
}
