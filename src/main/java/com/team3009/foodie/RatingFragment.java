package com.team3009.foodie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


public class RatingFragment extends Fragment {
    RatingBar rating_bar1;
    Button button;
    View v;
    View vie;
    private EditText titl,descrip;
    String title,description,url1 ;
    Context contxt;

    String rating;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle loc = this.getArguments();
        final float[] locData = loc.getFloatArray("location");
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_rating, container, false);
        vie = inflater.inflate(R.layout.uploadfrag, container, false);
        rating_bar1=(RatingBar) v.findViewById(R.id.ratingBar1);
        button=(Button) v.findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                 rating=String.valueOf(rating_bar1.getRating());
                description = descrip.getText().toString();
                Toast.makeText(getActivity(), rating, Toast.LENGTH_LONG).show();
                title = titl.getText().toString();
                Toast.makeText(getActivity(), title, Toast.LENGTH_LONG).show();

                Post post= new Post();
                post.resendData(rating);

            }

        });


        return v;
    }


}
































