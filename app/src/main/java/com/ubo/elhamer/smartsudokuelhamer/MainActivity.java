package com.ubo.elhamer.smartsudokuelhamer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        Button aboutB = (Button) findViewById(R.id.AboutButton);
        aboutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),AboutActivity.class);
                startActivity(intent);
            }
        });

        Button jouerB = (Button) findViewById(R.id.JouerButton);
        jouerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DifficultyActivity.class);
                startActivity(intent);
            }
        });
    }
}
