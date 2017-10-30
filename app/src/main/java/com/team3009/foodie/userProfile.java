package com.team3009.foodie;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;

/**
 * Created by AndrieX on 10/27/2017.
 */

public class userProfile {
    public String name;
    Map<String, Object> messages;
    //public String profileImageUrl;

    public userProfile(){

    }

    /*
    public userProfile(String name){
        this.name = name;
        //this.profileImageUrl = profileImageUrl;
    }*/

    public userProfile(String name, Map<String, Object> messages){
        this.name = name;
        this.messages = messages;
        //this.profileImageUrl = profileImageUrl;
    }
}
