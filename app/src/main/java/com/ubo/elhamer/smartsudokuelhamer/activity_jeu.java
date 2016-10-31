package com.ubo.elhamer.smartsudokuelhamer;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class activity_jeu extends AppCompatActivity {

    //getters and setters are missing in the following code
    private final int CHOIX_NUM_FENETRE = 0;
    private Grille grille;
    private Intent intent;
    private int x = 0;
    private int y = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        //grille=(Grille) findViewById(R.id.MainGrille);
        Intent mIntent = getIntent();
        grille=new Grille(this.getBaseContext(), mIntent.getIntExtra("diff",0));
        ((RelativeLayout) findViewById(R.id.activity_jeu)).addView(grille);
        intent=new Intent(this, activity_choix.class);


        // Intent Ã  initialiser ici
        // Associer la grille de l'interface graphique ici
        grille.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    x = grille.getXFromMatrix((int) event.getX());
                    y = grille.getYFromMatrix((int) event.getY());
                    if (x < 9 && y < 9 && grille.isNotFix(x, y))
                        startActivityForResult(intent, CHOIX_NUM_FENETRE);
                }
                return true;
            }
        });

        Button validerB = (Button) findViewById(R.id.ValiderButton);
        validerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Code de validation de grille
                valider(view);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.jeu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int request, int result, Intent intent) {
        // vÃ©rifier si la case n'est pas fixe, on lui affecte le numÃ©ro result
        if (request == CHOIX_NUM_FENETRE) {
            if(result == Activity.RESULT_OK){
                String ch=intent.getStringExtra("choix");
                grille.set(x,y, Integer.parseInt(ch));
                Toast.makeText(this, ch,
                        Toast.LENGTH_SHORT).show();
            }
            if (result == Activity.RESULT_CANCELED) {

            }
        }
    }

    public void valider(View v) {
        boolean resultat = grille.isValid() ;
        if(resultat)
            Toast.makeText(this, "Grille Valide",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Grille Non Valide",
                    Toast.LENGTH_LONG).show();
        System.out.println("RÃ©sultat : "+resultat);
    }
}
