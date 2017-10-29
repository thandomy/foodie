package com.team3009.foodie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private ArrayList<Comment> commentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genre;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.nameComment);
            genre = (TextView)view.findViewById(R.id.commentTxtView);
        }
    }
    public CommentAdapter(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comment, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comment movie = commentList.get(position);
        holder.title.setText(movie.getnName());
        holder.genre.setText(movie.getnName());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}