package com.mang.restaury.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mang.restaury.Model.Comment;
import com.mang.restaury.R;

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

        viewHolder.commentName.setText(comment.getUserId());
        viewHolder.commentDate.setText(comment.getDateTime());
        viewHolder.commentText.setText(comment.getDetail());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get ID from XML
            comment = (LinearLayout) itemView.findViewById(R.id.comment_layout);
            commentName = (TextView) itemView.findViewById(R.id.comment_name);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }

}
