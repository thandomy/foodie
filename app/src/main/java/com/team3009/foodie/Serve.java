package com.team3009.foodie;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Thando Moyo on 20-Aug-17.
 */

public class Serve {

    String uid;
    String title;
    String description;
    Double latitude;
    Double longitude;
    String uri;

    public Serve(String uid, String title, String description, Double latitude, Double longitude, String uri) {
        this.latitude = latitude;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.uri=uri;
    }



}
