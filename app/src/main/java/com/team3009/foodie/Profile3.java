package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;

import com.squareup.picasso.Picasso;



/**
 * A simple {@link Fragment} subclass.
 */
public class Profile2 extends Fragment {
    String key;

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Serve, Profile> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public Profile2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false);
        View rootView = inflater.inflate(R.layout.fragment_all_posts, container, false);
        // [START create_database_reference]
        String key = getArguments().getString("key");

        mDatabase = FirebaseDatabase.getInstance().getReference("Serving").child("key");
        Toast.makeText(getActivity(),"1 "+key, Toast.LENGTH_LONG).show();

        // [END create_database_reference]
        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        Query postsQuery = mDatabase;
        mAdapter = new FirebaseRecyclerAdapter<Serve, Profile>(Serve.class, R.layout.fragment_profile,
                Profile.class, postsQuery) {
            @Override
            protected void populateViewHolder(final Profile viewHolder, final Serve model, final int position) {
                viewHolder.userName.setText(model.title);
                viewHolder.foodDescr.setText(model.description);
                //viewHolder.location.setText(model.latitude + " , " +model.longitude);
                //add the others when available
                Picasso.with(getActivity())
                        .load(model.downloadUrl)
                        .error(R.drawable.common_google_signin_btn_text_light_disabled)
                        .into(viewHolder.foodie);

            }

        };
        mRecycler.setAdapter(mAdapter);
        return rootView;
    }

}
