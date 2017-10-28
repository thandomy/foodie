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
        //TextView textView = new TextView(getActivity());
        //textView.setText(R.string.hello_blank_fragment);
        View rootView = inflater.inflate(R.layout.activity_chat, container, false);

        //setting the userName shandis
        userName = (TextView) rootView.findViewById(R.id.userName);
        layout = (LinearLayout) rootView.findViewById(R.id.layout1);

        sendButton = (ImageView)rootView.findViewById(R.id.sendButton);

        messageArea = (EditText) rootView.findViewById(R.id.messageArea);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

        // Make an arguments of bundles that will take in the userId of the seller from Profile3 or profileTab
        final String sellerUId = getArguments().getString("userId"); //For now
        userName.setText(getUserName(sellerUId));
        userName.setVisibility(View.VISIBLE);

        //Each user has their own copy of contact lists
        final String  buyerUId =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference buyer = FirebaseDatabase.getInstance().getReference().child("contactList").child(buyerUId).child(sellerUId);
        final DatabaseReference seller = FirebaseDatabase.getInstance().getReference().child("contactList").child(sellerUId).child(buyerUId);

        /*
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", sellerUId);
        FirebaseDatabase.getInstance().getReference().child("userList").child(buyerUId).push().setValue(map);

        map = new HashMap<String, String>();
        map.put("name", buyerUId);
        FirebaseDatabase.getInstance().getReference().child("userList").child(sellerUId).push().setValue(map);
        */
        addUser(buyerUId,sellerUId);
        addUser(sellerUId,buyerUId);

        //writeContact(buyer,sellerUId);
        //writeContact(seller,buyerUId);

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
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(buyerUId)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(getUserName(sellerUId) + ":-\n" + message, 2);
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
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    //need to write to both of ther contactList

    public void writeContact(final DatabaseReference ref, final String otherContact){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                ref.push();
                ref.setValue(otherContact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getUserName(final String userId){
        String userName = null;
        FirebaseDatabase.getInstance().getReference().child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                String userName = (String) map.get("name");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return userName;
    }

    public void addUser(final String buyerUId, final String sellerUId){
        FirebaseDatabase.getInstance().getReference().child("userList").child(buyerUId).addListenerForSingleValueEvent(new ValueEventListener() {
            boolean addingUser = false;
            HashMap<String,String> map1 = new HashMap<String, String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(getActivity(),"dataSnapshots exists",Toast.LENGTH_LONG).show();
                    List<Object> map = (List<Object>) dataSnapshot.getValue();
                    if(!map.isEmpty()){
                        Toast.makeText(getActivity(),"map is not empty ",Toast.LENGTH_LONG).show();
                    }
                    if(map==null){
                        map1.put("name", sellerUId);
                        FirebaseDatabase.getInstance().getReference().child("userList").child(buyerUId).push().setValue(map);
                        return;
                    }else{
                        String u = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        for(Object l:map){
                            if(!l.toString().equals(u)){
                                Toast.makeText(getActivity(),"list user names = "+l,Toast.LENGTH_LONG).show();
                                addingUser=true;
                                if(addingUser){
                                    map1 = new HashMap<String, String>();
                                    map1.put("name", l.toString());
                                    FirebaseDatabase.getInstance().getReference().child("userList").child(buyerUId).push().setValue(map);
                                }
                                break;
                            }
                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
