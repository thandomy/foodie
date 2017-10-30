package com.team3009.foodie;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class RatingFrag extends Fragment {


    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    String uid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v =inflater.inflate(R.layout.fragment_rating, container, false);
       Bundle loc = this.getArguments();
        uid = loc.getString("userId");
                ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);


                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
public void onRatingChanged(RatingBar ratingBar, float rating,
        boolean fromUser) {



        }
        });



        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);

        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View v) {

    final ProgressDialog progressDialog= new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
    progressDialog.setTitle("Rating");
    progressDialog.setMessage("Rating...");
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.show();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            rateMe();
            progressDialog.cancel();
            onBackPressed();
        }
    };
    Handler pdCanceller = new Handler();
    pdCanceller.postDelayed(runnable, 5000);

        }

        });

        return v;
        }
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
    public void rateMe()
    {
        String rating =  String.valueOf(ratingBar.getRating());


        Post p = new Post();
        p.sendRating(rating, uid);
    }


        }
