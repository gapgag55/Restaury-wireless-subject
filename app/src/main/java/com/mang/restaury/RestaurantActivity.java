package com.mang.restaury;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {

    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        String restaurant_name = getIntent().getExtras().getString("restaurant_name");

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(restaurant_name);
    }
}
