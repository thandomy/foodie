package com.team3009.foodie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by AndrieX on 10/27/2017.
 */

public class userViewHolder extends RecyclerView.ViewHolder  {
    public ImageView userPic;
    public TextView userName;

    public userViewHolder(View view){
        super(view);
        userPic = (ImageView) view.findViewById(R.id.userPic);
        userName = (TextView) view.findViewById(R.id.userName);
    }
}
