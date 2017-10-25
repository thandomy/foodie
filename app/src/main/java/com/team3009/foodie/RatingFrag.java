package com.team3009.foodie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingFrag extends Fragment {

    private RatingBar ratingBar;

    private Button btnSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View  v = inflater.inflate(R.layout.fragment_rating, container, false);

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        btnSubmit = (Button) v.findViewById(R.id.button3);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
               // post.sendComment();
            }
        });


        return v;
    }



}
