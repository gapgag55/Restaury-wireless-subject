package com.mang.restaury.Fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Rating;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

@SuppressLint("ValidFragment")
public class ReviewsFragment extends Fragment {

    private ArrayList<Comment> comments;
    private View rootView;

    private String resID;

    public ReviewsFragment(String resID) {
        this.resID = resID;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_reviews, container, false);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        loadReview(ref);


        final RatingBar reviewRating = (RatingBar) rootView.findViewById(R.id.review_rating);

        Button sendButton = (Button) rootView.findViewById(R.id.send_comment_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check Authentication
                AuthenticationFragment auth = AuthenticationFragment.getInstance();

                // Check Login
                if (auth.getCurrentUser() != null) {

                    EditText commentBox = (EditText) rootView.findViewById(R.id.comment_box);

                    String detail = commentBox.getText().toString();
                    String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                    String uid = auth.getCurrentUser().getUid();
                    float rating = reviewRating.getRating();

                    if (detail.length() > 0 && rating > 0) {

                        ref.child("Comment").push().setValue(
                                new Comment(dateTime, detail, rating, resID, uid)
                        );


                        // Simple Dialog
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        loadReview(ref);

                    } else {
                        // Throw error there is no detail to submit for
                    }


                    // Reset value
                    commentBox.setText("");
                    reviewRating.setRating(0);


                } else {
                    auth.show(getFragmentManager(), "Authentication");
                }

            }
        });


        return rootView;
    }

    private void countReview(ArrayList<Comment> comments) {

        HashMap<Integer, Integer> statistic = new HashMap<>();
        float averageRating = 0;
        int rating, value;

        for (Comment comment : comments) {

            rating = (int) comment.getRating();

            if (statistic.get(rating) != null) {
                value = statistic.get(rating) + 1;
            } else {
                value = 1;
            }

            statistic.put(rating, value);

            System.out.println(rating);

            averageRating += comment.getRating();
        }

        if(comments.size()==0){
            averageRating = 0;
        }else {
            averageRating /= comments.size();
        }






        String fiveAmount = String.valueOf(statistic.get(5));
        String fourAmount = String.valueOf(statistic.get(4));
        String threeAmount = String.valueOf(statistic.get(3));
        String twoAmount = String.valueOf(statistic.get(2));
        String oneAmount = String.valueOf(statistic.get(1));


        if (fiveAmount.equals("null"))  fiveAmount  = "0";
        if (fourAmount.equals("null"))  fourAmount  = "0";
        if (threeAmount.equals("null")) threeAmount = "0";
        if (twoAmount.equals("null"))   twoAmount   = "0";
        if (oneAmount.equals("null"))   oneAmount   = "0";


        ((TextView) rootView.findViewById(R.id.statistic_five_number)).setText(fiveAmount);
        ((TextView) rootView.findViewById(R.id.statistic_four_number)).setText(fourAmount);
        ((TextView) rootView.findViewById(R.id.statistic_three_number)).setText(threeAmount);
        ((TextView) rootView.findViewById(R.id.statistic_two_number)).setText(twoAmount);
        ((TextView) rootView.findViewById(R.id.statistic_one_number)).setText(oneAmount);


        ((TextView) rootView.findViewById(R.id.average_number)).setText(String.format("%.2f", averageRating));
        ((RatingBar) rootView.findViewById(R.id.average_star)).setRating(averageRating);

        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();
            DatabaseReference restaurant = ref.child("Restaurant");
            restaurant.child(resID).child("restaurantStar").setValue(averageRating);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadReview(DatabaseReference ref) {

        Query comments = ref.child("Comment").orderByChild("restaurantID").equalTo(resID);
        comments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Comment> comments = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    // Get User profile
                    String uid = ds.child("userId").getValue(String.class);


                    // Get comment
                    String comment = ds.child("detail").getValue(String.class);
                    String dateTime = ds.child("dateTime").getValue(String.class);
                    float rating = ds.child("rating").getValue(float.class);

                    comments.add(
                            new Comment(dateTime, comment, rating, resID, uid)
                    );

                }

                // reverse comment
                Collections.reverse(comments);

                // Print comment to view
                RecyclerView recycleView = (RecyclerView) rootView.findViewById(R.id.comment_cycle);
                CommentAdapter myAdapter = new CommentAdapter(rootView.getContext(), comments);
                recycleView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));
                recycleView.setAdapter(myAdapter);


                countReview(comments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
