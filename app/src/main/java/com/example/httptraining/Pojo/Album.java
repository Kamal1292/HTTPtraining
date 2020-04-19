package com.example.httptraining.Pojo;

public class Album {

    private String userId;
    private String albumsId;
    private String title;

    public Album(String userId, String albumsId, String title) {
        this.userId = userId;
        this.albumsId = albumsId;
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public String getAlbumsId() {
        return albumsId;
    }

    public String getTitle() {
        return title;
    }
}
