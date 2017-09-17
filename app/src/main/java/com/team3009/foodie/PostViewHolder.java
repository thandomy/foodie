package com.team3009.foodie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageView imageView;
    public TextView body;
    public TextView location;

    public PostViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.post_title);
        body = (TextView) itemView.findViewById(R.id.post_body);
        location = (TextView) itemView.findViewById(R.id.location);
        imageView = (ImageView) itemView.findViewById(R.id.star);
    }

}
