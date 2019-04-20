package com.mang.restaury.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Adapter.CartAdapter;
import com.mang.restaury.Model.CartItem;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private View rootView;
    private CartAdapter cartAdapter;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        ListView cartItems = (ListView) rootView.findViewById(R.id.cart_items);
        cartItems.setDivider(null);


        // Load Cart Item from Realm
        final Realm realm = Realm.getDefaultInstance();

        final RealmResults<CartItem> items = realm.where(CartItem.class).findAll();
        final List<CartItem> cart = items.subList(0, items.size());

        cartAdapter = new CartAdapter(CartFragment.this, getActivity(), cart);
        cartItems.setAdapter(cartAdapter);
        setListViewHeightBasedOnChildren(cartItems);


        // Set Maximum people can book
        final Spinner date = (Spinner) rootView.findViewById(R.id.delivery_date);

        // Spinner Drop down elements
        List<String> dateData = new ArrayList<String>();
        dateData.add("Today");
        dateData.add("Tomorrow");

        setSpinner(date, dateData);



        // Set Maximum people can book
        final Spinner time = (Spinner) rootView.findViewById(R.id.delivery_time);

        // Spinner Drop down elements
        List<String> timeData = new ArrayList<String>();
        timeData.add("13:00");
        timeData.add("14:00");
        timeData.add("15:00");
        timeData.add("16:00");
        timeData.add("17:00");

        setSpinner(time, timeData);




       // Load User Data
        final AuthenticationFragment auth = AuthenticationFragment.getInstance();

        if (auth.getCurrentUser() != null) {

            final EditText fullNameEditText = (EditText) rootView.findViewById(R.id.fullname);
            final EditText phoneEditText = (EditText) rootView.findViewById(R.id.phone);
            final EditText addressEditText = (EditText) rootView.findViewById(R.id.address);


            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference ref = database.getReference();
            final String uid = auth.getCurrentUser().getUid();

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








        Button placeOrderButton = (Button) rootView.findViewById(R.id.place_order);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





        return rootView;
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

    public void setSpinner(Spinner spinner, List data) {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void updateValue() {

        TextView subtotal = (TextView) rootView.findViewById(R.id.sub_total);
        TextView deliveryFee = (TextView) rootView.findViewById(R.id.delivery_fee);
        TextView vat = (TextView) rootView.findViewById(R.id.vat);
        TextView total = (TextView) rootView.findViewById(R.id.total);


        int subTotalCal = cartAdapter.subtotal;
        double vatCal = subTotalCal * 0.07;
        int deliverFeeCal = 20;
        double totalCal = subTotalCal + deliverFeeCal + vatCal;

        subtotal.setText(String.valueOf("฿ " + subTotalCal));
        deliveryFee.setText(String.valueOf("฿ " + deliverFeeCal));
        vat.setText(String.valueOf("฿ " + String.format("%.2f", vatCal)));
        total.setText(String.valueOf("฿ " + String.format("%.2f", totalCal)));
    }

}
