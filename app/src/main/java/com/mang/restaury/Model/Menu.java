package com.mang.restaury.Model;

public class Menu {

    private int menu_ID ;
    private String menuName;
    private double price;
    private String menu_pictureURL;
    private int restaurant_ID;

    public Menu(String menuName, Double price) {
        this.menuName = menuName;
        this.price = price;
    }

    public Menu(int menu_ID, String menuName, double price, String menu_pictureURL, int restaurant_ID){
        this.menu_ID = menu_ID;
        this.menuName = menuName;
        this.price = price;
        this.menu_pictureURL = menu_pictureURL;
        this.restaurant_ID = restaurant_ID;
    }

    public int getMenu_ID() {
        return menu_ID;
    }

    public void setMenu_ID(int menu_ID) {
        this.menu_ID = menu_ID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMenu_pictureURL() {
        return menu_pictureURL;
    }

    public void setMenu_pictureURL(String menu_pictureURL) {
        this.menu_pictureURL = menu_pictureURL;
    }

    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
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
