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
import android.widget.RatingBar;
import android.widget.TextView;

import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.Activity.RestaurantActivity;
import com.squareup.picasso.Picasso;

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

        // Reference to an image file in Firebase Storage
        if(restaurant.getPicture() != null) Picasso.get().load(restaurant.getPicture()).into(viewHolder.restaurant_image);


        // viewHolder.restaurant_image.setImageURI();
        viewHolder.restaurant_name.setText(restaurant.getTitle());
        viewHolder.restaurant_price.setText(restaurant.getMinPrice()+" - "+restaurant.getMaxPrice()+" Baht");
        viewHolder.restaurant_type.setText(restaurant.getType().split(",")[0].toUpperCase()+" FOOD");
        viewHolder.restaurant_star.setRating((float) restaurant.getStar());


        // On Click
        viewHolder.restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, RestaurantActivity.class);
                intent.putExtra("restaurant_name", restaurant.getTitle());
                intent.putExtra("latitute",restaurant.getLatitude());
                intent.putExtra("longitute",restaurant.getLongtitude());
                intent.putExtra("picture",restaurant.getPicture());
                intent.putExtra("about", restaurant.getRestaurantAbout());
                intent.putExtra("res_id", restaurant.getRestaurantID());
                intent.putExtra("res_name",restaurant.getTitle());
                intent.putExtra("res_deliverFee", restaurant.getRestaurantDeliverFee());
                intent.putExtra("res_star", restaurant.getStar());
                intent.putExtra("resType", restaurant.getType());
                intent.putExtra("resURL", restaurant.getURL());

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
        RatingBar restaurant_star;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get ID from XML

            restaurant = (LinearLayout) itemView.findViewById(R.id.restaurant);
            restaurant_image = (ImageView) itemView.findViewById(R.id.restaurant_image);
            restaurant_name = (TextView) itemView.findViewById(R.id.restaurant_name);
            restaurant_price = (TextView) itemView.findViewById(R.id.restaurant_price);
            restaurant_type = (TextView) itemView.findViewById(R.id.restaurant_type);
            restaurant_star = (RatingBar) itemView.findViewById(R.id.average_star);
        }
    }

}
