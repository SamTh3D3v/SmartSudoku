package com.ubo.elhamer.smartsudokuelhamer;

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

//this code is used to make sure that the tree firts cases of the grid are random, oussama
        tmpGrid[0][0]=ThreadLocalRandom.current().nextInt(1,10);
        int rand2=tmpGrid[0][0];
        while(rand2 == tmpGrid[0][0]){
            rand2=ThreadLocalRandom.current().nextInt(1,10);
        }
        tmpGrid[0][1]=rand2;
        int rand3=ThreadLocalRandom.current().nextInt(1,10);
        while(rand3 == tmpGrid[0][0] || rand3 == tmpGrid[0][1]){
            rand3=ThreadLocalRandom.current().nextInt(1,10);
        }
        tmpGrid[0][2]=rand3;

//----------------------------------------------------------------------------------------

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
