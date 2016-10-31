package com.ubo.elhamer.smartsudokuelhamer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Grille extends View {

    private int screenWidth;
    private int screenHeight;
    private int n;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    private Difficulty difficulty;

    private int[][] matrix = new int[9][9];
    private boolean[][] fixIdx = new boolean[9][9];

    public Grille(Context context, AttributeSet attrs, int defStyle,int difficulty) {
        super(context, attrs, defStyle);
        init(difficulty);
    }

    public Grille(Context context, AttributeSet attrs,int difficulty) {
        super(context, attrs);
        init(difficulty);
    }

    public Grille(Context context,int difficulty) {
        super(context);
        init(difficulty);
    }

    private void init(int difficulty) {
        //set("000105000140000670080002400063070010900000003010090520007200080026000035000409000");
        // set("672145398145983672389762451263574819958621743714398526597236184426817935831459267");
        // set("123456789912345678891234567789123456678912345567891234456789123345678912234567891");
        // set("000400870", 0);
        // set("047092050", 1);
        // set("200600030", 2);
        // set("970500203", 3);
        // set("508024706", 4);
        // set("604007085", 5);
        // set("090308007", 6);
        // set("003240160", 7);
        // set("012000090", 8);
        SimpleBoardGenerator.GenerateValidBoard(difficulty,matrix,fixIdx);
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(2);
        paint1.setTextSize(40);

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.RED);
        paint2.setStrokeWidth(3);
        paint2.setTextSize(40);
        paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setColor(Color.BLACK);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        screenWidth = getWidth();
        screenHeight = getHeight();
        int x = Math.min(screenWidth, screenHeight);
        n = (x / 9) - (1 - (x % 2));

        // Dessiner les lignes noires et rouges
        for(int j=0; j<10;j++){
            if(j%3 != 0){
                canvas.drawLine(0, j*n, x-10, j*n, paint1);
                canvas.drawLine(j*n,0, j*n,x-10 , paint1);
            }else{
                canvas.drawLine(0, j*n, x-10, j*n, paint2);
                canvas.drawLine(j*n,0, j*n,x-10 , paint2);
            }
        }


        // Le contenu d'une case
        String s;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s = "" + (matrix[j][i] == 0 ? "" : matrix[j][i]);
                if (fixIdx[j][i])
                    canvas.drawText(s, i * n + (n / 2) - (n / 10), j * n
                            + (n / 2) + (n / 10), paint2);
                else
                    canvas.drawText(s, i * n + (n / 2) - (n / 10), j * n
                            + (n / 2) + (n / 10), paint1);
            }
        }
    }

    public int getXFromMatrix(int x) {
        // Renvoie l'indice d'une case Ã  partir du pixel x de sa position h
        return (x / n);
    }

    public int getYFromMatrix(int y) {
        // Renvoie l'indice d'une case Ã  partir du pixel y de sa position v
        return (y / n);
    }

    public void set(String s, int i) {
        // Remplir la iÃ¨me ligne de la matrice matrix avec un vecteur String s
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
        // Remplir la matrice matrix Ã  partir d'un vecteur String s
        for (int i = 0; i < 9; i++) {
            set(s.substring(i * 9, i * 9 + 9), i);
        }
    }

    public void set(int x, int y, int v) {
        // Affecter la valeur v Ã  la case (y,x)
        matrix[y][x]=v;
        invalidate();
    }

    public boolean isNotFix(int x, int y) {

        return fixIdx[y][x]==false;
    }

    public boolean isValid() {
        // 1. VÃ©rifier l'existence de chaque numÃ©ro (de 1 Ã  9) dans chaque
        // ligne et chaque colonne
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
        // ------
        // GagnÃ©
        return true;
    }
}