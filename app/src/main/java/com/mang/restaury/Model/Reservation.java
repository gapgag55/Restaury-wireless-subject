package com.mang.restaury.Model;

public class Reservation {
    private String reservation_spacialRequest;
    private String reservation_timeDate;
    private int table_ID;
    private int user_ID;


    public Reservation(String reservation_spacialRequest, String reservation_timeDate, int table_ID, int user_ID) {
        this.reservation_spacialRequest = reservation_spacialRequest;
        this.reservation_timeDate = reservation_timeDate;
        this.table_ID = table_ID;
        this.user_ID = user_ID;
    }

    public String getSpecialRequest() {
        return reservation_spacialRequest;
    }

    public String getDateTime() {
        return reservation_timeDate;
    }

    public int getTableId() {
        return table_ID;
    }

    public int getUserId() {
        return user_ID;
    }
}
