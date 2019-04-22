package com.mang.restaury.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

// Seem like Order in Firebase
public class Cart extends RealmObject {

    private String userId;
    private Double totalPrice;
    private String dateTime;
    private String address;
    private String phone;

    public Cart() {}

    public Cart(String userId, Double totalPrice, String dateTime, String addressm, String phone) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.dateTime = dateTime;
        this.address = address;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
