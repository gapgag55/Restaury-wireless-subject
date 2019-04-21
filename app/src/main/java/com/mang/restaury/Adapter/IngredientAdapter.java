package com.mang.restaury.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mang.restaury.Activity.CustomizeActivity;
import com.mang.restaury.Model.Customize;
import com.mang.restaury.R;

import java.util.ArrayList;

public class IngredientAdapter extends BaseAdapter {


    private ArrayList<Customize> ingredients;
    public static Customize selectedIngredient;

    LayoutInflater inflter;
    Context context;
    CustomizeActivity activity;

    private RadioButton button;
    private int selectedPosition = -1;

    public IngredientAdapter(Context context, CustomizeActivity activity, ArrayList<Customize> ingredients) {
        this.context = context;
        this.activity = activity;
        this.ingredients = ingredients;

        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)  {
        IngredientAdapter.ViewHolder holder;

        if(view == null){

            view = inflter.inflate(R.layout.radio_customize, viewGroup, false);

            holder = new IngredientAdapter.ViewHolder();

            holder.variationType = (RadioButton) view.findViewById(R.id.variation_type);
            holder.variationPrice = (TextView) view.findViewById(R.id.variation_price);

            view.setTag(holder);

        } else {
            holder = (IngredientAdapter.ViewHolder)view.getTag();
        }

        holder.variationType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i != selectedPosition && button != null){
                    button.setChecked(false);
                }

                selectedPosition = i;
                button = (RadioButton) v;

                selectedIngredient = ingredients.get(i);
                activity.updatePrice();
            }
        });


        if(selectedPosition != i){
            holder.variationType.setChecked(false);
        }else{
            holder.variationType.setChecked(true);
            if(button != null && holder.variationType != button){
                button = holder.variationType;
            }
        }


        Customize item = ingredients.get(i);

        holder.variationType.setText(item.getName());
        holder.variationPrice.setText("฿ +" + String.valueOf(item.getPrice()));

        return view;
    }

    private class ViewHolder{
        RadioButton     variationType;
        TextView        variationPrice;
    }
}
