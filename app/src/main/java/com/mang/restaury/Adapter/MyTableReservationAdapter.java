package com.mang.restaury.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Activity.YourOrderActivity;
import com.mang.restaury.Model.Order;
import com.mang.restaury.R;

import java.util.HashMap;

public class MyTableReservationAdapter extends BaseAdapter {

    private HashMap<String, HashMap<String, String>> items;

    LayoutInflater inflter;
    Context mContext;

    public MyTableReservationAdapter(Context context, HashMap<String, HashMap<String, String>> items) {
        this.mContext = context;
        this.items = items;

        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final MyTableReservationAdapter.ViewHolder holder;

        if(view == null){
            view = inflter.inflate(R.layout.my_table_reservation, viewGroup, false);
            holder = new MyTableReservationAdapter.ViewHolder();

            holder.restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
            holder.dateTime = (TextView) view.findViewById(R.id.date_time);

            view.setTag(holder);

        } else {
            holder = (MyTableReservationAdapter.ViewHolder) view.getTag();
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();


        final String reserveId = (items.keySet().toArray())[i].toString();
        final HashMap reserve = items.get(reserveId);
//
        final String restaurantId = (reserve.keySet().toArray())[0].toString();
        final String dateTime = reserve.get(restaurantId).toString();


        Query restaurants = ref.child("Restaurant").child(restaurantId);
        restaurants.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String restaurantName = dataSnapshot.child("restaurantName").getValue(String.class);

                holder.dateTime.setText(dateTime);
                holder.restaurantName.setText(restaurantName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private class ViewHolder{
        TextView restaurantName;
        TextView dateTime;
    }
}
