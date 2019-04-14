package com.mang.restaury.Activity;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Reservation;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.Model.Table;
import com.mang.restaury.R;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

        // Set Close
        ImageButton closeButton = (ImageButton) findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });


        // Set Maximum people can book
        final Spinner people = (Spinner) findViewById(R.id.maximum_people);

        // Spinner Drop down elements
        List<String> numbers = new ArrayList<String>();
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");

        setSpinner(people, numbers);


        // Set Maximum people can book
        final Spinner date = (Spinner) findViewById(R.id.reserve_date);

        // Spinner Drop down elements
        List<String> dateData = new ArrayList<String>();
        dateData.add("Today");
        dateData.add("Tomorrow");

        setSpinner(date, dateData);



        // Set Maximum people can book
        final Spinner time = (Spinner) findViewById(R.id.reserve_time);

        // Spinner Drop down elements
        List<String> timeData = new ArrayList<String>();
        timeData.add("13:00");
        timeData.add("14:00");
        timeData.add("15:00");
        timeData.add("16:00");
        timeData.add("17:00");

        setSpinner(time, timeData);

        final EditText instruction = (EditText) findViewById(R.id.instruction);


        // Submit Reservation
        final TextView status = (TextView) findViewById(R.id.reservation_status);
        status.setVisibility(View.GONE);

        Button reserveTableButton = (Button) findViewById(R.id.reserve_table);
        reserveTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference ref = database.getReference();

                // Get Current User ID
                final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Get Request from user
                final int reservePeople = Integer.parseInt(people.getSelectedItem().toString());
                final String reserveInstruction = instruction.getText().toString();
                String reserveDate = date.getSelectedItem().toString();
                String reserveTime = time.getSelectedItem().toString();

//                System.out.println(reservePeople + " : " + reserveDate + " : " + reserveTime);

                if (reserveDate.equals("Today")) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    reserveDate = dateFormat.format(date);
                }

                if (reserveDate.equals("Tomorrow")) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date((new Date()).getTime() + (1000 * 60 * 60 * 24));
                    reserveDate = dateFormat.format(date);
                }

                final String reserveDateTime = reserveDate + " " + reserveTime;
//                System.out.println(dateTime);


               // Get available table of this restaurant
                Query tableRef = ref.child("Table").orderByChild("restaurant_ID").equalTo(1);
                tableRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // dataSnapshot is the "issue" node with all children with id 0

                            final HashMap<String, Boolean> temp = new HashMap<>();
                            final ArrayList<Integer> tables = new ArrayList<>();

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                int seats = ds.child("table_seat").getValue(int.class);
                                final int tableID = ds.child("table_id").getValue(int.class);

                                if (seats >= reservePeople) {
                                    tables.add(tableID);
                                }
                            }

                            // Check table that is available
                            Query reservation = ref.child("Reservation").orderByChild("restaurantId").equalTo(resID);
                            reservation.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for (Integer tableID : tables) {
                                        // Set default
                                        temp.put("isTableAvailable", true);

                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                            int reserveTableID = ds.child("tableId").getValue(int.class);
                                            String dateTime = ds.child("dateTime").getValue(String.class);

                                            if (tableID == reserveTableID && dateTime.equals(reserveDateTime)) {
                                                temp.put("isTableAvailable", false);
                                                break;
                                            }
                                        }

                                        if (temp.get("isTableAvailable")) {
                                            // Save Data
                                            ref.child("Reservation").push().setValue(
                                                    new Reservation(reserveInstruction, reserveDateTime, tableID, resID, userID)
                                            );

                                            status.setVisibility(View.GONE);
                                            close();

                                            break;
                                        } else {
                                            status.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) { }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

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


    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
    }

}
