package com.mang.restaury.Adapter;

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

import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.RestaurantActivity;

import org.w3c.dom.Text;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private Context mContext;
    private List<Restaurant> mData;

    public RestaurantAdapter(Context mContext, List<Restaurant> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.restaurant, viewGroup, false);
        return new RestaurantAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final Restaurant restaurant = mData.get(i);

        // viewHolder.restaurant_image.setImageURI();
        viewHolder.restaurant_name.setText(restaurant.getTitle());
        viewHolder.restaurant_price.setText("250 - 1000 Baht");
        viewHolder.restaurant_type.setText("THAI FOOD");


        // On Click
        viewHolder.restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, RestaurantActivity.class);
                intent.putExtra("restaurant_name", restaurant.getTitle());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Declare variable
        LinearLayout restaurant;
        ImageView restaurant_image;
        TextView restaurant_name;
        TextView restaurant_price;
        TextView restaurant_type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get ID from XML

            restaurant = (LinearLayout) itemView.findViewById(R.id.restaurant);
            restaurant_image = (ImageView) itemView.findViewById(R.id.restaurant_image);
            restaurant_name = (TextView) itemView.findViewById(R.id.restaurant_name);
            restaurant_price = (TextView) itemView.findViewById(R.id.restaurant_price);
            restaurant_type = (TextView) itemView.findViewById(R.id.restaurant_type);
        }
    }

}
