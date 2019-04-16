package com.mang.restaury.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mang.restaury.R;

import java.util.ArrayList;

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

        // Set Close
        ImageButton closeButton = (ImageButton) findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });


        Button removeButton = (Button) findViewById(R.id.remove_button);
        Button addButton = (Button) findViewById(R.id.add_button);
        final TextView itemAmount = (TextView) findViewById(R.id.item_amount);


        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(itemAmount.getText().toString()) - 1;

                if (number > 0) {
                    itemAmount.setText(String.valueOf(number));
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(itemAmount.getText().toString()) + 1;
                itemAmount.setText(String.valueOf(number));
            }
        });








        // Variation paint out
        ListView variationList = (ListView) findViewById(R.id.variation_list);







//        res = findViewById(R.id.total_price);
//        res.setText("฿ "+String.format("%.2f", (float)price));

        // GET QUERY HERE
        // String restaurant_name = getIntent().getExtras().getString("restaurant_name");


    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
    }
}
