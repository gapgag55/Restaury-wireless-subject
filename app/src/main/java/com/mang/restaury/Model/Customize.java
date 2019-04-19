package com.mang.restaury.Model;

public class Customize {

    private String sizeId;
    private String name;
    private int price;

    public Customize(String sizeId, String name, int price) {
        this.sizeId = sizeId;
        this.name = name;
        this.price = price;
    }

    public String getSizeId() {
        return sizeId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
