package com.mang.restaury.Model;

public class Customize {

    private int sizeId;
    private String name;
    private int price;

    public Customize(int sizeId, String name, int price) {
        this.sizeId = sizeId;
        this.name = name;
        this.price = price;
    }

    public int getSizeId() {
        return sizeId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
