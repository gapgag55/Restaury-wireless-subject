package com.mang.restaury.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.mang.restaury.Adapter.MenuAdapter;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Menu;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MenusFragment extends Fragment {

    private ArrayList<Menu> menus;
    int rest_id;
    String res_name;

    public MenusFragment(int rest_id, String res_name) {
        // Required empty public constructor
        this.rest_id = rest_id;
        this.res_name = res_name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_menus, container, false);

        menus = new ArrayList<>();

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference tableRef = ref.child("Menu");



        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    int menu_ID = ds.child("menu_ID").getValue(int.class);
                    int menu_basePrice = ds.child("menu_basePrice").getValue(int.class);
                    String menu_name = ds.child("menu_name").getValue(String.class);
                    String menu_pictureURL = ds.child("menu_pictureURL").getValue(String.class);
                    int restaurant_ID = ds.child("restaurant_ID").getValue(int.class);

                    Log.d(TAG,menu_ID+"/"+rest_id+"/"+menu_basePrice);

                    if(restaurant_ID == rest_id) menus.add(new Menu(menu_ID,menu_name,menu_basePrice,menu_pictureURL,restaurant_ID,res_name));

                }

                RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.menu_cycle);
                MenuAdapter myAdapter = new MenuAdapter(view.getContext(), menus);
                recycleView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
                recycleView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        tableRef.addListenerForSingleValueEvent(eventListener);

        return view;
    }

}
