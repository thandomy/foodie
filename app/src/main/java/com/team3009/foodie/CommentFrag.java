package com.team3009.foodie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.Map;

public class CommentFrag extends Fragment {
    private EditText comnt;
    private Button submitComment;
    String comment, commentStr, nameStr;
    TextView mNameField, textViewComment;

    private ArrayList<Comment> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommentAdapter mAdapter;
    private DatabaseReference mDatabase, mDatabase2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.veiwcomments, container, false);
        final String postedUserId = getArguments().getString("userId");

       String commentingUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();


        recyclerView = (RecyclerView) v.findViewById(R.id.messages);

        mAdapter = new CommentAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



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
               mDatabase = FirebaseDatabase.getInstance().getReference("Comments");
               //System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP "+mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()));
           }
        });

//get the comment
        mDatabase = FirebaseDatabase.getInstance().getReference("Comments");





        //get the imagine
        mDatabase2 = FirebaseDatabase.getInstance().getReference("Users").child(commentingUserId);

        mDatabase2.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        nameStr = map.get("name").toString();
                    }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });


        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("comment") != null) {
                        commentStr = map.get("comment").toString();
                        System.out.println("ASSSJK:BBOJDB HHHHHHVOEUEHFJLE FJNEN ND NJD ND: "+nameStr +" " + commentStr);
                        movieList.add( new Comment(nameStr,commentStr));
                        mAdapter.notifyDataSetChanged();


                    }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });



        return v;
    }

   // public void prepareMovieData(Comment c){}


}