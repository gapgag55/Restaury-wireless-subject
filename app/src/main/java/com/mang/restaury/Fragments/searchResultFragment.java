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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Tree;
import com.mang.restaury.Adapter.MenuAdapter;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Menu;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;

import java.lang.reflect.Array;
import java.net.Inet4Address;
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
    private TreeMap<String,Integer> minMaxc = null;



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
        this.minMaxc = minMax;
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


                            if(foodtype!=null){
                                int match = 0;
                                String[] foodtypeR = r.getType().split(",");
                                Log.d("test"," "+foodtypeR[0]+r.getTitle());
                                for(int i = 0; i < foodtypeR.length;i++){
                                    Log.d("test2"," "+foodtypeR[i]+" "+(foodtype.get(foodtypeR[i])==0));
                                    if((int)foodtype.get(foodtypeR[i])==1){
                                        match = 1;
                                        break;
                                    };
                                }
                                if(match == 0) continue;
                            }


                            if(stars!=null){
                                Log.d("dad",Math.floor(r.getStar())+"");

                                if(stars.get((int)Math.floor(r.getStar()))==0)continue;
                            }

                            filteredRestaurant.add(r);
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
                                    for(Restaurant r : filteredRestaurant){
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

                                            if(minMaxc!=null){
                                                Integer min = minMaxc.get("min");
                                                Integer max = minMaxc.get("max");
                                                int price = ds.child("menuBasePrice").getValue(Integer.class);
                                                if(!filteredRestaurant2.containsKey(r.getRestaurantID())){
                                                    if(price>=min&&price<=max)filteredRestaurant2.put(r.getRestaurantID(),r);
                                                }
                                            }else{
                                                filteredRestaurant2.put(r.getRestaurantID(),r);
                                            }

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

        return view;
    }

}
