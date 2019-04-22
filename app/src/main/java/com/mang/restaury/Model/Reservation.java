package com.mang.restaury.Model;

public class Reservation {
    private String reservationSpacialRequest;
    private String reservationTimeDate;
    private String tableID;
    private String restaurantID;
    private String userID;


    public Reservation(String reservationSpacialRequest, String reservationTimeDate, String tableID, String restaurantID, String userID) {
        this.reservationSpacialRequest = reservationSpacialRequest;
        this.reservationTimeDate = reservationTimeDate;
        this.tableID = tableID;
        this.restaurantID = restaurantID;
        this.userID = userID;
    }

    public String getSpecialRequest() {
        return reservationSpacialRequest;
    }

    public String getDateTime() {
        return reservationTimeDate;
    }

    public String getTableId() {
        return tableID;
    }

    public String getRestaurantId() { return restaurantID; }

    public String getUserId() {
        return userID;
    }
}

