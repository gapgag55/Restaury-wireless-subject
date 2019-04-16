package com.mang.restaury.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.mang.restaury.Adapter.CommentAdapter;
import com.mang.restaury.Model.Comment;
import com.mang.restaury.Model.Reservation;
import com.mang.restaury.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@SuppressLint("ValidFragment")
public class ReviewsFragment extends Fragment {

    private ArrayList<Comment> comments;
    private View rootView;

    private int resID;

    public ReviewsFragment(int resID) {
        this.resID = resID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_reviews, container, false);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();


        Query comments = ref.child("Comment").orderByChild("restaurant_ID").equalTo(resID);
        comments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Comment> comments = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    // Get User profile
                    String uid = ds.child("user_ID").getValue(String.class);


                    // Get comment
                    String comment = ds.child("comment_detail").getValue(String.class);
                    String dateTime = ds.child("comment_dateTime").getValue(String.class);
                    int rating = ds.child("comment_rating").getValue(int.class);

                    comments.add(
                            new Comment(dateTime, comment, rating, resID, uid)
                    );

                }

                // Print comment to view
//                System.out.println(comments.toArray());
                RecyclerView recycleView = (RecyclerView) rootView.findViewById(R.id.comment_cycle);
                CommentAdapter myAdapter = new CommentAdapter(rootView.getContext(), comments);
                recycleView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));
                recycleView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//
//
//
//
//
//
//        Button sendButton = (Button) rootView.findViewById(R.id.send_comment_button);
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Check Authentication
//                AuthenticationFragment auth = AuthenticationFragment.getInstance();
//
//                // Check Login
//                if (auth.getCurrentUser() != null) {
//
//                    EditText commentBox = (EditText) rootView.findViewById(R.id.comment_box);
//
//                    String detail = commentBox.getText().toString();
//                    String dateTime = new Date().toString();
//                    String uid = auth.getCurrentUser().getUid();
//                    int rating = 5;
//
//                    if (detail.length() > 0) {
//
//                        ref.child("Comment").push().setValue(
//                                new Comment(dateTime, detail, rating, resID, uid)
//                        );
//
//                    } else {
//                        // Throw error there is no detail to submit for
//                    }
//
//
//                    // Reset value
//                    commentBox.setText("");
//
//
//                } else {
//                    auth.show(getFragmentManager(), "Authentication");
//                }
//
//            }
//        });
//
//



        return rootView;
    }
}
