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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mang.restaury.Activity.CustomizeActivity;
import com.mang.restaury.Model.Menu;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.Activity.RestaurantActivity;

import java.util.List;

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

    @Override
    public void onBindViewHolder(@NonNull final MenuAdapter.ViewHolder viewHolder, int i) {

        final Menu menu = mData.get(i);

        viewHolder.menuName.setText(menu.getMenuName());
        viewHolder.menuPrice.setText(menu.getPrice().toString());

        // On Click
        viewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CustomizeActivity.class);
                intent.putExtra("menu_name", menu.getMenuName());
                intent.putExtra("price", menu.getPrice());

                mContext.startActivity(intent);
                getActivity.overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get ID from XML

            menu = (LinearLayout) itemView.findViewById(R.id.menu_layout);
            menuName = (TextView) itemView.findViewById(R.id.menu_name);
            menuPrice = (TextView) itemView.findViewById(R.id.menu_price);
        }
    }


}