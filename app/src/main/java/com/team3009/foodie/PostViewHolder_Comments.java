package com.team3009.foodie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Zanele on 27-Oct-17.
 */

public class PostViewHolder_Comments extends RecyclerView.ViewHolder {
        public TextView comment;



        public PostViewHolder_Comments(View itemView) {
            super(itemView);
            comment = (TextView) itemView.findViewById(R.id.commentTxtView);



    }
}
