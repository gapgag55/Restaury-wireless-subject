package com.mang.restaury.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class TableReservationActivity extends AppCompatActivity {

    private ArrayList<Table> tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservation);

        final int resID = getIntent().getExtras().getInt("resID");
        String restaurantName = getIntent().getExtras().getString("restaurantName");

        // Set Display
        TextView restaurantNameText = (TextView) findViewById(R.id.restaurant_name);
        restaurantNameText.setText(restaurantName);


        // Set Maximum people can book
        Spinner people = (Spinner) findViewById(R.id.maximum_people);

        // Spinner Drop down elements
        List<String> numbers = new ArrayList<String>();
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");

        setSpinner(people, numbers);


        // Set Maximum people can book
        Spinner reserveDate = (Spinner) findViewById(R.id.reserve_date);

        // Spinner Drop down elements
        List<String> dateData = new ArrayList<String>();
        dateData.add("Today");
        dateData.add("Tomorrow");
        dateData.add("16/04/2562");
        dateData.add("17/04/2562");

        setSpinner(reserveDate, dateData);



        // Set Maximum people can book
        Spinner reserveTime = (Spinner) findViewById(R.id.reserve_time);

        // Spinner Drop down elements
        List<String> timeData = new ArrayList<String>();
        timeData.add("13:00");
        timeData.add("14:00");
        timeData.add("15:00");
        timeData.add("16:00");
        timeData.add("17:00");

        setSpinner(reserveTime, timeData);




//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference();
//        Query tableRef = ref.child("Table").orderByChild("restaurant_ID").equalTo(resID);
//
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                    int restaurantID = ds.child("restaurant_ID").getValue(int.class);
//                    int tableID = ds.child("table_id").getValue(int.class);
//                    int tableSeat = ds.child("table_seat").getValue(int.class);
//
//                    Log.d(TAG,restaurantID + ":" + tableID + ":" + tableSeat + "\n");
//
//                   // Load table of this restaurant
//                    tables.add(new Table(restaurantID, tableID, tableSeat));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };



        // Submit Reservation
        Button reserveTableButton = (Button) findViewById(R.id.reserve_table);
        reserveTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();

                // Get Current User ID
//                FirebaseAuth user = firebaseAuth.


                Query tableRef = ref.child("Table").orderByChild("restaurant_ID").equalTo(resID);



                // Get appropriate Table of this restaurant


                // if error show message

            }
        });
    }


    public void setSpinner(Spinner spinner, List data) {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


}
