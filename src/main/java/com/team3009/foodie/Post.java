package com.team3009.foodie;


import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.team3009.foodie.Serve;



public class Post {

    String key;
    String userId;
    Context context;

    String title;
    String description;
    Float latitude;
    Float longitude;
    String downloadUrl;



    public Post(){

    }
    public Post(String title, String description, Float latitude, Float longitude,String downloadUrl){
        this.downloadUrl = downloadUrl;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public void sendData() {
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Serve").push();


        node.setValue(new Serve(title,description,latitude,longitude, userId));


    }



    public void resendData(String rating) {
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Rating").push();
        node.setValue(rating);

    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
