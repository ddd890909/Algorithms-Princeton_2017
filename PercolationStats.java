/*
 * home work 1
 * Percolation
 * ddd
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] threshold;
	private int count;
	
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("n <= 0 or trials <= 0");
		}		
		count = n * n;
		threshold = new double[trials];
		for (int i = 0; i <= trials - 1; i++) {
			Percolation percol = new Percolation(n);
			while (!percol.percolates()) {
				percol.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
			}
			threshold[i] = (double) percol.numberOfOpenSites() / count;
		}		
	}   // perform trials independent experiments on an n-by-n grid
	
	public double mean() {
		return StdStats.mean(threshold);
	} // sample mean of percolation threshold
	
	public double stddev() {
		return StdStats.stddev(threshold);
	} // sample standard deviation of percolation threshold
	
	public double confidenceLo() {
		double conLow = mean() - 1.96 * stddev() / Math.sqrt((double) threshold.length);
		return conLow;
	} // low  endpoint of 95% confidence interval
	
	public double confidenceHi() {
		double conHig = mean() + 1.96 * stddev() / Math.sqrt((double) threshold.length);
		return conHig;
	} // high endpoint of 95% confidence interval

	public static void main(String[] args) {
		int n = StdIn.readInt();
		int trials = StdIn.readInt();
		PercolationStats perSta = new PercolationStats(n, trials);
        StdOut.println("mean = " + perSta.mean());
        StdOut.println("stddev = " + perSta.stddev());
        StdOut.println("95% confidence interval = [" + perSta.confidenceLo() + ", " 
                                                     + perSta.confidenceHi() + "]");
	} // test client (described below)

}
