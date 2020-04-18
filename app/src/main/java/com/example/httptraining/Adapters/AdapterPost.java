package com.example.httptraining.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httptraining.Pojo.Post;
import com.example.httptraining.Pojo.User;
import com.example.httptraining.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.PostViewHolder>  {
    private List<Post> postList = new ArrayList<>();
    private OnPostClickListener onPostClickListener;

    public AdapterPost(OnPostClickListener onPostClickListener) {
        this.onPostClickListener = onPostClickListener;
    }


    @Override
    public AdapterPost.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPost.PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);
    }

    public void setItems(Collection<Post> posts) {
        postList.addAll(posts);
        notifyDataSetChanged();
    }

    public void clearItems() {
        postList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtPost;

        public PostViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title_post);
            txtPost = itemView.findViewById(R.id.txt_post);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = postList.get(getLayoutPosition());
                    onPostClickListener.onUserClick(post);
                }
            });
        }
        public void bind(Post post) {
            String title = post.getTitle();
            txtTitle.setText(title);
            String strPost =  post.getPost();
            txtPost.setText(strPost);
        }
    }

    public interface OnPostClickListener{
        void onUserClick(Post post);
    }
}
