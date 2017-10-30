package com.team3009.foodie;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by clubless on 2017/10/16.
 */

public class Order {
    String Id;
    String mealOrdered;

    public Order(){

    }

    public Order(String mealOrdered, String Id){
        this.mealOrdered = mealOrdered;
        //this.mealPosted = post;
        this.Id = Id;
    }

    public void sendData(String mealOrder) {
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Orders").push();
        ///final String key = node.getKey();
        String userId = getUid();
        node.setValue(new Order(mealOrder,userId));
        //final DatabaseReference mCustomerDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        //node.setValue(new Order(mealOrder,userId));
    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
