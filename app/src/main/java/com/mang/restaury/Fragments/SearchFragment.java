package com.mang.restaury.Fragments;


import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Activity.SearchActivity;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.Activity.SearchActivity;

import java.util.ArrayList;

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

                    int restaurantID = ds.child("restaurant_ID").getValue(int.class);
                    String restaurantAbout = ds.child("restaurant_about").getValue(String.class);
                    int restaurantDeliverFee = ds.child("restaurant_deliverFee").getValue(int.class);
                    double restaurantLatitute = ds.child("restaurant_latitute").getValue(double.class);
                    double restaurantLongtitute = ds.child("restaurant_longtitute").getValue(double.class);
                    String restaurantName = ds.child("restaurant_name").getValue(String.class);
                    String picture = ds.child("restaurant_pic").getValue(String.class);

                    Log.d(TAG,restaurantID+"/"+restaurantAbout+picture);

                    restaurants.add(new Restaurant(restaurantName,
                            restaurantLatitute,
                            restaurantLongtitute,
                            restaurantID,
                            restaurantAbout,
                            restaurantDeliverFee,
                            picture));
                }

                RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.restaurant_cycle);
                RestaurantAdapter myAdapter = new RestaurantAdapter(view.getContext(), restaurants);
                recycleView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                recycleView.setAdapter(myAdapter);
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