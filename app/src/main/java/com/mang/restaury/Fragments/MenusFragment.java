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
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Adapter.MenuAdapter;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Menu;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MenusFragment extends Fragment {

    private ArrayList<Menu> menus;
    String resID;

    public MenusFragment(String resID) {
        // Required empty public constructor
        this.resID = resID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_menus, container, false);

        menus = new ArrayList<>();

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        Query menuRef = ref.child("Menu").orderByChild("restaurantID").equalTo(resID);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String menu_ID = ds.getKey();
                    int menu_basePrice = ds.child("menuBasePrice").getValue(int.class);
                    String menu_name = ds.child("menuName").getValue(String.class);

                    Log.d(TAG,menu_ID+"/"+resID+"/"+menu_basePrice);

                    menus.add(new Menu(menu_ID, menu_name, menu_basePrice, resID));
                }

                System.out.println(menus.toArray());

                RecyclerView recycleView = (RecyclerView) rootView.findViewById(R.id.menu_cycle);
                MenuAdapter myAdapter = new MenuAdapter(rootView.getContext(), menus, getActivity());
                recycleView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));
                recycleView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        menuRef.addListenerForSingleValueEvent(eventListener);


        Button viewCartButton = (Button) rootView.findViewById(R.id.view_cart_button);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Go to cart
            }
        });

        return rootView;
    }

}
