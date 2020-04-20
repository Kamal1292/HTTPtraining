package com.example.httptraining.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.httptraining.Pojo.Album;
import com.example.httptraining.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterAlbums extends RecyclerView.Adapter<AdapterAlbums.AlbumsViewHolder>  {
    private List<Album> albumList = new ArrayList<>();
    private AdapterAlbums.OnAlbumClickListener onAlbumClickListener;

    public AdapterAlbums(AdapterAlbums.OnAlbumClickListener onAlbumClickListener) {
        this.onAlbumClickListener = onAlbumClickListener;
    }


    @Override
    public AdapterAlbums.AlbumsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album, parent, false);
        return new AdapterAlbums.AlbumsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterAlbums.AlbumsViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.bind(album);
    }

    public void setItems(Collection<Album> albums) {
        albumList.addAll(albums);
        notifyDataSetChanged();
    }

    public void clearItems() {
        albumList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class AlbumsViewHolder extends RecyclerView.ViewHolder {
        private TextView txtAlbumTitle;

        public AlbumsViewHolder(View itemView) {
            super(itemView);
            txtAlbumTitle = itemView.findViewById(R.id.txt_albums_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Album album = albumList.get(getLayoutPosition());
                    onAlbumClickListener.onAlbumClick(album);
                }
            });
        }
        public void bind(Album album) {
            String albumTitle = album.getTitle();
            txtAlbumTitle.setText(albumTitle);
            }
    }

    public interface OnAlbumClickListener{
        void onAlbumClick(Album album);
    }
}

