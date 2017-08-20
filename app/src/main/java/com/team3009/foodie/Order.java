package com.team3009.foodie;

//import com.braintreepayments.api.dropin.DropInResult;

public class Order {

    String result;
    Double latitude;
    Double longitude;

    public Order(String result, Double latitude, Double longitude) {
        this.result = result;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
