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


public class CommentFragment extends Fragment {
    private EditText comnt;
    private Button submitComment;
    String comment, nameComment;
    Map singleUser;
    TextView mcommentField, nameCommentField;
    String userId;
    private static final int DROP_IN_REQUEST_CODE = 567;
    private static final String SANDBOX_TOKENIZATION_KEY = "sandbox_tmxhyf7d_dcpspy2brwdjr3qn";
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Serve, PostViewHolder_Comments> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.viewcomments, container, false);



       // comnt = (EditText) v.findViewById(R.id.add_comment);
        mDatabase = FirebaseDatabase.getInstance().getReference("Comment");
        mRecycler = (RecyclerView) v.findViewById(R.id.messages);


      /* submitComment.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               comment = comnt.getText().toString();

              Post p = new Post();
               p.sendComment(comment);
               mDatabase= FirebaseDatabase.getInstance().getReference("Comment");
           }
        });*/

        mRecycler.setHasFixedSize(true);
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);


        Query postsQuery = mDatabase;
        mAdapter = new FirebaseRecyclerAdapter<Serve, PostViewHolder_Comments>(Serve.class, R.layout.comment_fragment,
                PostViewHolder_Comments.class, postsQuery) {
            @Override
            protected void populateViewHolder(final PostViewHolder_Comments viewHolder, final Serve model, final int position) {

                viewHolder.comment.setText(model.comment);

            }
        };
        mRecycler.setAdapter(mAdapter);




        return v;
    }


 /*   public void viewComment( DatabaseReference userRef) {

        userRef.addValueEventListener(new ValueEventListener() {
                private String mcomment;

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        singleUser = (Map) entry.getValue();
                        //Get phone field and append to list
                        mcomment = singleUser.get("comment").toString();
                        mcommentField.setText(mcomment);

                    }
                }
            }
                @Override
                public void onCancelled (DatabaseError databaseError){


            }
        });
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void viewName (DatabaseReference userRef){

        userRef.addValueEventListener(new ValueEventListener() {
            private String mcomment;

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    if(map.get("name") != null){
                        mcomment= map.get("name").toString();
                        nameCommentField.setText(mcomment);
                    }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){


            }
        });

    }*/

}


