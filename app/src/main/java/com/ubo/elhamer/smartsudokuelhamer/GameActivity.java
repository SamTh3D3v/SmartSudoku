package com.ubo.elhamer.smartsudokuelhamer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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

public class GameActivity extends AppCompatActivity {


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    //getters and setters are missing in the following code
    private final int CHOIX_NUM_FENETRE = 0;
    private SudokuGrid sudokuGrid;
    private Intent intent;
    private int x = 0;
    private int y = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_layout);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent mIntent = getIntent();
        sudokuGrid =new SudokuGrid(this.getBaseContext(), mIntent.getIntExtra("diff",0));
        ((RelativeLayout) findViewById(R.id.activity_jeu)).addView(sudokuGrid);
        intent=new Intent(this, ChoiceActivity.class);

        sudokuGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    x = sudokuGrid.getXFromMatrix((int) event.getX());
                    y = sudokuGrid.getYFromMatrix((int) event.getY());
                    if (x < 9 && y < 9 && sudokuGrid.isNotFix(x, y))
                        startActivityForResult(intent, CHOIX_NUM_FENETRE);
                }
                return true;
            }
        });



        Button validerB = (Button) findViewById(R.id.ValiderButton);
        validerB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                valider(v);
            }
        });

    }
    private void addDrawerItems() {
        String[] menuArray = { "New", "Help ON/OFF","Check","Solve", "Credit" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: //nouvelle partie
                        Intent intent = new Intent(view.getContext(),DifficultyActivity.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1: //help
                        sudokuGrid.setHelperOn(!sudokuGrid.isHelperOn());
                        sudokuGrid.invalidate();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 2: //valider
                        valider(view);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 3: //Resoudre or solve the grid
                        BacktrackingSudokuSolver.IsGridValide(sudokuGrid.getOriginalMatrix());
                        sudokuGrid.invalidate();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 4: //Credit, A propos
                        Intent inte = new Intent(view.getContext(),AboutActivity.class);
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
        if (request == CHOIX_NUM_FENETRE) {
            if(result == Activity.RESULT_OK){
                String ch=intent.getStringExtra("choix");
                sudokuGrid.set(x,y, Integer.parseInt(ch));
                Toast.makeText(this, ch,
                        Toast.LENGTH_SHORT).show();
            }
            if (result == Activity.RESULT_CANCELED) {

            }
        }
    }

    public void valider(View v) {
        boolean resultat = sudokuGrid.isValid() ;
        if(resultat){
            sudokuGrid.setStateRect(StateRect.WinningRect);
            sudokuGrid.invalidate();
            Toast.makeText(this, "You Won",
                    Toast.LENGTH_LONG).show();
        }
        else{
            sudokuGrid.setStateRect(StateRect.LoosingRect);
            sudokuGrid.invalidate();
            Toast.makeText(this, "Try again",
                    Toast.LENGTH_LONG).show();
        }
    }
}
