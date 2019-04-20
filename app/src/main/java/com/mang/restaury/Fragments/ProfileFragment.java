package com.mang.restaury.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Adapter.MyOrderAdapter;
import com.mang.restaury.Model.Order;
import com.mang.restaury.R;

import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View rootView;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);



        // Load Orders
        final ListView myOrders = (ListView) rootView.findViewById(R.id.my_orders);
        myOrders.setDivider(null);




        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        final AuthenticationFragment auth = AuthenticationFragment.getInstance();
        final String uid = auth.getCurrentUser().getUid();

        Query orders = ref.child("Order").orderByChild("userId").equalTo(uid);
        orders.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String, Order> orders = new HashMap<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String key = ds.getKey();
                    Double orderTotalPrice = ds.child("orderTotalPrice").getValue(Double.class);
                    String orderDateTime = ds.child("orderDateTime").getValue(String.class);
                    String orderAddress = ds.child("orderAddress").getValue(String.class);
                    String orderPhone = ds.child("orderPhone").getValue(String.class);

                    orders.put(key, new Order(uid, orderTotalPrice, orderDateTime, orderAddress, orderPhone));

                }

                MyOrderAdapter orderAdapter = new MyOrderAdapter(getContext(), orders);
                myOrders.setAdapter(orderAdapter);
                setListViewHeightBasedOnChildren(myOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

















        final EditText fullNameEditText = (EditText) rootView.findViewById(R.id.fullname);
        final EditText phoneEditText = (EditText) rootView.findViewById(R.id.phone);
        final EditText addressEditText = (EditText) rootView.findViewById(R.id.address);
        final ScrollView logout = (ScrollView) rootView.findViewById(R.id.profile_scrollview);

        // Query User Profile

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


        Button updateButton = rootView.findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] splitFullName = fullNameEditText.getText().toString().split(" ");
                String firstName = splitFullName[0];
                String lastName = splitFullName[1];

                String phone = phoneEditText.getText().toString();
                String address = addressEditText.getText().toString();

                DatabaseReference userRef = ref.child("User").child(uid);

                userRef.child("firstName").setValue(firstName);
                userRef.child("lastName").setValue(lastName);
                userRef.child("phoneNumber").setValue(phone);
                userRef.child("address").setValue(address);

                Toast.makeText(getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
            }
        });


        TextView logoutButton = (TextView) rootView.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToLogoutView();
                auth.signOut();

                // Going to SearchFragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.wrapper, new SearchFragment(), "search_fragment")
                        .commit();
            }
        });


        return rootView;
    }


    private void changeToLogoutView() {
        ScrollView logout = (ScrollView) rootView.findViewById(R.id.profile_scrollview);
        logout.setVisibility(View.GONE);
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
