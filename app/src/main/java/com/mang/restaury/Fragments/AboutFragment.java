package com.mang.restaury.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.R;
import com.mang.restaury.Activity.TableReservationActivity;

/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("ValidFragment")
public class AboutFragment extends Fragment {

    private View rootView;

    private String resID;
    private String restaurantName;
    private double latitute;
    private double longitute;
    private String about;

    MapView mMapView;
    private GoogleMap googleMap;

    public AboutFragment(String resID, String restaurantName, double latitute, double longitute, String about) {
        this.resID = resID;
        this.restaurantName = restaurantName;
        this.latitute = latitute;
        this.longitute = longitute;
        this.about = about;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about, container, false);

        TextView description = (TextView) rootView.findViewById(R.id.about);
        description.setText(about);

        Button bookTableButton = (Button) rootView.findViewById(R.id.table_book_button);

        bookTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthenticationFragment auth = AuthenticationFragment.getInstance();
                System.out.println(auth.getCurrentUser());

                // Check Login
                if (auth.getCurrentUser() != null) {
                    bookTable();
                } else {
                    auth.show(getFragmentManager(), "Authentication");
                }

            }
        });

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(latitute, longitute);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_icon);

                Marker marker = googleMap.addMarker(new MarkerOptions().position(sydney).title(restaurantName));
                marker.setIcon(icon);

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(17).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        // count reservation

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query reservation = ref.child("Reservation").orderByChild("restaurantId").equalTo(resID);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long reservation_time = dataSnapshot.getChildrenCount();

                Log.d("reservation", " "+ reservation_time+resID);

                TextView reservation_count = (TextView) rootView.findViewById(R.id.reservation_count);
                reservation_count.setText(Long.toString(reservation_time)+" Reservations");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        reservation.addValueEventListener(eventListener);

        return rootView;
    }

    public void bookTable() {
        Intent intent = new Intent(rootView.getContext(), TableReservationActivity.class);
        intent.putExtra("resID", resID);
        intent.putExtra("restaurantName", restaurantName);
        rootView.getContext().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
