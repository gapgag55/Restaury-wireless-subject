package com.mang.restaury.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        final EditText fullNameEditText = (EditText) rootView.findViewById(R.id.fullname);
        final EditText phoneEditText = (EditText) rootView.findViewById(R.id.phone);
        final EditText addressEditText = (EditText) rootView.findViewById(R.id.address);


        // Query User Profile

        final AuthenticationFragment auth = AuthenticationFragment.getInstance();
        final String uid = auth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

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


                Query userRef = ref.child("User").orderByChild("userId").equalTo(uid);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                        String[] splitFullName = fullNameEditText.getText().toString().split(" ");
//                        String firstName = splitFullName[0];
//                        String lastName = splitFullName[1];
//
//                        String phone = phoneEditText.getText().toString();
//                        String address = addressEditText.getText().toString();
//
//
//                        ref.child("User").child(uid).setValue(
//                                new User(uid, acct.getGivenName(), acct.getFamilyName(), "", "", acct.getEmail())
//                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



        return rootView;
    }

}
