package com.mang.restaury;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private TextView search_title;
    List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String keyword = getIntent().getExtras().getString("keyword");

        search_title = (TextView) findViewById(R.id.search_title);
        search_title.setText("Search: " + keyword);


        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));

        RecyclerView recycleView = (RecyclerView) findViewById(R.id.restaurant_cycle);
        RestaurantAdapter myAdapter = new RestaurantAdapter(this, restaurants);
        recycleView.setLayoutManager(new GridLayoutManager(this, 2));
        recycleView.setAdapter(myAdapter);

    }
}
