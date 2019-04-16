package com.mang.restaury.Model;

import io.realm.RealmObject;

// Seem like Orderdetail in Firebase

public class CartItem extends RealmObject {

    private int menuID;
    private int sizeID;
    private String instruction;
    private int totalNumber;

    public CartItem() {}

    public CartItem(int menuID, int sizeID, String instruction, int totalNumber) {
        this.menuID = menuID;
        this.sizeID = sizeID;
        this.instruction = instruction;
        this.totalNumber = totalNumber;
    }

    public int getMenuID() {
        return menuID;
    }

    public int getSizeID() {
        return sizeID;
    }

    public String getInstruction() {
        return instruction;
    }

    public int getTotalNumber() {
        return totalNumber;
    }
}
