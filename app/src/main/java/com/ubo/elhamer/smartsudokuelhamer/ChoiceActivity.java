package com.ubo.elhamer.smartsudokuelhamer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChoiceActivity extends AppCompatActivity {
    private String[] listeChoix=new String[]{"1","2","3","4","5","6","7","8","9"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_activity_layout);
        ListView mListView = (ListView) findViewById(R.id.NumbersListeView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChoiceActivity.this,
                android.R.layout.simple_list_item_1, listeChoix);
        mListView.setAdapter(adapter);
        mListView.setClickable(true) ;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("choix",listeChoix[position]);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
