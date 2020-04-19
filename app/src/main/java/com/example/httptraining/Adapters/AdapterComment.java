package com.example.httptraining.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.httptraining.Pojo.Comment;
import com.example.httptraining.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.CommentViewHolder>  {
    private List<Comment> commentList = new ArrayList<>();
    private AdapterComment.OnCommentClickListener onCommentClickListener;

    public AdapterComment(AdapterComment.OnCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }


    @Override
    public AdapterComment.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new AdapterComment.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterComment.CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.bind(comment);
    }

    public void setItems(Collection<Comment> comments) {
        commentList.addAll(comments);
        notifyDataSetChanged();
    }

    public void clearItems() {
        commentList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameCom;
        private TextView txtEmailCom;
        private TextView txtBodyCom;

        public CommentViewHolder(View itemView) {
            super(itemView);
            txtNameCom = itemView.findViewById(R.id.txt_name_comment);
            txtEmailCom = itemView.findViewById(R.id.txt_commentator_email);
            txtBodyCom = itemView.findViewById(R.id.txt_comment_body);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comment comment = commentList.get(getLayoutPosition());
                    onCommentClickListener.onCommentClick(comment);
                }
            });
        }
        public void bind(Comment comment) {
            String nameCom = comment.getCommentName();
            txtNameCom.setText(nameCom);
            String emailCom =  comment.getCommentatorEmail();
            txtEmailCom.setText(emailCom);
            String bodyCom = comment.getCommentBody();
            txtBodyCom.setText(bodyCom);
        }
    }

    public interface OnCommentClickListener{
        void onCommentClick(Comment comment);
    }
}