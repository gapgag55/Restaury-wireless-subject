package com.mang.restaury.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mang.restaury.Adapter.MenuAdapter;
import com.mang.restaury.Model.Menu;
import com.mang.restaury.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenusFragment extends Fragment {

    private ArrayList<Menu> menus;

    public MenusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_menus, container, false);

        menus = new ArrayList<>();
        menus.add(new Menu("Thai Fried Rice with Prawns", 60.00));
        menus.add(new Menu("Thai Style Pork Omelette", 40.00));
        menus.add(new Menu("Thai Fried Rice with Prawns", 10.00));
        menus.add(new Menu("Thai Style Pork Omelette", 50.00));
        menus.add(new Menu("Thai Fried Rice with Prawns", 70.00));
        menus.add(new Menu("Thai Style Pork Omelette", 80.00));

        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.menu_cycle);
        MenuAdapter myAdapter = new MenuAdapter(view.getContext(), menus);
        recycleView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        recycleView.setAdapter(myAdapter);

        return view;
    }

}
