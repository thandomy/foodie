package com.team3009.foodie;






import java.util.HashMap;
import java.util.Map;



public class Serve {
    public String title;
    public String description;
    public Float latitude;
    public Float longitude;
    public String downloadUrl;


    public Serve(){

    }

    public Serve(String title, String description, Float latitude, Float longitude,String downloadUrl) {
        this.downloadUrl = downloadUrl;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;

    }


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
