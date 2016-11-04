package com.ubo.elhamer.smartsudokuelhamer;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class activity_jeu extends AppCompatActivity {


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

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

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //grille=(Grille) findViewById(R.id.MainGrille);
        Intent mIntent = getIntent();
        grille=new Grille(this.getBaseContext(), mIntent.getIntExtra("diff",0));
        ((RelativeLayout) findViewById(R.id.activity_jeu)).addView(grille);
        intent=new Intent(this, activity_choix.class);


        // Intent a  initialiser ici
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
            public void onClick(View v) {
//Code de validation de grille
                valider(v);
            }
        });

    }
    private void addDrawerItems() {
        String[] menuArray = { "Nouveau", "Aide ON/OFF","Valider","Resoudre", "A propos" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: //nouvelle partie
                        Intent intent = new Intent(view.getContext(),activity_difficulty.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1: //help
                        grille.setHelperOn(!grille.isHelperOn());
                        grille.invalidate();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 2: //valider
                        valider(view);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 3: //Resoudre or solve the grid
                        BacktrackingSudokuSolver.IsGridValide(grille.getOriginalMatrix());
                        grille.invalidate();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 4: //Credit, A propos
                        Intent inte = new Intent(view.getContext(),activity_about.class);
                        startActivity(inte);
                        mDrawerLayout.closeDrawers();
                        break;
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }




    @Override
    public void onActivityResult(int request, int result, Intent intent) {
        // verifier si la case n'est pas fixe, on lui affecte le numero result
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
        if(resultat){
            grille.setStateRect(StateRect.WinningRect);
            grille.invalidate();
            Toast.makeText(this, "Grille Valide",
                    Toast.LENGTH_LONG).show();
        }
        else{
            grille.setStateRect(StateRect.LoosingRect);
            grille.invalidate();
            Toast.makeText(this, "Grille Non Valide",
                    Toast.LENGTH_LONG).show();
            System.out.println("Resultat : "+resultat);
        }
    }
}
