package com.mang.restaury.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Adapter.CartAdapter;
import com.mang.restaury.Adapter.OrderAdapter;
import com.mang.restaury.Fragments.AuthenticationFragment;
import com.mang.restaury.Fragments.CartFragment;
import com.mang.restaury.Model.CartItem;
import com.mang.restaury.Model.OrderDetail;
import com.mang.restaury.R;

import java.util.ArrayList;
import java.util.List;

public class YourOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order);

        // From MyOrderAdapter.java
        final String orderId = getIntent().getExtras().getString("orderId");


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        final AuthenticationFragment auth = AuthenticationFragment.getInstance();
        final String uid = auth.getCurrentUser().getUid();

        // Load order item
        final ListView orderItems = (ListView) findViewById(R.id.cart_items);
        orderItems.setDivider(null);

        Query orderItem = ref.child("OrderDetail").orderByChild("orderID").equalTo(orderId);
        orderItem.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<OrderDetail> items = new ArrayList<OrderDetail>();


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String ingredientID = ds.child("ingredientID").getValue(String.class);
                    String menuID = ds.child("menuID").getValue(String.class);
                    String orderDetailRequest = ds.child("orderDetailRequest").getValue(String.class);
                    String orderID = ds.child("orderID").getValue(String.class);
                    String sizeID = ds.child("sizeID").getValue(String.class);
                    int totalNumber = ds.child("totalNumber").getValue(int.class);

                    items.add(new OrderDetail(ingredientID, menuID, orderDetailRequest, orderID, sizeID, totalNumber));

                }


                OrderAdapter orderAdapter = new OrderAdapter(getBaseContext(), items);
                orderItems.setAdapter(orderAdapter);
                setListViewHeightBasedOnChildren(orderItems);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Load order
        Query order = ref.child("Order").child(orderId);
        order.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TextView subtotal = (TextView) findViewById(R.id.sub_total);
                TextView deliveryFee = (TextView) findViewById(R.id.delivery_fee);
                TextView vat = (TextView) findViewById(R.id.vat);
                TextView total = (TextView) findViewById(R.id.total);

                TextView deliveryDate = (TextView) findViewById(R.id.delivery_date);
                TextView deliveryTime = (TextView) findViewById(R.id.delivery_time);

                final TextView fullNameEditText = (TextView) findViewById(R.id.fullname);
                final TextView phoneEditText = (TextView) findViewById(R.id.phone);
                final TextView addressEditText = (TextView) findViewById(R.id.address);


                double totalCal = dataSnapshot.child("orderTotalPrice").getValue(Double.class);
                int deliverFeeCal = 20;
                double subTotalCal = (totalCal - deliverFeeCal) / (1 + 0.07);
                double vatCal = subTotalCal * 0.07;

                subtotal.setText(String.valueOf("฿ " + String.format("%.0f", subTotalCal)));
                deliveryFee.setText(String.valueOf("฿ " + deliverFeeCal));
                vat.setText(String.valueOf("฿ " + String.format("%.2f", vatCal)));
                total.setText(String.valueOf("฿ " + String.format("%.2f", totalCal)));


                String dateTime = dataSnapshot.child("orderDateTime").getValue(String.class);
                String[] splitDateTime = dateTime.split(" ");

                deliveryDate.setText(splitDateTime[0]);
                deliveryTime.setText(splitDateTime[1]);


                // Load User
                Query users = ref.child("User").child(uid);
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String firstName = dataSnapshot.child("firstName").getValue(String.class);
                        String lastName = dataSnapshot.child("lastName").getValue(String.class);
                        String fullname = firstName + " " + lastName;

                        String phone = dataSnapshot.child("phoneNumber").getValue(String.class);
                        String address = dataSnapshot.child("address").getValue(String.class);

                        fullNameEditText.setText(fullname);
                        phoneEditText.setText(phone);
                        addressEditText.setText(address);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
