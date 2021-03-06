package com.team3009.foodie;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;




public class PostListFragment extends Fragment {
    private static final int DROP_IN_REQUEST_CODE = 567;
    private static final String SANDBOX_TOKENIZATION_KEY = "sandbox_tmxhyf7d_dcpspy2brwdjr3qn";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Serve, PostViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_all_posts, container, false);

        final float[] lastLocation = getArguments().getFloatArray("lastLocation");

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
                //Toast.makeText(getActivity(),model.title,Toast.LENGTH_SHORT).show();
                viewHolder.title.setText(model.title);
                viewHolder.body.setText(model.description);
                viewHolder.price.setText(model.price.toString());
                Picasso.with(getActivity())
                        .load(model.downloadUrl)
                        .error(R.drawable.bt_ic_vaulted_venmo)
                        .into(viewHolder.imageView);
                /*FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("mealOrdered").equalTo(model.key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //if(dataSnapshot.exists()) {
                            //mRecycler.removeViewAt(viewHolder.getLayoutPosition());
                            //viewHolder.itemView.setVisibility(View.GONE);
                            //mRecycler.removeViewAt(position);
                          //  return;
                       // }
                        viewHolder.title.setText(model.title);
                        viewHolder.body.setText(model.description);
                        viewHolder.price.setText(model.price.toString());
                        Picasso.with(getActivity())
                                .load(model.downloadUrl)
                                .error(R.drawable.common_google_signin_btn_text_light_disabled)
                                .into(viewHolder.imageView);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

                final DatabaseReference postRef = getRef(position);
                final String postKey = postRef.getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivityForResult(getDropInRequest().getIntent(getActivity()), DROP_IN_REQUEST_CODE);
                        OrderFragment profile = new OrderFragment();
                        Bundle args = new Bundle();
                        args.putString("key",model.key);
                        args.putString("userId",model.userId);
                        args.putString("amount",model.price.toString());

                        args.putFloatArray("lastLocation", lastLocation);

                        profile.setArguments(args);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(((ViewGroup)(getView().getParent())).getId(), profile,profile.getTag()).addToBackStack("v")
                                .commit();
                    }
                });

            }
        };
        mRecycler.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DROP_IN_REQUEST_CODE) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // Send the result to Firebase
                //Post p = new Post();
                //p.sendData(result);
                // Show a message that the transaction was successful
                Toast.makeText(getActivity(), R.string.payment_succesful, Toast.LENGTH_LONG).show();
            }
        }
    }
    private DropInRequest getDropInRequest() {
        return new DropInRequest()
                // Use the Braintree sandbox for dev/demo purposes
                .clientToken(SANDBOX_TOKENIZATION_KEY)
                .requestThreeDSecureVerification(true)
                .collectDeviceData(true);
    }

}


