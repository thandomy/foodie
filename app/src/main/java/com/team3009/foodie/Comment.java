package com.team3009.foodie;

/**
 * Created by Zanele on 28-Oct-17.
 */

public class Comment {

    public String  comment;
    public String  nName;
    public Comment(){

    }
    public Comment(String comment, String nName){
        this.comment = comment;
        this.nName = nName;
    }
    public String getComment(){
        return comment;
    }
    public String getnName(){
        return nName;
    }

}


