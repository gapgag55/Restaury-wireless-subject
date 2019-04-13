package com.mang.restaury.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.Model.Table;
import com.mang.restaury.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class TableReservationActivity extends AppCompatActivity {

    private ArrayList<Table> tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservation);

        int resID = getIntent().getExtras().getInt("res_id");
        String restaurantName = getIntent().getExtras().getString("restaurantName");

        // Set Display
        TextView restaurantNameText = (TextView) findViewById(R.id.restaurant_name);
        restaurantNameText.setText(restaurantName);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query tableRef = ref.child("Table").orderByChild("restaurant_ID").equalTo(resID);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    int restaurantID = ds.child("restaurant_ID").getValue(int.class);
                    int tableID = ds.child("table_id").getValue(int.class);
                    int tableSeat = ds.child("table_seat").getValue(int.class);

                    Log.d(TAG,restaurantID + ":" + tableID + ":" + tableSeat + "\n");

                   // Load table of this restaurant
                    tables.add(new Table(restaurantID, tableID, tableSeat));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        // Assume I am User: 1


        // Submit Reservation
    }

}
