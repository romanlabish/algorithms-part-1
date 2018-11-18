/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] percolateGrid;
    private final int size;
    private final WeightedQuickUnionUF connectionWithTopNode;
    private final WeightedQuickUnionUF connectionWithBothNodes;
    private int numberOfOpenSites;


    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException("Grid size should be greater than 0.");
        size = n;
        percolateGrid = new boolean[size * size + 1];
        connectionWithTopNode = new WeightedQuickUnionUF((size * size) + 1);
        connectionWithBothNodes = new WeightedQuickUnionUF((size * size) + 2);
        numberOfOpenSites = 0;
    }

    public void open(int row, int col) {
        validateCell(row, col);

        if (isOpen(row, col))
            return;
        int position = getConvertedPosition(row, col);
        percolateGrid[position] = true;
        numberOfOpenSites++;
        if (row == 1) {
            connectionWithTopNode.union(0, position);
            connectionWithBothNodes.union(0, position);
        }
        if (row == size) {
            connectionWithBothNodes.union(size * size + 1, position);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            connectionWithTopNode.union(getConvertedPosition(row - 1, col), position);
            connectionWithBothNodes.union(getConvertedPosition(row - 1, col), position);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            connectionWithTopNode.union(getConvertedPosition(row, col - 1), position);
            connectionWithBothNodes.union(getConvertedPosition(row, col - 1), position);
        }
        if (row < size && isOpen(row + 1, col)) {
            connectionWithTopNode.union(getConvertedPosition(row + 1, col), position);
            connectionWithBothNodes.union(getConvertedPosition(row + 1, col), position);
        }
        if (col < size && isOpen(row, col + 1)) {
            connectionWithTopNode.union(getConvertedPosition(row, col + 1), position);
            connectionWithBothNodes.union(getConvertedPosition(row, col + 1), position);
        }
    }   // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        validateCell(row, col);

        return percolateGrid[getConvertedPosition(row, col)];
    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        validateCell(row, col);

        int position = getConvertedPosition(row, col);
        return connectionWithTopNode.connected(0, position);
    } // is site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }      // number of open sites

    public boolean percolates() {
        return connectionWithBothNodes.connected(0, size * size + 1);
    }              // does the system percolate?

    private int getConvertedPosition(int row, int col) {
        return (row - 1) * size + col;
    }

    private void validateCell(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size)
            throw new IllegalArgumentException("Invalid row or column");
    }
}
