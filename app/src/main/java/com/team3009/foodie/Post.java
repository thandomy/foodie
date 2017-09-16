package com.team3009.foodie;


import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.team3009.foodie.Serve;



public class Post {


    public Post(){

    }

    public void sendData(String title, String body, Float latitude, Float longitude,String url) {
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Serving").push();
        String userId = getUid();
        node.setValue(new Serve(title,body,latitude,longitude, url));
    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
