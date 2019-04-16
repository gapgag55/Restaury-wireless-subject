package com.mang.restaury.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Cart extends RealmObject {
    private RealmList<Menu> menus;

    public RealmList<Menu> getRestaurants() {
        return menus;
    }
}
