package com.mang.restaury.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mang.restaury.Fragments.CartFragment;
import com.mang.restaury.Model.CartItem;
import com.mang.restaury.R;


import java.util.List;

import io.realm.Realm;

public class CartAdapter extends BaseAdapter {

    private List<CartItem> items;
    public static int subtotal = 0;

    LayoutInflater inflter;
    Context context;
    private CartFragment cartFragment;
    private Realm realm;

    public CartAdapter(CartFragment fragment, Context context, List<CartItem> items) {
        this.cartFragment = fragment;
        this.context = context;
        this.items = items;
        realm = Realm.getDefaultInstance();

        inflter = (LayoutInflater.from(context));

        subtotal = 0;

        // Calculate Default
        for (CartItem item : items) {
            subtotal += item.getTotalPrice();
        }

        cartFragment.updateValue();
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
    public View getView(final int i, View view, ViewGroup viewGroup)  {
        final CartAdapter.ViewHolder holder;

        if(view == null){
            view = inflter.inflate(R.layout.cart_item, viewGroup, false);
            holder = new CartAdapter.ViewHolder();

            holder.removeButton = (Button) view.findViewById(R.id.remove_button);
            holder.addButton = (Button) view.findViewById(R.id.add_button);
            holder.itemAmount = (TextView) view.findViewById(R.id.menu_amount);
            holder.menuName = (TextView) view.findViewById(R.id.menu_name);
            holder.menuPrice = (TextView) view.findViewById(R.id.menu_price);

            view.setTag(holder);

        } else {
            holder = (CartAdapter.ViewHolder) view.getTag();
        }

        final CartItem cartItem = items.get(i);

        // Set Button
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPrice = Integer.parseInt(holder.menuPrice.getText().toString().split(" ")[1]);
                int itemAmount = Integer.parseInt(holder.itemAmount.getText().toString());
                final int basePrice = totalPrice / itemAmount;

                itemAmount = itemAmount - 1;
                totalPrice = itemAmount * basePrice;

                final int amount = itemAmount;
                final int price = totalPrice;

                if (itemAmount > 0) {
                    // Set realm amount && price
                    realm.beginTransaction();
                    CartItem item = realm.where(CartItem.class).equalTo("menuID", cartItem.getMenuID().toString()).findFirst();
                    item.setTotalPrice(price);
                    item.setTotalNumber(amount);
                    realm.commitTransaction();

                    // set Text
                    holder.itemAmount.setText(String.valueOf(itemAmount));
                    holder.menuPrice.setText("฿ " + totalPrice);


                    // set totalPrice
                    subtotal -= basePrice;
                    cartFragment.updateValue();
                }
            }
        });

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPrice = Double.parseDouble(holder.menuPrice.getText().toString().split(" ")[1]);
                int itemAmount = Integer.parseInt(holder.itemAmount.getText().toString());
                double basePrice = totalPrice / itemAmount;

                itemAmount = itemAmount + 1;
                totalPrice = itemAmount * basePrice;


                // Set realm amount && price
//                realm.beginTransaction();
//                CartItem item = realm.where(CartItem.class).equalTo("menuID", cartItem.getMenuID().toString()).findFirst();
//                item.setTotalPrice(totalPrice);
//                item.setTotalNumber(itemAmount);
//                realm.commitTransaction();
//
//                //set text
//                holder.itemAmount.setText(String.valueOf(itemAmount));
//                holder.menuPrice.setText("฿ " + totalPrice);
//
//
//                subtotal += basePrice;
//                cartFragment.updateValue();
            }
        });

        holder.menuName.setText(String.valueOf(cartItem.getMenuName()));
        holder.menuPrice.setText("฿ " +  String.valueOf(cartItem.getTotalPrice()));
        holder.itemAmount.setText(String.valueOf(cartItem.getTotalNumber()));

        return view;
    }

    private class ViewHolder{
        Button      removeButton;
        Button      addButton;
        TextView    itemAmount;
        TextView    menuName;
        TextView    menuPrice;
    }
}
