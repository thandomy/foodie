package com.team3009.foodie;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Zanele on 8/22/2017.
 */

public class Post {


    Double Latitude;
    Double Longitude;
    String uri_string;
    String TitleField;
    String BodyField ;


    public Post(String title, String body, Double latitude, Double longitude, String uri){
        post(title, body, latitude, longitude, uri);
    }

    public void post(String title, String body, Double latitude, Double longitude, String uri) {
        // Get the Firebase node to write the data to
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Serving").push();
        // Write an entry containing the location and payment data of the user to the Firebase node

        String userEmail = getUid();
        node.setValue(new Serve(userEmail,title,body,latitude,longitude,uri));


    }

    public String getUid() {

        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
