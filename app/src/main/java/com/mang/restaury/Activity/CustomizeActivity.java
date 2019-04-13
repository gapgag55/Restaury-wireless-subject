package com.mang.restaury.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mang.restaury.R;

public class CustomizeActivity extends AppCompatActivity {

    private TextView menu;
    private TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        String menu_name = getIntent().getExtras().getString("menu_name");
//        int price = getIntent().getExtras().getInt("price");

        menu = findViewById(R.id.restaurant_name);
        menu.setText(menu_name);

//        res = findViewById(R.id.total_price);
//        res.setText("฿ "+String.format("%.2f", (float)price));

        // GET QUERY HERE
        // String restaurant_name = getIntent().getExtras().getString("restaurant_name");


    }
}
