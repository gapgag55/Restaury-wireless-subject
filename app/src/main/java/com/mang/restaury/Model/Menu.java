package com.mang.restaury.Model;

import io.realm.RealmObject;

public class Menu extends RealmObject  {

    private String menu_ID ;
    private String menuName;
    private double price;
    private String restaurant_ID;

    public Menu() {
    }

    public Menu(String menuName, Double price) {
        this.menuName = menuName;
        this.price = price;
    }

    public Menu(String menu_ID, String menuName, double price, String restaurant_ID){
        this.menu_ID = menu_ID;
        this.menuName = menuName;
        this.price = price;
        this.restaurant_ID = restaurant_ID;
    }

    public String getMenu_ID() {
        return menu_ID;
    }

    public void setMenu_ID(String menu_ID) {
        this.menu_ID = menu_ID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(String restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
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
