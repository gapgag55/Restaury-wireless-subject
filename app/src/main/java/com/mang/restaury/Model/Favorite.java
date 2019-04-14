package com.mang.restaury.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Favorite extends RealmObject {

    private RealmList<Restaurant> restaurants;

    public RealmList<Restaurant> getRestaurants() {
        return restaurants;
    }
}
