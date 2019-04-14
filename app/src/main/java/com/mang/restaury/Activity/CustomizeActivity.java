package com.mang.restaury.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


//        res = findViewById(R.id.total_price);
//        res.setText("฿ "+String.format("%.2f", (float)price));

        // GET QUERY HERE
        // String restaurant_name = getIntent().getExtras().getString("restaurant_name");


    }
}
