package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile  extends RecyclerView.ViewHolder{


    View mView;
    ImageView userPic;
    ImageView foodie;
    TextView ingredients;
    TextView foodDescr;
    TextView userName;

    public Profile(View itemView) {
        super(itemView);
        mView = itemView;
        userName = (TextView) itemView.findViewById(R.id.userName);
        foodDescr = (TextView) itemView.findViewById(R.id.foodDescr);
        ingredients = (TextView) itemView.findViewById(R.id.ingredients);
        foodie = (ImageView) itemView.findViewById(R.id.foodie);
        userPic = (ImageView) itemView.findViewById(R.id.userPic);
    }

}
