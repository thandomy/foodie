package com.team3009.foodie;


import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team3009.foodie.Serve;

import java.util.Map;


public class Post {


    public Post(){

    }

    public void sendData(String title, String body, Float latitude, Float longitude,String url) {
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Serving").push();
        final String key = node.getKey();
        String userId = getUid();
        final DatabaseReference mCustomerDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        /*ValueEventListener valueEventListener = mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("foodId")==null){
                        map.put("foodId",key);
                        mCustomerDatabase.updateChildren(map);
                    }else{
                        DatabaseReference foodId = mCustomerDatabase.child("foodId").push();
                        foodId.setValue(key);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mCustomerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("foodId")==null){
                        map.put("foodId",key);
                        mCustomerDatabase.updateChildren(map);
                    }else{
                        DatabaseReference foodId = mCustomerDatabase.child("foodId").push();
                        foodId.setValue(key);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        node.setValue(new Serve(title,body,latitude,longitude, url, key, userId));
    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
