package com.team3009.foodie;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post {
    public Post(){

    }
    public void sendData(String title, String body,String ingre, Float latitude, Float longitude,String url,Float price) {
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Serving").push();
        node.setValue(new Serve(title,body, ingre,latitude,longitude, url, price));
    }
}
