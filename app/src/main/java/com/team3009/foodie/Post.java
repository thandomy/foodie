package com.team3009.foodie;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team3009.foodie.Serve;

/**
 * Created by Zanele on 8/22/2017.
 */

public class Post {


    Double Latitude;
    Double Longitude;
    String uri_string;
    String TitleField;
    String BodyField ;

   /* public Post(String TitleField,String BodyField, Double Latitude,Double Longitude,String uri_string){
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.uri_string = uri_string;
        this.TitleField = TitleField;
        this.BodyField = BodyField;
    }*/




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
