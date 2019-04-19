package com.mang.restaury.Model;

import io.realm.RealmObject;

// Seem like Orderdetail in Firebase

public class CartItem extends RealmObject {

    private String menuID; // MenuID can get restaurantID
    private String menuName;
    private String sizeID;
    private String instruction;
    private int totalPrice;
    private int totalNumber;

    public CartItem() {}

    public CartItem(String menuID, String menuName, String sizeID, String instruction, int totalPrice, int totalNumber) {
        this.menuID = menuID;
        this.menuName = menuName;
        this.sizeID = sizeID;
        this.instruction = instruction;
        this.totalPrice = totalPrice;
        this.totalNumber = totalNumber;
    }

    public String getMenuID() {
        return menuID;
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalNumber() {
        return totalNumber;
    }
}
