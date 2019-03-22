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

import java.util.ArrayList;
import java.util.List;


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

        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));

        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.restaurant_cycle);
        RestaurantAdapter myAdapter = new RestaurantAdapter(view.getContext(), restaurants);
        recycleView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recycleView.setAdapter(myAdapter);

        return view;
    }

}
