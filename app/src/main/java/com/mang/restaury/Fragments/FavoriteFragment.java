package com.mang.restaury.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    List<Restaurant> restaurants;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        final Realm realm = Realm.getDefaultInstance();

        final RealmResults<Restaurant> favoritedRestaurants = realm.where(Restaurant.class).findAll();
        restaurants = favoritedRestaurants.subList(0, favoritedRestaurants.size());

        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.restaurant_cycle);
        RestaurantAdapter myAdapter = new RestaurantAdapter(view.getContext(), restaurants);
        recycleView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recycleView.setAdapter(myAdapter);

        return view;
    }

}
