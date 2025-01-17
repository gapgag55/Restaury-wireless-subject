package com.mang.restaury.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Activity.SearchActivity;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.Activity.SearchActivity;

import java.util.ArrayList;
import java.util.TreeMap;

import static android.support.constraint.Constraints.TAG;


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
                    String restaurantURL = ds.child("restaurantURL").getValue(String.class);

                    restaurants.add(new Restaurant(restaurantName,
                            restaurantLatitute,
                            restaurantLongtitute,
                            ds.getKey(),
                            restaurantAbout,
                            restaurantDeliverFee,
                            null,
                            restaurantStar,
                            restaurantType,
                            restaurantURL));
                }

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();
                final DatabaseReference tableRef = ref.child("RestaurantPicture");

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

                        final TreeMap<String,Integer[]> resRange =  new TreeMap<String,Integer[]>();
                        final TreeMap<String,Restaurant> filteredRestaurant2 = new TreeMap<String,Restaurant>();

                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference();
                        Query menuRef = ref.child("Menu");

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String resID = ds.child("restaurantID").getValue(String.class);
                                    for(Restaurant r : restaurants){
                                        if(r.getRestaurantID().equals(resID)){
                                            Integer[] minMax;
                                            if(!resRange.containsKey(resID)){

                                                minMax = new Integer[2];
                                                minMax[0] = ds.child("menuBasePrice").getValue(Integer.class);
                                                minMax[1] = ds.child("menuBasePrice").getValue(Integer.class);

                                            }else{
                                                minMax = resRange.get(resID);
                                                int price = ds.child("menuBasePrice").getValue(Integer.class);
                                                if(price<minMax[0]) minMax[0] = price;
                                                if(price>minMax[1]) minMax[1] = price;

                                                Log.d("minMax",minMax[0]+" "+minMax[1]);

                                            }
                                            resRange.put(resID,minMax);
                                            filteredRestaurant2.put(r.getRestaurantID(),r);


                                        }
                                    }
                                }

                                ArrayList<Restaurant> fFilter = new ArrayList<>();
                                for(Restaurant r : filteredRestaurant2.values()){
                                    String resID = r.getRestaurantID();
                                    Integer[] minMax = resRange.get(resID);
                                    r.setMinPrice(minMax[0]);
                                    r.setMaxPrice(minMax[1]);
                                    fFilter.add(r);
                                }

                                RecyclerView recycleView = (RecyclerView) view.findViewById (R.id.restaurant_cycle);
                                RestaurantAdapter myAdapter = new RestaurantAdapter(view.getContext(), fFilter);
                                recycleView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                                recycleView.setAdapter(myAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        menuRef.addListenerForSingleValueEvent(eventListener);


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