package com.ubo.elhamer.smartsudokuelhamer;

/**
 * Created by Elhamer Oussama on 10/31/2016.
 *
 */

public class BacktrackingSudokuSolver {

    static Boolean valueValideInLine(int val, int[][] grid, int line)
    {
        for (int j=0; j < 9; j++)
            if (grid[line][j] == val)
                return false;
        return true;
    }

    static Boolean valueValideInColumn(int val, int[][] grid, int column)
    {
        for (int i=0; i < 9; i++)
            if (grid[i][column] == val)
                return false;
        return true;
    }

    static Boolean valueValideInBlock(int val, int[][] grid, int i, int j)
    {
        int _i = i-(i%3), _j = j-(j%3);
        for (i=_i; i < _i+3; i++)
            for (j=_j; j < _j+3; j++)
                if (grid[i][j] == val)
                    return false;
        return true;
    }
    public static Boolean IsGridValide(int[][] grid){
        return isGridValide(grid,0);
    }

    static Boolean isGridValide(int[][] grid, int position)
    {
        if (position == 9*9)
            return true;

        int i = position/9, j = position%9;

        if (grid[i][j] != 0)
            return isGridValide(grid, position+1);

        for (int k=1; k <= 9; k++)
        {
            if (valueValideInLine(k,grid,i) && valueValideInColumn(k,grid,j) && valueValideInBlock(k,grid,i,j))
            {
                grid[i][j]=k;

                if ( isGridValide (grid, position+1) )
                    return true;
            }
        }
        grid[i][j]=0;
        return false;
    }

}
