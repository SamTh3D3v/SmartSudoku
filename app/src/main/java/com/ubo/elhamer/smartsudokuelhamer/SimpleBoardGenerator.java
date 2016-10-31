package com.ubo.elhamer.smartsudokuelhamer;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Elhamer Oussama on 10/31/2016.
 */

public class SimpleBoardGenerator {
    public static void GenerateValidBoard(int numberOfEmptyCases, int[][] tmpGrid, boolean[][] maskGrid){


       for (int i=0;i<81;i++){
           tmpGrid[i/9][i%9]=0;
           maskGrid[i/9][i%9]=true;
       }

        int randCase = ThreadLocalRandom.current().nextInt(0, 81);
        int line = randCase / 9;
        int column = randCase % 9;
        tmpGrid[line][column]=ThreadLocalRandom.current().nextInt(1,10);

        BacktrackingSudokuSolver.IsGridValide(tmpGrid);

        int cpt=0;
        while(cpt < numberOfEmptyCases) {

             randCase = ThreadLocalRandom.current().nextInt(0, 81);
             line = randCase / 9;
             column = randCase % 9;
            if(tmpGrid[line][column]!=0){
                tmpGrid[line][column]=0;
                maskGrid[line][column]=false;
                cpt++;
            }

        }
    }
    public static boolean SolveBoard(int[][] tmpGrid){

        return BacktrackingSudokuSolver.IsGridValide(tmpGrid);
    }
}
