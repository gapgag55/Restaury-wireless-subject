package com.mang.restaury.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Tree;
import com.mang.restaury.Adapter.MenuAdapter;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Menu;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class searchResultFragment extends Fragment {

    private ArrayList<Restaurant> restaurants;
    private TreeMap<String,Integer> foodtype = null;
    private TreeMap<Integer, Integer> stars = null;
    private TreeMap<String,Integer> minMax = null;



    String keyword;


    public searchResultFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public searchResultFragment(String keyword) {
        // Required empty public constructor
        this.keyword = keyword;
    }

    @SuppressLint("ValidFragment")
    public searchResultFragment(String keyword, TreeMap<String,Integer> foodtype, TreeMap<Integer, Integer> stars, TreeMap<String,Integer> minMax) {
        // Required empty public constructor
        this.keyword = keyword;
        this.foodtype = foodtype;
        this.stars = stars;
        this.minMax = minMax;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        restaurants = new ArrayList<>();

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference tableRef = ref.child("Restaurant");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String restaurantAbout = ds.child("restaurantAbout").getValue(String.class);
                    Double restaurantDeliverFee = ds.child("restaurantDeliverFee").getValue(Double.class);
                    Double restaurantLatitute = ds.child("restaurantLatitute").getValue(Double.class);
                    Double restaurantLongtitute = ds.child("restaurantLongtitute").getValue(Double.class);
                    String restaurantName = ds.child("restaurantName").getValue(String.class);
                    Integer restaurantStar = ds.child("restaurantStar").getValue(Integer.class);
                    String restaurantType = ds.child("restaurantType").getValue(String.class);


                    restaurants.add(new Restaurant(restaurantName,
                            restaurantLatitute,
                            restaurantLongtitute,
                            ds.getKey(),
                            restaurantAbout,
                            restaurantDeliverFee,
                            null,
                            restaurantStar,
                            restaurantType));
                }

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();
                DatabaseReference tableRef = ref.child("RestaurantPicture");

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            String restaurantID = ds.child("restaurantID").getValue(String.class);
                            String restaurantPictureURL = ds.child("restaurantPictureURL").getValue(String.class);
                            for(Restaurant r : restaurants){
                                if(r.getRestaurantID().equals(restaurantID)){
                                    r.setPicture(restaurantPictureURL);
                                }
                            }
                        }

                        final ArrayList<Restaurant> filteredRestaurant = new ArrayList<>();

                        for(Restaurant r : restaurants){
                            if(!r.getTitle().toLowerCase().contains(keyword.toLowerCase()))continue;

                            if(stars != null){
                                Log.d("dad",Math.floor(r.getStar())+"");
                                if(stars.get((int)Math.floor(r.getStar()))==0)continue;
                            }
                            filteredRestaurant.add(r);
                        }



                        RecyclerView recycleView = (RecyclerView) view.findViewById (R.id.restaurant_cycle);
                        RestaurantAdapter myAdapter = new RestaurantAdapter(view.getContext(), filteredRestaurant);
                        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        recycleView.setAdapter(myAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                tableRef.addListenerForSingleValueEvent(eventListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        tableRef.addListenerForSingleValueEvent(eventListener);

        return view;
    }

}
