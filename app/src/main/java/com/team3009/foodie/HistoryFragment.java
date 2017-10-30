package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }

    private FirebaseRecyclerAdapter<History, HistoryViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        final Query postsQuery = FirebaseDatabase.getInstance().getReference("Orders").orderByChild("Id")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mAdapter = new FirebaseRecyclerAdapter<History, HistoryViewHolder>(History.class, R.layout.history_item,
                HistoryViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final HistoryViewHolder viewHolder, final History model, int position) {
                Toast.makeText(getActivity(),model.mealOrdered,Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference()
                        .child("Serving").child(model.mealOrdered).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String,Object> map;

                        if(dataSnapshot.exists()){
                            map = (HashMap<String, Object>) dataSnapshot.getValue();
                            //viewHolder.seller.setText(map.get("userId").toString());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(map.get("userId").toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                HashMap<String,Object> map1;
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        map1 = (HashMap<String, Object>) dataSnapshot.getValue();
                                        viewHolder.seller.setText(map1.get("name").toString());
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            viewHolder.mealname.setText(map.get("title").toString());
                            viewHolder.amount.setText(map.get("price").toString());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        mRecycler.setAdapter(mAdapter);
        return rootView;
    }

}
