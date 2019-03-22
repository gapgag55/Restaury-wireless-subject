package com.mang.restaury.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.SearchActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private ArrayList<Restaurant> restaurants;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        final EditText search = (EditText) view.findViewById(R.id.search_input);


        restaurants = new ArrayList<>();
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


        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Intent intent = new Intent(view.getContext(), SearchActivity.class);
                    intent.putExtra("keyword", search.getText().toString());
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });

        return view;
    }

}
