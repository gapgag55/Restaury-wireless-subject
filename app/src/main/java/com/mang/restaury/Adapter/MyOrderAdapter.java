package com.mang.restaury.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mang.restaury.Activity.RestaurantActivity;
import com.mang.restaury.Model.CartItem;
import com.mang.restaury.Model.Order;
import com.mang.restaury.R;
import com.mang.restaury.YourOrderActivity;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;


public class MyOrderAdapter extends BaseAdapter {

    private HashMap<String, Order> items;

    LayoutInflater inflter;
    Context mContext;

    public MyOrderAdapter(Context context, HashMap<String, Order> items) {
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

        final MyOrderAdapter.ViewHolder holder;

        if(view == null){
            view = inflter.inflate(R.layout.cart_item, viewGroup, false);
            holder = new MyOrderAdapter.ViewHolder();

            holder.dateTime = (TextView) view.findViewById(R.id.date_time);
            holder.viewOrderButton = (Button) view.findViewById(R.id.view_order);

            view.setTag(holder);

        } else {
            holder = (MyOrderAdapter.ViewHolder) view.getTag();
        }
//
//        for (Order order : items) {
//
//        }

        Order order = items.get((items.keySet().toArray())[i]);

        holder.dateTime.setText("Date: " + order.getOrderDateTime().toString());
        holder.viewOrderButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 Intent intent = new Intent(mContext, YourOrderActivity.class);
//                 intent.putExtra("restaurant_name", restaurant.getTitle());

                 mContext.startActivity(intent);

             }
         });

        return view;
    }

    private class ViewHolder{
        TextView dateTime;
        Button viewOrderButton;
    }
}