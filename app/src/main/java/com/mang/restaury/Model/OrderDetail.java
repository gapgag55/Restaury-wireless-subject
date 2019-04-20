package com.mang.restaury.Model;

public class OrderDetail {

    private String ingredientID;
    private String menuID; // MenuID can get restaurantID
    private String orderDetailRequest;
    private String orderID;
    private String sizeID;
    private int totalNumber;

    public OrderDetail(String ingredientID, String menuID, String orderDetailRequest, String orderID, String sizeID, int totalNumber) {
        this.ingredientID = ingredientID;
        this.menuID = menuID;
        this.orderDetailRequest = orderDetailRequest;
        this.orderID = orderID;
        this.sizeID = sizeID;
        this.totalNumber = totalNumber;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public String getMenuID() {
        return menuID;
    }

    public String getOrderDetailRequest() {
        return orderDetailRequest;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getSizeID() {
        return sizeID;
    }

    public int getTotalNumber() {
        return totalNumber;
    }
}
