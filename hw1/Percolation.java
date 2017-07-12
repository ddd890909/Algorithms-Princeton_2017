/*
 * home work 1
 * Percolation
 * ddd
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int rowMax;
	private int colMax;
	private int count;
	private int countOpen;
	private boolean[] siteOpen;
	private WeightedQuickUnionUF wqu;
	private WeightedQuickUnionUF wquNoBottom;
	
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("illegal argument n = " + n);
		}
		rowMax = n;
		colMax = n;
		count = n * n + 2;
		wqu = new WeightedQuickUnionUF(count);
		wquNoBottom = new WeightedQuickUnionUF(count - 1);
		siteOpen = new boolean[count];
		countOpen = 0;
        for (int i = 0; i <= count - 1; i++) {
        	siteOpen[i] = false;
        }
        siteOpen[0] = true;
        siteOpen[count - 1] = true;
	} // create n-by-n grid, with all sites blocked
	
	private void validate(int row, int col) {
		if (row < 1 || row > rowMax) {
            throw new IndexOutOfBoundsException("row " + row + " is not between 1 and " + rowMax);  
        }
		if (col < 1 || col > colMax) {
            throw new IndexOutOfBoundsException("col " + col + " is not between 1 and " + colMax);  
        }
    }
	
	private int xyTo1D(int row, int col) {
		validate(row, col);
		int result = (row - 1) * colMax + col;
		return result;		
	} // map 2D coordinates to 1D coordinates
	
	public void open(int row, int col) {
		validate(row, col);
		if (siteOpen[xyTo1D(row, col)] == false) {
			siteOpen[xyTo1D(row, col)] = true;
			countOpen++;
			if (row == 1) {
				wqu.union(xyTo1D(row, col), 0);
				wquNoBottom.union(xyTo1D(row, col), 0);
			} else {
				if (isOpen(row - 1, col)) {
					wqu.union(xyTo1D(row, col), xyTo1D(row - 1, col));
					wquNoBottom.union(xyTo1D(row, col), xyTo1D(row - 1, col));
				}
			}
			if (row == rowMax) {
				wqu.union(xyTo1D(row, col), count - 1);
			} else {			
				if (isOpen(row + 1, col)) {
					wqu.union(xyTo1D(row, col), xyTo1D(row + 1, col));
					wquNoBottom.union(xyTo1D(row, col), xyTo1D(row + 1, col));
				}
			}
			if (col >= 2 && isOpen(row, col - 1)) {
				wqu.union(xyTo1D(row, col), xyTo1D(row, col - 1));
				wquNoBottom.union(xyTo1D(row, col), xyTo1D(row, col - 1));
			}		
			if (col <= colMax - 1 && isOpen(row, col + 1)) {
				wqu.union(xyTo1D(row, col), xyTo1D(row, col + 1));
				wquNoBottom.union(xyTo1D(row, col), xyTo1D(row, col + 1));
			}
		}
	}    // open site (row, col) if it is not open already
	
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return (siteOpen[xyTo1D(row, col)] == true);
	}  // is site (row, col) open?
	
	public boolean isFull(int row, int col) {
		validate(row, col);
		return wquNoBottom.connected(xyTo1D(row, col), 0);
	} // is site (row, col) full?
	
	public int numberOfOpenSites() {
		return countOpen;
	} // number of open sites
	
	public boolean percolates() {
		return wqu.connected(count - 1, 0);
	} // does the system percolate?

	public static void main(String[] args) {
		int n = StdIn.readInt();
		Percolation percol = new Percolation(n);
		percol.open(1, 1);
		percol.open(2, 1);
		percol.open(3, 1);
		percol.open(3, 3);
		StdOut.println(percol.isFull(3, 3));
		StdOut.println(percol.wqu.connected(percol.xyTo1D(1, 1), percol.xyTo1D(3, 3)));
		StdOut.println(percol.wquNoBottom.connected(percol.xyTo1D(1, 1), percol.xyTo1D(3, 3)));
		StdOut.println(percol.percolates());
	}  // test client (optional)
}
