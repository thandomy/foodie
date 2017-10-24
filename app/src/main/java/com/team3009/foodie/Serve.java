package com.team3009.foodie;
public class Serve {
    public String title;
    public String description;
    public Float latitude;
    public Float longitude;
    public String downloadUrl;
    public String ingredients;
    public Float price;
    public String userId;
    public String key;
    public String comment;
    public Serve(){

    }

    public Serve(String comment){
        this.comment = comment;

    }

    public Serve(String title, String description, String ingredients, Float latitude, Float longitude,String downloadUrl,String  key,String  userId, Float price) {
        this.downloadUrl = downloadUrl;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ingredients = ingredients;
        this.price = price;
        this.userId = userId;
        this.key = key;
    }
}
