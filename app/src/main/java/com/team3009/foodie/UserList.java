package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserList extends Fragment {


    public UserList() {
        // Required empty public constructor
    }

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<userProfile, userViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.user_list, container, false);
        final String thisUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mRecycler = (RecyclerView) rootView.findViewById(R.id.allUsers);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);



        mDatabase = FirebaseDatabase.getInstance().getReference();

        final Query postsQuery = FirebaseDatabase.getInstance().getReference("UserList").child(thisUserId).orderByKey();
        mAdapter = new FirebaseRecyclerAdapter<userProfile , userViewHolder>(userProfile.class, R.layout.user_item,
                userViewHolder.class, postsQuery) {

            @Override
            protected void populateViewHolder(final userViewHolder viewHolder, final userProfile model, int position) {




                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(model.name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            if(map.get("name")!=null) {
                                viewHolder.userName.setText(map.get("name").toString());
                            }
                            if(map.get("profileImageUrl")!= null){
                                Picasso.with(getActivity()).load(map.get("profileImageUrl").toString())
                                        .error(R.drawable.common_google_signin_btn_icon_dark)
                                        .into(viewHolder.userPic);

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                viewHolder.userPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        profileTab chats = new profileTab();
                        Bundle args = new Bundle();
                        args.putString("userId", model.name);

                        chats.setArguments(args);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(((ViewGroup)(getView().getParent())).getId(), chats,chats.getTag()).addToBackStack("v")
                                .commit();
                    }
                });

                viewHolder.userName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Chats chats = new Chats();
                        Bundle args = new Bundle();
                        args.putString("userId", model.name);

                        chats.setArguments(args);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(((ViewGroup)(getView().getParent())).getId(), chats,chats.getTag()).addToBackStack("v")
                                .commit();
                    }
                });

            }

        };
        mRecycler.setAdapter(mAdapter);
        return rootView;
    }

}
