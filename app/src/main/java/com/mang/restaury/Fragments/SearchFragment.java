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
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.SearchActivity;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public static class Table {

        public int restaurant_ID;
        public int table_id;
        public int table_seat;

        public Table(int restaurant_ID, int table_id,int table_seat) {
            // ...
        }

        public Table() {
            // ...
        }

    }

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

                    int restaurant_ID = ds.child("restaurant_ID").getValue(int.class);
                    String restaurant_about = ds.child("restaurant_about").getValue(String.class);
                    int restaurant_deliverFee = ds.child("restaurant_deliverFee").getValue(int.class);
                    double restaurant_latitute = ds.child("restaurant_latitute").getValue(double.class);
                    double restaurant_longtitute = ds.child("restaurant_longtitute").getValue(double.class);
                    String restaurant_name = ds.child("restaurant_name").getValue(String.class);
                    String picture = ds.child("restaurant_pic").getValue(String.class);

                    Log.d(TAG,restaurant_ID+"/"+restaurant_about+picture);


                    restaurants.add(new Restaurant(restaurant_name,
                            restaurant_latitute,
                            restaurant_longtitute,
                            restaurant_ID,
                            restaurant_about,
                            restaurant_deliverFee,
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


//        restaurants = new ArrayList<>();
//        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
//        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
//        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
//        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
//        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
//        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));
//        restaurants.add(new Restaurant("Shabu Ha", 13.1670473, 100.9324348));
//        restaurants.add(new Restaurant("Shabu Ha 2", 13.1670473, 100.9324348));




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
