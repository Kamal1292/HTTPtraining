package com.example.httptraining.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.httptraining.Pojo.User;
import com.example.httptraining.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.UserViewHolder>  {
    private List<User> userList = new ArrayList<>();
    private OnUserClickListener onUserClickListener;

    public AdapterUser(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    public void setItems(List<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearItems() {
        userList.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView nickTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.txt_user_name);
            nickTextView = itemView.findViewById(R.id.txt_user_nick_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = userList.get(getLayoutPosition());
                    onUserClickListener.onUserClick(user);
                }
            });
        }

        public void bind(User user) {
            nameTextView.setText(user.getName());
            nickTextView.setText(user.getNickname());
        }
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }
}
