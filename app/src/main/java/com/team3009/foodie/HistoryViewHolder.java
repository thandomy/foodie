package com.team3009.foodie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AndrieX on 10/30/2017.
 */

public class HistoryViewHolder extends RecyclerView.ViewHolder  {
    public TextView mealname;
    public TextView seller;
    public TextView amount;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        mealname = (TextView) itemView.findViewById(R.id.mealname);
        seller = (TextView) itemView.findViewById(R.id.seller);
        amount = (TextView) itemView.findViewById(R.id.amount);
    }
}
