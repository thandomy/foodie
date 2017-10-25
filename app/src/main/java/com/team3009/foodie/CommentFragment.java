package com.team3009.foodie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class CommentFragment extends Fragment {
    private EditText comnt;
    private Button submitComment;
    String comment, nameComment;
    Map singleUser;
    TextView mcommentField, nameCommentField;
    String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         View v = inflater.inflate(R.layout.comment_fragment, container, false);

        mcommentField = (TextView) v.findViewById(R.id.commentTxtView);
        comnt = (EditText) v.findViewById(R.id.add_comment);
        nameCommentField = (TextView) v.findViewById(R.id.nameComment);
        submitComment = (Button) v.findViewById(R.id.submit_comment);

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = comnt.getText().toString();

                Post p = new Post();
                p.sendComment(comment);
                final  DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Comment");
                userId = getUid();
                final DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                viewComment(userRef);
                viewName(currentUser);


            }
        });

        return v;
    }


    public void viewComment( DatabaseReference userRef) {

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

    }

}


