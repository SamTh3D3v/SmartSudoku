package com.ubo.elhamer.smartsudokuelhamer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity_layout);

        TextView tv=(TextView) findViewById(R.id.AboutTextView);
        tv.setClickable(true);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "This app was created by <b>Elhamer Oussama</b>, for learning purposes. <br/>  email: <a href=\"mailto:oussama.elhamer@outlook.com\">oussama.elhamer@outlook.com</a> <br/>" +
                "<br/> source code: <a href='https://github.com/SamTheDev/SmartSudoku'> Smart Sudoku on github</a>";
        tv.setText(Html.fromHtml(text));
    }
}
