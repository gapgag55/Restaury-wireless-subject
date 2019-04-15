package com.mang.restaury.Model;

public class Comment {

    private String dateTime;
    private String detail;
    private int rating;
    private int restaurantID;
    private String userId;

    public Comment(String dateTime, String detail, int rating, int restaurantID, String userId) {
        this.dateTime = dateTime;
        this.detail = detail;
        this.rating = rating;
        this.restaurantID = restaurantID;
        this.userId = userId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDetail() {
        return detail;
    }

    public int getRating() {
        return rating;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public String getUserId() {
        return userId;
    }
}
