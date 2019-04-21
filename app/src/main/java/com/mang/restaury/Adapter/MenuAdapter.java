package com.mang.restaury.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Activity.CustomizeActivity;
import com.mang.restaury.Model.Menu;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.Activity.RestaurantActivity;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>  {
    private Context mContext;
    private List<Menu> mData;
    private Activity getActivity;

    public MenuAdapter(Context mContext, List<Menu> mData, Activity getActivity) {
        this.mContext = mContext;
        this.mData = mData;
        this.getActivity = getActivity;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.menu, viewGroup, false);
        return new MenuAdapter.ViewHolder(view);
    }

    public String addLinebreaks(String input, int maxLineLength) {
        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());

        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            if (lineLen + word.length() > maxLineLength) {
                output.append("\n");
                lineLen = 0;

            }
            output.append(word+" ");
            lineLen += word.length();
        }
        return output.toString();
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuAdapter.ViewHolder viewHolder, int i) {

        final Menu menu = mData.get(i);

        viewHolder.menuName.setText(addLinebreaks(menu.getMenuName(),30));

        viewHolder.menuPrice.setText("฿ " + menu.getPrice().toString());


        // Enable and Disable intent for add ons

        final HashMap<String, Boolean> temp = new HashMap<>();
        temp.put("addonsAvailable", true);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        final Query menuSize = ref.child("MenuSize").orderByChild("menuID").equalTo(menu.getMenuID());
        menuSize.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    temp.put("addonsAvailable", true);
                } else {
                    viewHolder.menuAddons.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // On Click
        viewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (temp.get("addonsAvailable")) {
                    Intent intent = new Intent(mContext, CustomizeActivity.class);
                    intent.putExtra("menuName", menu.getMenuName());
                    intent.putExtra("menuID", menu.getMenuID());
                    intent.putExtra("menuPrice", menu.getPrice().toString());

                    mContext.startActivity(intent);
                    getActivity.overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Declare variable
        LinearLayout menu;
        TextView menuName;
        TextView menuPrice;
        TextView menuAddons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get ID from XML
            menu = (LinearLayout) itemView.findViewById(R.id.menu_layout);
            menuName = (TextView) itemView.findViewById(R.id.menu_name);
            menuPrice = (TextView) itemView.findViewById(R.id.menu_price);
            menuAddons = (TextView) itemView.findViewById(R.id.menu_addons);
        }
    }


}