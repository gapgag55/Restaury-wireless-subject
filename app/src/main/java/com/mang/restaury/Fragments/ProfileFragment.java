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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Model.User;
import com.mang.restaury.R;


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


        final EditText fullNameEditText = (EditText) rootView.findViewById(R.id.fullname);
        final EditText phoneEditText = (EditText) rootView.findViewById(R.id.phone);
        final EditText addressEditText = (EditText) rootView.findViewById(R.id.address);
        final ScrollView logout = (ScrollView) rootView.findViewById(R.id.profile_scrollview);

        // Query User Profile

        final AuthenticationFragment auth = AuthenticationFragment.getInstance();

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

    private void changeToLogoutView2() {
        ScrollView logout = (ScrollView) rootView.findViewById(R.id.profile_scrollview);
        logout.setVisibility(View.VISIBLE);
    }
}
