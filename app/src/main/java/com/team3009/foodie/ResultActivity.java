package com.team3009.foodie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.team3009.foodie.R;

public class ResultActivity extends AppCompatActivity {
    private TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        textView = (TextView)findViewById(R.id.resultview);
        textView.setText("Welcome to the Result Activity");
    }
}