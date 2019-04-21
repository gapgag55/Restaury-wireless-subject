package com.mang.restaury.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mang.restaury.Activity.YourOrderActivity;
import com.mang.restaury.Model.Order;
import com.mang.restaury.R;

import java.util.HashMap;

public class MyTableReservationAdapter extends BaseAdapter {

    private HashMap<String, String> items;

    LayoutInflater inflter;
    Context mContext;

    public MyTableReservationAdapter(Context context, HashMap<String, String> items) {
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

//        final String orderKey = (items.keySet().toArray())[i].toString();
//        Order order = items.get(orderKey);
//
//        holder.dateTime.setText("Date: " + order.getOrderDateTime());
//        holder.viewOrderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mContext, YourOrderActivity.class);
//                intent.putExtra("orderId", orderKey);
//
//                mContext.startActivity(intent);
//
//            }
//        });

        return view;
    }

    private class ViewHolder{
        TextView restaurantName;
        TextView dateTime;
    }
}
