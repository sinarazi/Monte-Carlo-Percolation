package com.Percolation;

// Importing WeightedQuickUnionUF library of algs4.jar file.
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation
    {
    final private WeightedQuickUnionUF grid;
    final private WeightedQuickUnionUF full;
    final private int n; // number
    final private int top;
    final private int bottom;
    private boolean[] openNodes; // to check opening of nodes



    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
         if (N <= 0) // handle exception of invalid argument
           throw new java.lang.IllegalArgumentException("Sorry, N should be greater than 0 :/ .");


        grid = new WeightedQuickUnionUF(N * N + 2);  // call WeightedQuickUnionUF
        full = new WeightedQuickUnionUF(N * N + 1);

        this.n = N; // object

        top = getSingleArrayIndex(N, N) + 1;
        bottom = getSingleArrayIndex(N, N) + 2;
        openNodes = new boolean[N * N];
    }


    // function to get index of an array
    private int getSingleArrayIndex(int i, int j)
    {
        doOutOfBoundsCheck(i, j);

        return (n * (i - 1) + j) - 1;
    }


    // to implement IndexOutOfBoundsException
    private void doOutOfBoundsCheck(int i, int j)
    {
        if (!isValid(i, j))
            throw new IndexOutOfBoundsException("Sorry, Values not found :/ .");
    }



    // to check validation
    private boolean isValid(int i, int j)
    {
        return i > 0 && j > 0 && i <= n && j <= n;
    }


    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        doOutOfBoundsCheck(i, j);

        if (isOpen(i, j))
        {
            return; // No need to open this again as it's already open
        }

        int index = getSingleArrayIndex(i, j);
        openNodes[index] = true;

        if (i == 1)
        {
            grid.union(top, index); // using union method of WeightedQuickUnionUF
            full.union(top, index);
        }

        if (i == n)
        {
            grid.union(bottom, index);
        }


        if (isValid(i - 1, j) && isOpen(i - 1, j))
        {
            grid.union(getSingleArrayIndex(i - 1, j), index);
            full.union(getSingleArrayIndex(i - 1, j), index);
        }

        if (isValid(i, j + 1) && isOpen(i, j + 1))
        {
            grid.union(getSingleArrayIndex(i, j + 1), index);
            full.union(getSingleArrayIndex(i, j + 1), index);
        }

        if (isValid(i + 1, j) && isOpen(i + 1, j))
        {
            grid.union(getSingleArrayIndex(i + 1, j), index);
            full.union(getSingleArrayIndex(i + 1, j), index);

        }

        if (isValid(i, j - 1) && isOpen(i, j - 1))
        {
            /* Merges the set containing element
            {@code p} with the set containing element {@code q}.
             */
            grid.union(getSingleArrayIndex(i, j - 1), index);
            full.union(getSingleArrayIndex(i, j - 1), index);
        }
    }


    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        doOutOfBoundsCheck(i, j);

        return openNodes[getSingleArrayIndex(i, j)];
    }


    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        int index = getSingleArrayIndex(i, j);

        return connect(index, top); // Returns true if the two elements are in the same set
    }


    /* does the system percolate? and check
    the connection of top and bottom.
    */
    public boolean percolates()
    {
        return connect(top, bottom); // to fix deprecated methods in java api,
                                     // I have to use connect method instead of using connected
                                     // method of WeightedQuickUnionUF

    }

    public boolean connect(int p, int q)
    {
        return grid.find(p) == grid.find(q);
    }
/*
    // to count number of open sites in order to calculate the mean
    public int numberOfOpenSites()
    {
        int openSites = 0;

        for(int row = 1; row <= n; row++)
            for (int cul = 1; cul <= n; cul++)
                if (isOpen(row, cul))
                    openSites++;

        return openSites;
    }

 */
}

