package com.ubo.elhamer.smartsudokuelhamer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_activity_layout);

        Button hardB=(Button) findViewById(R.id.HardButton);
        hardB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(view.getContext(),GameActivity.class);
                intent.putExtra("diff", DifficultyEnum.Medium.getValue());
                startActivity(intent);
            }
        });

        Button easyB=(Button) findViewById(R.id.EasyButton);
        easyB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(view.getContext(),GameActivity.class);
                intent.putExtra("diff", DifficultyEnum.Easy.getValue());
                startActivity(intent);
            }
        });

        Button verryHardB=(Button) findViewById(R.id.VeryHardButton);
        verryHardB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(view.getContext(),GameActivity.class);
                intent.putExtra("diff", DifficultyEnum.Hard.getValue());
                startActivity(intent);
            }
        });
    }
}
