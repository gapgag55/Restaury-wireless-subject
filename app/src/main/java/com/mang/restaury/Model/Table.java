package com.mang.restaury.Model;

public class Table {
    public int restaurant_ID;
    public int table_id;
    public int table_seat;

    public Table(int restaurant_ID, int table_id, int table_seat) {
        this.restaurant_ID = restaurant_ID;
        this.table_id = table_id;
        this.table_seat = table_seat;

    }

    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public int getTable_id() {
        return table_id;
    }

    public int getTable_seat() {
        return table_seat;
    }
}
