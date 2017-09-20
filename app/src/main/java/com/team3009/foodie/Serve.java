package com.team3009.foodie;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class Serve {
    String title;
    String description;
    Float latitude;
    Float longitude;
    String downloadUrl;


    public Serve(){

    }

    public Serve(String title, String description, Float latitude, Float longitude,String downloadUrl) {
        this.downloadUrl = downloadUrl;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("uid", uid);
        //result.put("author", author);
        result.put("title",title);
        result.put("description",description);
        //result.put("starCount", starCount);
        result.put("downloadUrl",downloadUrl);

        return result;
    }



}
