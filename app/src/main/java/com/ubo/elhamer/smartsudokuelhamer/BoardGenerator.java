package com.ubo.elhamer.smartsudokuelhamer;

import android.content.Intent;
import android.os.Debug;
import android.widget.GridLayout;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Oussama Elhamer on 10/31/2016.
 * this class is used to generate a valide Sudoku board, using the following algorithm :
 * 1. start with an empty board, each case has a list of valide numbers
 * 2. choose a random case and fill it randomly with one of the valid numbers
 * 3. Using the backtracking solver check wether the current board has at least one valid solution
 * if not undo the previous step and choose another number and cell
 * -- dont stop untill the board is completly full with numbers
 * -- the backtracking solver ca produce a valid solution on its own but
 *    it isn't randmly generated !
 */

public class BoardGenerator {


    public static GridCase[][] generateValidBoard(int numberOfFilledCases){
        //Init a new empty grid
        GridCase[][] tmpGrid=new GridCase[9][9], originalGrid;
        ArrayList<Integer> tmpList=new ArrayList<>(9);
        for (int i=1;i<10;i++)
            tmpList.add(i);

        for(GridCase[] arr2: tmpGrid)
        {
            for(GridCase val: arr2){
                val=new GridCase();
                val.setPossibleValues((ArrayList<Integer>) tmpList.clone());
                val.setVal(0);
            }

        }

        int cpt=0;
        while(cpt < numberOfFilledCases) {

           originalGrid= tmpGrid.clone();


            //choose a random case
            int randCase = ThreadLocalRandom.current().nextInt(0, 81);
            int line = randCase / 9;
            int column = randCase % 9;

            if (tmpGrid[line][column].getVal() == 0) {
                int randValueIndex = ThreadLocalRandom.current().nextInt(0, tmpGrid[line][column].getPossibleValues().size());
                int randValue = (tmpGrid[line][column].getPossibleValues()).get(randValueIndex);
                tmpGrid[line][column].setVal(randValue);

                for (int i = 0; i < 9; i++) {
                    tmpGrid[line][i].getPossibleValues().remove(new Integer(randValue));
                    if (i != randCase / 9)
                        tmpGrid[i][column].getPossibleValues().remove(new Integer(randValue));
                }
                int _i = line - (line % 3), _j = column - (column % 3);
                for (int _line = _i; _line < _i + 3; _line++)
                    for (int _column = _j; _column < _j + 3; _column++)
                        if (tmpGrid[_line][_column].getPossibleValues().contains(new Integer(randValue)))
                            tmpGrid[_line][_column].getPossibleValues().remove(new Integer(randValue));


                if(BacktrackingSudokuSolver.IsGridValide(tmpGrid)){
                    cpt++;
                }else{
                    tmpGrid=originalGrid;
                }
            }
        }
        return tmpGrid;
    }
    public static int[][] generateValidPrimitiveBoard(int numberOfFilledCases){
         int[][] matrix = new int[9][9];

        GridCase[][] gridMatrix=generateValidBoard(numberOfFilledCases);
        for (int i=0; i<9;i++){
            for (int j=0; j<9;j++){
                matrix[i][j]=gridMatrix[i][j].getVal();
            }
        }
       return matrix;

    }

}

