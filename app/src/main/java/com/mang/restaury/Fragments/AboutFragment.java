package com.mang.restaury.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mang.restaury.Activity.CustomizeActivity;
import com.mang.restaury.R;
import com.mang.restaury.Activity.TableReservationActivity;

/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("ValidFragment")
public class AboutFragment extends Fragment {

    private View rootView;
//    private Authentication auth;

    private TextView description;
    private String resID;
    private String restaurantName;
    private double latitute;
    private double longitute;
    private String about;

//    MapView mMapView;
//    private GoogleMap googleMap;

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
//        auth = new Authentication(rootView, inflater);

        TextView description = (TextView) rootView.findViewById(R.id.about);
        description.setText(about);

//        createMap(rootView, savedInstanceState);

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

        return rootView;
    }

    public void bookTable() {
        Intent intent = new Intent(rootView.getContext(), TableReservationActivity.class);
        intent.putExtra("resID", resID);
        intent.putExtra("restaurantName", restaurantName);
        rootView.getContext().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
    }

//
//    private void createMap(View rootView, Bundle savedInstanceState) {
//        mMapView = (MapView) rootView.findViewById(R.id.mapView);
//        mMapView.onCreate(savedInstanceState);
//
//        mMapView.onResume(); // needed to get the map to display immediately
//
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                googleMap = mMap;
//
//                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(latitute, longitute);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            }
//        });
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mMapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mMapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mMapView.onLowMemory();
//    }

}
