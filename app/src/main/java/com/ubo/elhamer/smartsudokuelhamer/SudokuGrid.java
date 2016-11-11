package com.ubo.elhamer.smartsudokuelhamer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class SudokuGrid extends View {

    private boolean helperOn=false;
    private int screenWidth;
    private int screenHeight;
    private int n;
    private Paint blackPaint;
    private Paint redPaint;
    private Paint grayPaint;
    private Paint greenPaint;
    private Paint orangePaint;
    private Paint whitePaint;

    public StateRect getStateRect() {
        return stateRect;
    }

    public void setStateRect(StateRect stateRect) {
        this.stateRect = stateRect;
    }

    private StateRect stateRect=StateRect.NoRect;

    private Paint winPaint;
    private Paint loosePaint;

    public boolean isHelperOn() {
        return helperOn;
    }

    public void setHelperOn(boolean helperOn) {
        this.helperOn = helperOn;
    }
    //turn the matrix to its original state
    public int[][] getOriginalMatrix() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!fixIdx[i][j])
                    matrix[i][j]=0;
            }
        }
        return matrix;
    }
    //return a copy of the original matrix
    public int[][] getOriginalMatrixClone() {
        //clone matrix
        int [][] matrixCopy = new int[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
            matrixCopy[i] = matrix[i].clone();


      //set original matrix
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!fixIdx[i][j])
                    matrixCopy[i][j]=0;
            }
        }
        return matrixCopy;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    private int[][] matrix = new int[9][9];
    private boolean[][] fixIdx = new boolean[9][9];

    public SudokuGrid(Context context, AttributeSet attrs, int defStyle, int difficulty) {
        super(context, attrs, defStyle);
        init(difficulty);
    }

    public SudokuGrid(Context context, AttributeSet attrs, int difficulty) {
        super(context, attrs);
        init(difficulty);
    }

    public SudokuGrid(Context context, int difficulty) {
        super(context);
        init(difficulty);
    }

    private void init(int difficulty) {
        SimpleBoardGenerator.GenerateValidBoard(difficulty,matrix,fixIdx);
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(2);
        blackPaint.setTextSize(40);

        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(3);
        redPaint.setTextSize(40);

        grayPaint = new Paint();
        grayPaint.setAntiAlias(true);
        grayPaint.setColor(Color.LTGRAY);

        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setColor(Color.GREEN);

        orangePaint = new Paint();
        orangePaint.setAntiAlias(true);
        orangePaint.setColor(Color.rgb(255,165,0));

        winPaint = new Paint();
        winPaint.setAntiAlias(true);
        winPaint.setColor(Color.GREEN);
        winPaint.setStyle(Paint.Style.STROKE);
        winPaint.setStrokeWidth(10);

        loosePaint = new Paint();
        loosePaint.setAntiAlias(true);
        loosePaint.setColor(Color.RED);
        loosePaint.setStyle(Paint.Style.STROKE);
        loosePaint.setStrokeWidth(10);

        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        screenWidth = getWidth();
        screenHeight = getHeight();
        int x = Math.min(screenWidth, screenHeight);
        n = (x / 9) - (1 - (x % 2));

        canvas.drawRect(1,1,9*n,9*n,whitePaint);

        for(int j=0; j<10;j++){
            if(j%3 != 0){
                canvas.drawLine(0, j*n, x-10, j*n, blackPaint);
                canvas.drawLine(j*n,0, j*n,x-10 , blackPaint);
            }else{
                canvas.drawLine(0, j*n, x-10, j*n, redPaint);
                canvas.drawLine(j*n,0, j*n,x-10 , redPaint);
            }
        }



        int[][] matrixClone=new int[9][9];
        if(helperOn){

            //clone the matrix
            matrixClone=getOriginalMatrixClone();
            BacktrackingSudokuSolver.IsGridValide(matrixClone);
        }

        String s;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s = "" + (matrix[j][i] == 0 ? "" : matrix[j][i]);
                if (fixIdx[j][i]) {
                    canvas.drawRect(i * n+1,j * n+1,i * n+n-1,j * n+n-1,grayPaint);
                    canvas.drawText(s, i * n + (n / 2) - (n / 10), j * n
                            + (n / 2) + (n / 10), redPaint);
                }
                else {

                    if(helperOn){
                        if (matrixClone[j][i]==matrix[j][i])
                            canvas.drawRect(i * n+1,j * n+1,i * n+n-1,j * n+n-1,greenPaint);
                        else
                            canvas.drawRect(i * n+1,j * n+1,i * n+n-1,j * n+n-1,orangePaint);

                    }

                    canvas.drawText(s, i * n + (n / 2) - (n / 10), j * n
                            + (n / 2) + (n / 10), blackPaint);
                }
            }
        }
        switch (stateRect){
            case LoosingRect:
                canvas.drawRect(1,1,n*9,n*9,loosePaint);
                break;
            case WinningRect:
                canvas.drawRect(1,1,n*9,n*9,winPaint);
                break;
        }
    }

    public int getXFromMatrix(int x) {
        return (x / n);
    }

    public int getYFromMatrix(int y) {
        return (y / n);
    }

    public void set(String s, int i) {
        int v;
        for (int j = 0; j < 9; j++) {
            v = s.charAt(j) - '0';
            matrix[i][j] = v;
            if (v == 0)
                fixIdx[i][j] = false;
            else
                fixIdx[i][j] = true;
        }
    }

    public void set(String s) {
        for (int i = 0; i < 9; i++) {
            set(s.substring(i * 9, i * 9 + 9), i);
        }
    }

    public void set(int x, int y, int v) {
        matrix[y][x]=v;
        invalidate();
    }

    public boolean isNotFix(int x, int y) {

        return fixIdx[y][x]==false;
    }

    public boolean isValid() {
        boolean[] rl = { true, true, true, true, true, true, true, true, true };
        boolean[] rc = { true, true, true, true, true, true, true, true, true };
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j] == 0)
                    return false;
                if (rl[j] && rc[j])
                    rl[j] = rc[j] = false;
                else
                    return false;
            }
            for (int j = 0; j < 9; j++) {
                rl[matrix[i][j] - 1] = true;
                rc[matrix[i][j] - 1] = true;
            }
        }
        // ------
        // 2. VÃ©rifier l'existence de chaque numÃ©ro (de 1 Ã  9) dans chacun
        // des 9 carrÃ©s
        boolean[] r = { true, true, true, true, true, true, true, true, true };
        int w;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                w = 0;
                for (int k = i * 3; k < i * 3 + 3; k++) {
                    for (int l = j * 3; l < j * 3 + 3; l++) {
                        if (matrix[k][l] == 0)
                            return false;
                        if (r[w])
                            r[w++] = false;
                        else
                            return false;
                    }
                }
                for (int k = i * 3; k < i * 3 + 3; k++) {
                    for (int l = j * 3; l < j * 3 + 3; l++) {
                        r[matrix[k][l] - 1] = true;
                    }
                }
            }
        }
        return true;
    }
}