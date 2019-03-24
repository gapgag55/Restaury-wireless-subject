package com.mang.restaury.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mang.restaury.R;
import com.mang.restaury.RestaurantActivity;
import com.mang.restaury.TableReservationActivity;

/**
 * A simple {@link Fragment} subclass.
 */

public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_about, container, false);
        final Button bookTableButton = (Button) view.findViewById(R.id.table_book_button);

        bookTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(view.getContext(), TableReservationActivity.class);
                intent.putExtra("restaurant_name", "Shabu Ha");

                view.getContext().startActivity(intent);
            }
        });

        return view;
    }

}
