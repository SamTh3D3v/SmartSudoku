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
        setContentView(R.layout.activity_main);
        Button aboutB = (Button) findViewById(R.id.AboutButton);
        aboutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),activity_about.class);
                startActivity(intent);
            }
        });

        Button jouerB = (Button) findViewById(R.id.JouerButton);
        jouerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),activity_jeu.class);
                startActivity(intent);
            }
        });
    }
}
