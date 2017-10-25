package com.team3009.foodie;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static android.R.attr.key;

public class Post {
    public Post(){

    }
    public void sendData(String title, String body,String ingre, Float latitude, Float longitude,String url,Float price) {
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Serving").push();
        final String key = node.getKey();
        System.out.println(key);
        String userId = getUid();
        node.setValue(new Serve(title,body, ingre,latitude,longitude, url, key, userId, price));
        final DatabaseReference mCustomerDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

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

        node.setValue(new Serve(title,body,ingre,latitude,longitude, url, key, userId,price));
    }

    public void sendComment(String comment){
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Comment").push();
        node.setValue(new Serve(comment));
    }

    public void sendRating(String rating){
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Rating").push();
        node.setValue(new Serve(rating));
    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    }

