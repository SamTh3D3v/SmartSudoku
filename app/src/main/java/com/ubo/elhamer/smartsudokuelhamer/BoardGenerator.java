package com.ubo.elhamer.smartsudokuelhamer;

/**
 * Created by Oussama Elhamer on 10/31/2016.
 * this class is used to generate a valide Sudoku board, using the following algorithm :
 * 1. start with an empty board, each case has a list of valide numbers
 * 2. choose a random case and fill it randomly with one of the valid numbers
 * 3. Using the backtracking solver check wether the current board has at least one valide solution
 * if not undo the previous step and choose another number and cell
 * -- dont stop untill the board is completly full with numbers
 * -- the backtracking solver ca produce a valide solution on its own but
 *    it isn't randmly generated !
 */

public class BoardGenerator {
}
