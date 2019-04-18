package com.mang.restaury.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

// Seem like Order in Firebase
public class Cart extends RealmObject {

    private String userId;
    private Double totalPrice;
    private String dateTime;
    private String address;
    private int restaurantId;
    private RealmList<CartItem> cartItems;

    public Cart() {}

    public Cart(String userId, Double totalPrice, String dateTime, String address, int restaurantId, RealmList<CartItem> cartItems) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.dateTime = dateTime;
        this.address = address;
        this.restaurantId = restaurantId;
        this.cartItems = cartItems;
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

    public int getRestaurantId() {
        return restaurantId;
    }

    public RealmList<CartItem> getCartItems() {
        return cartItems;
    }
}
