package com.mang.restaury.Model;

import io.realm.RealmObject;

public class Restaurant extends RealmObject {

    private String restaurantID;
    private String restaurantAbout;
    private double restaurantDeliverFee;
    private String title;
    private double latitude;
    private double longtitude;
    private String picture;


    public Restaurant() {
    }

    public Restaurant(String title, double latitude, double longtitude, String restaurantID, String restaurantAbout, double restaurantDeliverFee, String picture) {
        this.title = title;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.restaurantAbout = restaurantAbout;
        this.restaurantID = restaurantID;
        this.restaurantDeliverFee = restaurantDeliverFee;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Restaurant(String title, double latitude, double longtitude) {
        this.title = title;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantAbout() {
        return restaurantAbout;
    }

    public void setRestaurantAbout(String restaurantAbout) {
        this.restaurantAbout = restaurantAbout;
    }

    public double getRestaurantDeliverFee() {
        return restaurantDeliverFee;
    }

    public void setRestaurantDeliverFee(double restaurantDeliverFee) {
        this.restaurantDeliverFee = restaurantDeliverFee;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
