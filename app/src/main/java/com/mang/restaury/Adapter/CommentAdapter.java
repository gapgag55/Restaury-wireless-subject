package com.mang.restaury.Adapter;

import android.content.Context;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mang.restaury.Model.Comment;
import com.mang.restaury.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> mData;

    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.comment, viewGroup, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentAdapter.ViewHolder viewHolder, int i) {

        final Comment comment = mData.get(i);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        Query users = ref.child("User").child(comment.getUserId());
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstName = dataSnapshot.child("firstName").getValue(String.class);
                String lastName = dataSnapshot.child("lastName").getValue(String.class);
                String fullname = firstName + " " + lastName;

                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

                try {
                    Date date = parser.parse(comment.getDateTime());

                    viewHolder.commentName.setText(fullname);
                    viewHolder.commentText.setText(comment.getDetail());
                    viewHolder.commentRating.setRating(comment.getRating());
                    viewHolder.commentDate.setText(formatter.format(date));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Declare variable
        LinearLayout comment;
        TextView commentName;
        TextView commentDate;
        TextView commentText;
        RatingBar commentRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get ID from XML
            comment = (LinearLayout) itemView.findViewById(R.id.comment_layout);
            commentName = (TextView) itemView.findViewById(R.id.comment_name);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            commentRating = (RatingBar) itemView.findViewById(R.id.rating);
        }
    }

}
