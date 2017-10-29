package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chats extends Fragment {

    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    TextView userName;

    public Chats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_chat, container, false);

        //setting the userName shandis
        userName = (TextView) rootView.findViewById(R.id.userName);
        layout = (LinearLayout) rootView.findViewById(R.id.layout1);

        sendButton = (ImageView)rootView.findViewById(R.id.sendButton);

        messageArea = (EditText) rootView.findViewById(R.id.messageArea);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

        // Make an arguments of bundles that will take in the userId of the seller from Profile3 or profileTab
        //The userName for textView
        final String sellerUId = getArguments().getString("userId"); //For now
        FirebaseDatabase.getInstance().getReference().child("Users").child(sellerUId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                userName.setText((String)map.get("name"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userName.setVisibility(View.VISIBLE);

        //Each user has their own copy of contact lists
        final String  buyerUId =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference buyer = FirebaseDatabase.getInstance().getReference().child("contactList").child(buyerUId).child(sellerUId);
        final DatabaseReference seller = FirebaseDatabase.getInstance().getReference().child("contactList").child(sellerUId).child(buyerUId);

        addUser(buyerUId,sellerUId);
        addUser(sellerUId,buyerUId);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                //Toast.makeText(getActivity(), FirebaseDatabase.getInstance().getReference().child("Users").toString(), Toast.LENGTH_SHORT).show();

               if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", buyerUId);
                    buyer.push().setValue(map);
                    seller.push().setValue(map);
                    messageArea.setText("");
                }

            }
        });

        buyer.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                //Map map = dataSnapshot.getValue(Map.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                final String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(userName)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Users").child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                            //String userName = (String) map.get("name");
                            addMessageBox((String) map.get("name") + ":-\n" + message, 2);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(getActivity()); //dunno what to do here
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{
            textView.setBackgroundResource(R.drawable.bubble_out);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    //need to write to both of ther contactList

    public void addUser(final String buyerUId, final String sellerUId){

        FirebaseDatabase.getInstance().getReference().child("userList").child(buyerUId).orderByChild("name").equalTo(sellerUId).addListenerForSingleValueEvent(new ValueEventListener() {
            boolean addingUser = false;
            HashMap<String,String> map1 = new HashMap<String, String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getActivity(),"dataSnapshots exists",Toast.LENGTH_LONG).show();
                HashMap<String,Object> map = (HashMap<String, Object>) dataSnapshot.getValue();

                DatabaseReference node;
                if (dataSnapshot.exists()) {
                    return;
                }

                node = FirebaseDatabase.getInstance().getReference().child("userList").child(buyerUId).push();
                //map1.put("key",node.getKey());
                map1.put("name", sellerUId);
                node.setValue(map1);
                //FirebaseDatabase.getInstance().getReference().child("userList").child(buyerUId).setValue(map1);
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

