package com.mang.restaury.Model;

public class Restaurant {

    private int restaurant_ID;
    private String restaurant_about;
    private int restaurant_deliverFee;
    private String title;
    private double latitude;
    private double longtitude;
    private String picture;


    public Restaurant() {
    }

    public Restaurant(String title, double latitude, double longtitude, int restaurant_ID, String restaurant_about, int restaurant_deliverFee,String picture) {
        this.title = title;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.restaurant_about = restaurant_about;
        this.restaurant_ID = restaurant_ID;
        this.restaurant_deliverFee = restaurant_deliverFee;
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

    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
    }

    public String getRestaurant_about() {
        return restaurant_about;
    }

    public void setRestaurant_about(String restaurant_about) {
        this.restaurant_about = restaurant_about;
    }

    public int getRestaurant_deliverFee() {
        return restaurant_deliverFee;
    }

    public void setRestaurant_deliverFee(int restaurant_deliverFee) {
        this.restaurant_deliverFee = restaurant_deliverFee;
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
