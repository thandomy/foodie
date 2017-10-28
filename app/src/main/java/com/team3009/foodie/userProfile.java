package com.team3009.foodie;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;

/**
 * Created by AndrieX on 10/27/2017.
 */

public class userProfile {
    public String name;
    public Map<String, String> messages;
    //public String profileImageUrl;

    public userProfile(){

    }

    public userProfile(String name, Map<String, String> messages){
        this.name = name;
        this.messages = messages;
        //this.profileImageUrl = profileImageUrl;
    }
}
