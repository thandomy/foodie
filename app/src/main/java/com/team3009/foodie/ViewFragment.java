package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {


    private RecyclerView recyclerView;
    private DatabaseReference myref;
    public ViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_view, container, false);

        System.out.println("ghasdgd");

        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        myref= FirebaseDatabase.getInstance().getReference("Serving");
        FirebaseRecyclerAdapter<Blog,BlogViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<Blog,BlogViewHolder>(
                Blog.class,
                R.layout.individual_row,
                BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(model.getImage());
            }
        };
        recyclerView.setAdapter(recyclerAdapter);

        return  v;
    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView textView_title;
        TextView textView_decription;
        ImageView imageView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            textView_title = (TextView)itemView.findViewById(R.id.title);
            textView_decription = (TextView) itemView.findViewById(R.id.description);
            imageView=(ImageView)itemView.findViewById(R.id.image);
        }
        public void setTitle(String title)
        {
            textView_title.setText(title+"");
        }
        public void setDescription(String description)
        {
            textView_decription.setText(description);
        }
        public void setImage(String image)
        {
            Picasso.with(mView.getContext())
                    .load(image)
                    .into(imageView);
        }
    }


}
