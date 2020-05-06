package com.falkenapps.macgyver.professional.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FalkenApps:falken on 6/29/17.
 */

public class CommentRVAdapter extends RecyclerView.Adapter<CommentRVAdapter.CommentViewHolder>{

    private List<Comment> commentList;
    private final Context context;


    public CommentRVAdapter(List<Comment> commentList, Context context) {
        super();
        if (commentList != null){
            this.commentList = commentList;
        }else{
            this.commentList = new ArrayList<>();
        }
        this.context = context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        final Comment comment = commentList.get(position);
        Drawable myDrawable;

        holder.txtComment.setText(comment.getText());
        holder.txtUserName.setText(comment.getAuthorName());
        holder.txtCommentDate.setText(comment.getDate().toString());
        double score = comment.getScore();

        if (score >= 4.5){

            myDrawable = ContextCompat.getDrawable(context,R.drawable.scorefive);


        }else if(score < 4.5 && score >=3.5){
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scorefour);

        }else if(score < 3.5 && score >=2.5){
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scorethree);

        } else if(score < 2.5 && score >=1.5){
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scoretwo);

        }else if(score < 1.5 && score >=0) {
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scoreone);

        }else{
            myDrawable = ContextCompat.getDrawable(context,R.drawable.scorethree);

        }

        if (myDrawable != null){
            holder.imgCommentStars.setImageDrawable(myDrawable);
        }

    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    @Override
    public void onViewRecycled(CommentViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(CommentViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(CommentViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(CommentViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void addComment(Comment comment){
        if (comment != null){
            commentList.add(comment);

        }
    }

    public void setCommentList(List<Comment> commentList){
        this.commentList = new ArrayList<>(commentList); //This does a shallow copy
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        public final TextView txtComment;
        public final TextView txtUserName;
        public final TextView txtCommentDate;
        public final ImageView imgCommentStars;


        public CommentViewHolder(View itemView) {
            super(itemView);
            this.txtComment = (TextView) itemView.findViewById(R.id.txtComment);
            this.txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            this.txtCommentDate = (TextView) itemView.findViewById(R.id.txtCommentDate);
            this.imgCommentStars = (ImageView) itemView.findViewById(R.id.imgCommentStars);
        }

    }
}
