package com.mang.restaury.Model;

import io.realm.RealmObject;

// Seem like Orderdetail in Firebase

public class CartItem extends RealmObject {

    private String menuID;
    private String sizeID;
    private String instruction;
    private int totalNumber;

    public CartItem() {}

    public CartItem(String menuID, String sizeID, String instruction, int totalNumber) {
        this.menuID = menuID;
        this.sizeID = sizeID;
        this.instruction = instruction;
        this.totalNumber = totalNumber;
    }

    public String getMenuID() {
        return menuID;
    }

    public String getSizeID() {
        return sizeID;
    }

    public String getInstruction() {
        return instruction;
    }

    public int getTotalNumber() {
        return totalNumber;
    }
}
