package com.ubo.elhamer.smartsudokuelhamer;

/**
 * Created by Elhamer Oussama on 10/31/2016.
 * Permet de resoudre une grille donnee
 */

public class BacktrackingSudokuSolver {
    public static final int matrixSize=9;

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
        int blockI = i-(i%3), blockJ = j-(j%3);
        for (i=blockI; i < blockI+3; i++)
            for (j=blockJ; j < blockJ+3; j++)
                if (grid[i][j] == val)
                    return false;
        return true;
    }
    public static Boolean IsGridValide(int[][] grid)
    {
        return isGridValide(grid,0);
    }
   //metod recursive pour resoudre la grille a partir de la position en parametre
    static Boolean isGridValide(int[][] grid, int position)
    {
        if (position == matrixSize*matrixSize)
            return true;

        int line = position/matrixSize, column = position%matrixSize;

        if (grid[line][column] != 0)
            return isGridValide(grid, position+1);

        for (int k=1; k <= 9; k++)
        {
            if (valueValideInLine(k,grid,line) && valueValideInColumn(k,grid,column) && valueValideInBlock(k,grid,line,column))
            {
                grid[line][column]=k;

                if ( isGridValide (grid, position+1) )
                    return true;
            }
        }
        grid[line][column]=0;
        return false;
    }

}
