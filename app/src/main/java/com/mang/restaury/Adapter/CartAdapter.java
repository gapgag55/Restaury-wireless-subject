package com.mang.restaury.Adapter;

import android.content.Context;
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
import com.mang.restaury.Fragments.AuthenticationFragment;
import com.mang.restaury.Fragments.CartFragment;
import com.mang.restaury.Model.CartItem;
import com.mang.restaury.R;


import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    private ArrayList<CartItem> items;
    public static int totalPrice = 0;

    LayoutInflater inflter;
    Context context;
    CartFragment parent;

    public CartAdapter(Context context, CartFragment fragment, ArrayList<CartItem> items) {
        this.context = context;
        this.items = items;
        this.parent = fragment;

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
            holder = (CartAdapter.ViewHolder)view.getTag();
        }

        final CartItem cartItem = items.get(i);

        // Set Button
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPrice = Integer.parseInt(holder.menuPrice.getText().toString().split(" ")[1]);
                int itemAmount = Integer.parseInt(holder.itemAmount.getText().toString());
                int basePrice = totalPrice / itemAmount;

                itemAmount = itemAmount - 1;
                totalPrice = itemAmount * basePrice;

                if (itemAmount > 0) {
                    // Set realm amount && price
                    holder.itemAmount.setText(String.valueOf(itemAmount));
                    holder.menuPrice.setText("฿ " + totalPrice);


                    // set totalPrice

                }
            }
        });

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPrice = Integer.parseInt(holder.menuPrice.getText().toString().split(" ")[1]);
                int itemAmount = Integer.parseInt(holder.itemAmount.getText().toString());
                int basePrice = totalPrice / itemAmount;

                itemAmount = itemAmount + 1;
                totalPrice = itemAmount * basePrice;


                // Set realm amount && price
                holder.itemAmount.setText(String.valueOf(itemAmount));
                holder.menuPrice.setText("฿ " + totalPrice);
            }
        });


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        // Load data from firebase
        final Query menu = ref.child("Menu").child(cartItem.getMenuID());
        menu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                System.out.println(dataSnapshot);
                if (dataSnapshot.exists()) {

                    String menuName = dataSnapshot.child("menuName").getValue(String.class);
                    int price = dataSnapshot.child("menuBasePrice").getValue(int.class);
                    int total = cartItem.getTotalNumber();

                    int menuPrice = price * total;

                    holder.menuName.setText(menuName);
                    holder.menuPrice.setText("฿ " +  menuPrice);
                    holder.itemAmount.setText(String.valueOf(total));


                    // set total price
                    totalPrice += menuPrice;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
