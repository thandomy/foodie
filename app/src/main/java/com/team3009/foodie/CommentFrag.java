package com.team3009.foodie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInResult;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class CommentFrag extends Fragment {
    private EditText comnt;
    private Button submitComment;
    String comment, commentStr, nameStr;
    Map singleUser;
    TextView mNameField, textViewComment;
    String userId;
    private static final int DROP_IN_REQUEST_CODE = 567;
    private static final String SANDBOX_TOKENIZATION_KEY = "sandbox_tmxhyf7d_dcpspy2brwdjr3qn";
    private DatabaseReference mDatabase, mDatabase2;

    private FirebaseRecyclerAdapter<Serve, HolderComment> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.veiwcomments, container, false);
        final String postedUserId = getArguments().getString("userId");

       String commentingUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();

       //but i cant have this here as these are not in veiwcomments

       textViewComment = (TextView) v.findViewById(R.id.commentTxtView);
        mNameField = (TextView) v.findViewById(R.id.nameComment);

         comnt = (EditText) v.findViewById(R.id.add_comment);
         submitComment = (Button) v.findViewById(R.id.submit_comment);
       submitComment.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               comment = comnt.getText().toString();
              Post p = new Post();
               p.sendComment(comment,postedUserId);
               mDatabase= FirebaseDatabase.getInstance().getReference("Comment");
           }
        });

//get the comment
        mDatabase = FirebaseDatabase.getInstance().getReference("Comments");


        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("comments") != null) {
                        commentStr = map.get("comments").toString();
                        textViewComment.setText(commentStr);
                    }
                }
            }
                @Override
                public void onCancelled (DatabaseError databaseError){

                }
            });


        //get the imagine
        mDatabase2 = FirebaseDatabase.getInstance().getReference("Users").child(commentingUserId);

        mDatabase2.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        nameStr = map.get("name").toString();
                        mNameField.setText(nameStr);
                    }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });





        mRecycler = (RecyclerView) v.findViewById(R.id.messages);
        mRecycler.setHasFixedSize(true);
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        Query postsQuery = mDatabase;

        mAdapter = new RecyclerAdapter<Serve, HolderComment>(Serve.class, R.layout.fragment_comment,
                HolderComment.class, postsQuery) {
            @Override
            protected void populateViewHolder(final HolderComment viewHolder, final Serve model, final int position) {

                viewHolder.comment.setText(model.comment);




            }
        };
        mRecycler.setAdapter(mAdapter);




        return v;
    }


}