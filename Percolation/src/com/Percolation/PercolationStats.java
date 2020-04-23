package com.Percolation;

import edu.princeton.cs.algs4.StdRandom; // Importing StdRandom library of algs4.jar file.
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;


// perform T independent experiments on an N-by-N grid
public class PercolationStats
    {
    private final int n;
    private final int t;
    private final double[] perStats;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        if (N <= 0 && T <= 0)
        {
            throw new IllegalArgumentException("Sorry, N and T should be greater than 0");
        }
        else
            {
            this.n = N;
            this.t = T;
            this.perStats = new double[T];

            for(int j = 0; j < T; ++j)
            {
                Percolation P = new Percolation(N);

                int i;
                for(i = 0; !P.percolates(); ++i)
                {
                    this.openRandomNode(P);
                }

                this.perStats[j] = (double)i / (double)(N * N); // To gain average of percolate system
            }
        }
    }


    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(this.perStats);
    }


    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(this.perStats);
    }


    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt((double)this.t);
    }


    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt((double)this.t);
    }


    // Open random nodes
    private void openRandomNode (Percolation N) {
        boolean T = true;
        int j = 0;

        int i;
        for(i = 0; T; T = N.isOpen(j, i))
        {
            /* Call uniform method of StdRandom inorder to get
             a random long integer uniformly in [0, n)
             */
            j = StdRandom.uniform(1, this.n + 1);
            i = StdRandom.uniform(1, this.n + 1);
        }

        N.open(j, i);
    }


    // test client, described below
    public static void main(String[] args)
    {
        // Integer.parseInt(args[0])
        int N = 4096;
        int T = 100;
        
        // to measure time of project
        Stopwatch timeW = new Stopwatch();

        PercolationStats ps = new PercolationStats(N, T);
        
        System.out.println("mean:\t\t\t\t\t= " + ps.mean());
        System.out.println("stddev:\t\t\t\t\t= " + ps.stddev());

        double var = ps.confidenceLo();
        System.out.println("95% confidence interval = " + var + ", " + ps.confidenceHi());


        /* Call elapsedTime method of Stopwatch inorder to elapse
         CPU time (in seconds) since the stopwatch was created
         */
        double time = timeW.elapsedTime();
        System.out.println("The total time is:\t" + time); // Print total time

    }
}
