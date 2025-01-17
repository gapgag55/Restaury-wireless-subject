package com.mang.restaury.Model;

import io.realm.RealmObject;

// Seem like Orderdetail in Firebase

public class CartItem extends RealmObject {

    private String menuID; // MenuID can get restaurantID
    private String ingredientID;
    private String menuName;
    private String sizeID;
    private String instruction;
    private double totalPrice;
    private int totalNumber;

    public CartItem() {}

    public CartItem(String menuID, String ingredientID, String menuName, String sizeID, String instruction, double totalPrice, int totalNumber) {
        this.menuID = menuID;
        this.ingredientID = ingredientID;
        this.menuName = menuName;
        this.sizeID = sizeID;
        this.instruction = instruction;
        this.totalPrice = totalPrice;
        this.totalNumber = totalNumber;
    }

    public String getMenuID() {
        return menuID;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getSizeID() {
        return sizeID;
    }

    public String getInstruction() {
        return instruction;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }
}
