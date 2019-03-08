package com.mang.restaury.Model;

public class Restaurant {

    private String title;
    private double latitude;
    private double longtitude;


    public Restaurant() {
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
