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
import com.mang.restaury.Model.CartItem;
import com.mang.restaury.Model.OrderDetail;
import com.mang.restaury.R;

import java.util.List;


public class OrderAdapter extends BaseAdapter {

    private List<OrderDetail> items;

    LayoutInflater inflter;
    Context context;

    public OrderAdapter(Context context, List<OrderDetail> items) {
        this.context = context;
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
    public View getView(final int i, View view, ViewGroup viewGroup)  {
        final OrderAdapter.ViewHolder holder;

        if (view == null) {

            view = inflter.inflate(R.layout.cart_item, viewGroup, false);
            holder = new OrderAdapter.ViewHolder();

            holder.removeButton = (Button) view.findViewById(R.id.remove_button);
            holder.addButton = (Button) view.findViewById(R.id.add_button);
            holder.itemAmount = (TextView) view.findViewById(R.id.menu_amount);
            holder.menuName = (TextView) view.findViewById(R.id.menu_name);
            holder.menuPrice = (TextView) view.findViewById(R.id.menu_price);

            view.setTag(holder);

        } else {
            holder = (OrderAdapter.ViewHolder) view.getTag();
        }


        final OrderDetail orderItem = items.get(i);

        holder.removeButton.setVisibility(View.GONE);
        holder.addButton.setVisibility(View.GONE);

        // GET restaurant name

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        Query menus = ref.child("Menu").child(orderItem.getMenuID());
        menus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final String menuName = dataSnapshot.child("menuName").getValue(String.class);
                final int menuPrice = dataSnapshot.child("menuBasePrice").getValue(int.class);


                // Get Size ID Price
                Query size = ref.child("MenuSize").orderByChild("sizeID").equalTo(orderItem.getSizeID());
                size.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            if (ds.child("menuID").getValue(String.class).equals(orderItem.getMenuID())) {

                                int additionalPrice = ds.child("additionalPrice").getValue(int.class);

                                int menuTotalPrice = orderItem.getTotalNumber() * (menuPrice + additionalPrice);

                                holder.menuName.setText(String.valueOf(menuName));
                                holder.menuPrice.setText("฿ " +  String.valueOf(menuTotalPrice));
                                holder.itemAmount.setText(String.valueOf(orderItem.getTotalNumber()));

                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
