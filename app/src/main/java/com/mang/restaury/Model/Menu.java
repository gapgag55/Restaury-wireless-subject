package com.mang.restaury.Model;

import io.realm.RealmObject;

public class Menu extends RealmObject  {

    private String menuID;
    private String menuName;
    private double price;
    private String restaurantID;

    public Menu() {
    }

    public Menu(String menuName, Double price) {
        this.menuName = menuName;
        this.price = price;
    }

    public Menu(String menuID, String menuName, double price, String restaurantID){
        this.menuID = menuID;
        this.menuName = menuName;
        this.price = price;
        this.restaurantID = restaurantID;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurant_ID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
