package com.team3009.foodie;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        //textView = (TextView)findViewById(R.id.resultview);
        Bundle messageBundle = getIntent().getExtras();
        String message = messageBundle.getString("message");
        //textView.setText(message);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Messenger fragment = new Messenger();
        FragmentManager mFragmentManager = getSupportFragmentManager();

        Bundle loc = new Bundle();
        loc.putString("message",message);
        fragment.setArguments(loc);
        mFragmentManager.beginTransaction().replace(R.id.contain, fragment).commit();


    }
}