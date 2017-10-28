package com.team3009.foodie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Zanele on 28-Oct-17.
 */

public class HolderComment  extends RecyclerView.ViewHolder {

    public TextView comment;


    public HolderComment(View itemView) {

        super(itemView);
        comment = (TextView) itemView.findViewById(R.id.commentTxtView);


    }
}
