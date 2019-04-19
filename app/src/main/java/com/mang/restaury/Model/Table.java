package com.mang.restaury.Model;

public class Table {
    public int restaurantID;
    public int tableID;
    public int tableSeat;

    public Table(int restaurantID, int tableID, int tableSeat) {
        this.restaurantID = restaurantID;
        this.tableID = tableID;
        this.tableSeat = tableSeat;

    }

    public int getRestaurant_ID() {
        return restaurantID;
    }

    public int getTable_id() {
        return tableID;
    }

    public int getTable_seat() {
        return tableSeat;
    }
}
