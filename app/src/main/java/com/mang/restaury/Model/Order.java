package com.mang.restaury.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

// Seem like Order in Firebase
public class Order {

    private String userId;
    private Double orderTotalPrice;
    private String orderDateTime;
    private String orderAddress;
    private String orderPhone;

    public Order() {}

    public Order(String userId, Double totalPrice, String dateTime, String address, String phone) {
        this.userId = userId;
        this.orderTotalPrice = totalPrice;
        this.orderDateTime = dateTime;
        this.orderAddress = address;
        this.orderPhone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public String getOrderPhone() {
        return orderPhone;
    }
}
