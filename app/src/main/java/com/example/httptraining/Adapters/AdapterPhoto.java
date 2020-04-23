package com.example.httptraining.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.httptraining.Pojo.Album;
import com.example.httptraining.Pojo.Photo;
import com.example.httptraining.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterPhoto extends RecyclerView.Adapter<AdapterPhoto.PhotoViewHolder>  {
    private List<Photo> photoList = new ArrayList<>();
    private AdapterPhoto.OnPhotoClickListener onPhotoClickListener;

    public AdapterPhoto(AdapterPhoto.OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }


    @Override
    public AdapterPhoto.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new AdapterPhoto.PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPhoto.PhotoViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        holder.bind(photo);
    }

    public void setItems(Collection<Photo> photos) {
        photoList.addAll(photos);
        notifyDataSetChanged();
    }

    public void clearItems() {
        photoList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPhotoTitle;
        private ImageView userPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            txtPhotoTitle = itemView.findViewById(R.id.txt_photo_title);
            userPhoto = itemView.findViewById(R.id.img_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Photo photo = photoList.get(getLayoutPosition());
                    onPhotoClickListener.onPhotoClick(photo);
                }
            });
        }
        public void bind(Photo photo) {
            String photoTitle = photo.getPhotoTitle();
            txtPhotoTitle.setText(photoTitle);
            String photoUrl = photo.getUrl();
            Glide.with(itemView.getContext()).load(photoUrl).into(userPhoto);
            userPhoto.setVisibility(photoUrl != null ? View.VISIBLE : View.GONE);
        }
    }

    public interface OnPhotoClickListener{
        void onPhotoClick(Photo photo);
    }
}