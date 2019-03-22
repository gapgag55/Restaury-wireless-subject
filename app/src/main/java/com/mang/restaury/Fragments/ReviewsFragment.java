package com.mang.restaury.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mang.restaury.Adapter.CommentAdapter;
import com.mang.restaury.Adapter.RestaurantAdapter;
import com.mang.restaury.Model.Comment;
import com.mang.restaury.Model.Restaurant;
import com.mang.restaury.R;
import com.mang.restaury.SearchActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    private ArrayList<Comment> comments;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_reviews, container, false);

        comments = new ArrayList<>();
        comments.add(new Comment("Sarayut Lawilai", "10 January 2018", "Food was great. I could not speak Thai, so communication is a bit of an issue. Everthing was find in the end. The owner was so nice.", 5));
        comments.add(new Comment("Sarayut Lawilai", "10 January 2018", "Food was great. I could not speak Thai, so communication is a bit of an issue. Everthing was find in the end. The owner was so nice.", 5));
        comments.add(new Comment("Sarayut Lawilai", "10 January 2018", "Food was great. I could not speak Thai, so communication is a bit of an issue. Everthing was find in the end. The owner was so nice.", 5));

        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.comment_cycle);
        CommentAdapter myAdapter = new CommentAdapter(view.getContext(), comments);
        recycleView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        recycleView.setAdapter(myAdapter);

        return view;
    }

}
