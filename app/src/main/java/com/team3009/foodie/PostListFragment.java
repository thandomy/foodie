package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;

import com.squareup.picasso.Picasso;


public class PostListFragment extends Fragment {
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Serve, PostViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_all_posts, container, false);
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference("Serving");
        // [END create_database_reference]
        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);



        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = mDatabase;
        mAdapter = new FirebaseRecyclerAdapter<Serve, PostViewHolder>(Serve.class, R.layout.item_post,
                PostViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, final Serve model, final int position) {
                viewHolder.title.setText(model.title);
                viewHolder.body.setText(model.description);
                viewHolder.price.setText(model.price.toString());
                Picasso.with(getActivity())
                        .load(model.downloadUrl)
                        .error(R.drawable.common_google_signin_btn_text_light_disabled)
                        .into(viewHolder.imageView);

            }
        };


        mRecycler.setAdapter(mAdapter);
        return rootView;
    }
}


